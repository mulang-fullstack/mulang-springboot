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

    /**
     * 이미지 업로드 (Public 접근 가능)
     *
     * @param multipartFile 업로드할 파일
     * @param directory 저장 디렉토리 (예: "thumbnails", "profiles")
     * @return 저장된 File 엔티티
     */
    public File uploadImage(MultipartFile multipartFile, String directory) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        String fileKey = createFileKey(multipartFile, directory);

        // S3 업로드 (Public Read 권한)
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .contentType(multipartFile.getContentType())
                .acl(ObjectCannedACL.PUBLIC_READ)  // Public 접근 허용
                .build();

        s3Client.putObject(request,
                RequestBody.fromBytes(multipartFile.getBytes()));

        // DB 저장
        return saveFileEntity(multipartFile, fileKey, directory.toUpperCase());
    }

    /**
     * 영상 업로드 (Private, Pre-signed URL로만 접근 가능)
     *
     * @param multipartFile 업로드할 영상 파일
     * @return 저장된 File 엔티티
     */
    public File uploadVideo(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        String fileKey = createFileKey(multipartFile, "videos");

        // S3 업로드 (Private - ACL 설정 안 함)
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .contentType(multipartFile.getContentType())
                // ACL 없음 = Private
                .build();

        s3Client.putObject(request,
                RequestBody.fromBytes(multipartFile.getBytes()));

        // DB 저장
        return saveFileEntity(multipartFile, fileKey, "VIDEO");
    }

    /**
     * Public 이미지 URL 가져오기
     *
     * @param fileId 파일 ID
     * @return Public URL
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
     *
     * @param fileId 파일 ID
     * @param minutes 유효 시간 (분)
     * @return Pre-signed URL (임시 접근 URL)
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
     *
     * @param file 삭제할 파일 엔티티
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

    // ===== Private 헬퍼 메서드 =====

    /**
     * S3 파일 키 생성 (directory/uuid.확장자)
     */
    private String createFileKey(MultipartFile file, String directory) {
        String originalName = file.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        return directory + "/" + uuid + extension;
    }

    /**
     * File 엔티티 생성 및 저장
     */
    private File saveFileEntity(MultipartFile multipartFile, String fileKey, String fileType) {
        String fileName = fileKey.substring(fileKey.lastIndexOf("/") + 1);

        File entity = File.builder()
                .originalName(multipartFile.getOriginalFilename())
                .storedName(fileName)
                .url(fileKey)  // S3 Key 저장
                .size(multipartFile.getSize())
                .type(multipartFile.getContentType())
                .storageType("S3")
                .bucketName(bucketName)
                .fileType(fileType)
                .uploadedAt(LocalDateTime.now())
                .build();

        return fileRepository.save(entity);
    }

    /**
     * 파일 조회
     */
    private File getFile(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다"));
    }

    /**
     * S3 파일인지 확인
     */
    private void checkIsS3File(File file) {
        if (!"S3".equals(file.getStorageType())) {
            throw new RuntimeException("S3 파일이 아닙니다");
        }
    }
}