//package yoonsome.mulang.infra.file.storage;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.*;
//import software.amazon.awssdk.services.s3.presigner.S3Presigner;
//import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
//import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
//import yoonsome.mulang.infra.file.entity.File;
//import yoonsome.mulang.infra.file.enums.StorageType;
//
//import java.io.IOException;
//import java.time.Duration;
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//public class S3FileStorage implements FileStorage {
//
//    private final S3Client s3Client;
//    private final S3Presigner s3Presigner;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucketName;
//
//    @Value("${cloud.aws.region.static}")
//    private String region;
//
//    @Override
//    public FileStorageResult upload(MultipartFile multipartFile, String directory) throws IOException {
//        // 파일명 생성
//        String originalFileName = multipartFile.getOriginalFilename();
//        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
//        String uuid = UUID.randomUUID().toString();
//        String fileName = uuid + fileExtension;
//        String fileKey = directory + "/" + fileName;
//
//        // S3 업로드 설정
//        PutObjectRequest.Builder requestBuilder = PutObjectRequest.builder()
//                .bucket(bucketName)
//                .key(fileKey)
//                .contentType(multipartFile.getContentType())
//                .contentLength(multipartFile.getSize());
//
//        // 비디오가 아니면 Public 읽기 권한 부여
//        if (!directory.equals("videos")) {
//            requestBuilder.acl(ObjectCannedACL.PUBLIC_READ);
//        }
//
//        // S3에 업로드
//        s3Client.putObject(
//                requestBuilder.build(),
//                RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize())
//        );
//
//        return FileStorageResult.builder()
//                .storedName(fileName)
//                .url(fileKey)  // S3 Key 저장
//                .storageType(StorageType.S3)
//                .bucketName(bucketName)
//                .build();
//    }
//
//    @Override
//    public void delete(File file) throws IOException {
//        try {
//            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
//                    .bucket(file.getBucketName())
//                    .key(file.getUrl())
//                    .build();
//
//            s3Client.deleteObject(deleteRequest);
//        } catch (S3Exception e) {
//            throw new IOException("S3 파일 삭제 실패: " + file.getUrl(), e);
//        }
//    }
//
//    @Override
//    public Resource loadAsResource(File file) throws IOException {
//        try {
//            GetObjectRequest getRequest = GetObjectRequest.builder()
//                    .bucket(file.getBucketName())
//                    .key(file.getUrl())
//                    .build();
//
//            byte[] fileBytes = s3Client.getObjectAsBytes(getRequest).asByteArray();
//            return new ByteArrayResource(fileBytes);
//        } catch (S3Exception e) {
//            throw new IOException("S3 파일 로드 실패: " + file.getUrl(), e);
//        }
//    }
//
//    @Override
//    public String getAccessibleUrl(File file, int expirationMinutes) {
//        // Public 파일은 직접 URL 반환
//        if (file.isPublicAccessible()) {
//            return String.format("https://%s.s3.%s.amazonaws.com/%s",
//                    file.getBucketName(), region, file.getUrl());
//        }
//
//        // Private 파일(VOD)은 Pre-signed URL 생성
//        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                .bucket(file.getBucketName())
//                .key(file.getUrl())
//                .build();
//
//        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
//                .getObjectRequest(getObjectRequest)
//                .signatureDuration(Duration.ofMinutes(expirationMinutes))
//                .build();
//
//        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
//        return presignedRequest.url().toString();
//    }
//}