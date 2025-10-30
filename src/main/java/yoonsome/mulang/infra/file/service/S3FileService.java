package yoonsome.mulang.infra.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import yoonsome.mulang.infra.file.entity.File;
import yoonsome.mulang.infra.file.enums.FileType;
import yoonsome.mulang.infra.file.repo.FileRepository;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class S3FileService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final FileRepository fileRepository;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    // ===== 간편한 용도별 메서드 =====

    public File uploadThumbnail(MultipartFile file) throws IOException {
        return uploadFile(file, FileType.THUMBNAIL);
    }

    public File uploadProfileImage(MultipartFile file) throws IOException {
        return uploadFile(file, FileType.PROFILE_IMAGE);
    }

    public File uploadImage(MultipartFile file) throws IOException {
        return uploadFile(file, FileType.IMAGE);
    }

    public File uploadVideo(MultipartFile file) throws IOException {
        return uploadFile(file, FileType.VIDEO);
    }

    // ===== 범용 업로드 메서드 =====

    /**
     * 파일 업로드 (ACL 없이 버킷 정책 사용)
     */
    public File uploadFile(MultipartFile multipartFile, FileType fileType) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        String directory = getDirectory(fileType);
        String fileKey = createFileKey(multipartFile, directory);

        // S3 업로드 설정 (ACL 제거)
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .contentType(multipartFile.getContentType())
                .build();

        // S3에 업로드
        s3Client.putObject(request,
                RequestBody.fromBytes(multipartFile.getBytes()));

        // DB 저장
        return saveFileEntity(multipartFile, fileKey, fileType.name());
    }

    /**
     * Public URL 가져오기
     */
    public String getPublicUrl(Long fileId) {
        File file = getFile(fileId);
        checkIsS3File(file);

        if ("VIDEO".equals(file.getFileType())) {
            throw new RuntimeException("비디오는 Public URL을 제공하지 않습니다");
        }

        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, region, file.getUrl());
    }

    /**
     * Pre-signed URL 생성 (영상용)
     */
    public String getPresignedUrl(Long fileId, int minutes) {
        File file = getFile(fileId);
        checkIsS3File(file);

        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(file.getUrl())
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(minutes))
                .getObjectRequest(getRequest)
                .build();

        PresignedGetObjectRequest presigned = s3Presigner.presignGetObject(presignRequest);
        return presigned.url().toString();
    }

    /**
     * S3 파일 삭제
     */
    public void deleteFile(File file) {
        if (file == null || !"S3".equals(file.getStorageType())) {
            return;
        }

        try {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(file.getUrl())
                    .build();

            s3Client.deleteObject(deleteRequest);
            fileRepository.delete(file);
        } catch (S3Exception e) {
            throw new RuntimeException("S3 파일 삭제 실패: " + file.getUrl(), e);
        }
    }

    /**
     * FileType에 따라 S3 디렉토리 결정
     * Public 파일은 모두 public/ 안에 저장
     */
    private String getDirectory(FileType fileType) {
        return switch (fileType) {
            case VIDEO -> "private/videos";              // Private
            case THUMBNAIL -> "public/thumbnails";       // Public
            case PROFILE_IMAGE -> "public/profiles";     // Public
            case IMAGE -> "public/images";     // Public
            case LECTURE_MATERIAL -> "private/materials"; // Private
            case DOCUMENT -> "private/documents";        // Private
        };
    }

    private String createFileKey(MultipartFile file, String directory) {
        String originalName = file.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        return directory + "/" + uuid + extension;
    }

    private File saveFileEntity(MultipartFile multipartFile, String fileKey, String fileType) {
        String fileName = fileKey.substring(fileKey.lastIndexOf("/") + 1);

        File entity = File.builder()
                .originalName(multipartFile.getOriginalFilename())
                .storedName(fileName)
                .url(fileKey)
                .size(multipartFile.getSize())
                .type(multipartFile.getContentType())
                .storageType("S3")
                .bucketName(bucketName)
                .fileType(fileType)
                .uploadedAt(LocalDateTime.now())
                .build();

        return fileRepository.save(entity);
    }

    private File getFile(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다"));
    }

    private void checkIsS3File(File file) {
        if (!"S3".equals(file.getStorageType())) {
            throw new RuntimeException("S3 파일이 아닙니다");
        }
    }
}