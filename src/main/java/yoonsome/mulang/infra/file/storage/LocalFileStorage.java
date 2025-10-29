//package yoonsome.mulang.infra.file.storage;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//import yoonsome.mulang.infra.file.entity.File;
//import yoonsome.mulang.infra.file.enums.StorageType;
//
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//public class LocalFileStorage implements FileStorage {
//
//    @Value("${file.dir}")
//    private String fileDir;
//
//    @Override
//    public FileStorageResult upload(MultipartFile multipartFile, String directory) throws IOException {
//        // 디렉토리 생성
//        Path dirPath = Paths.get(fileDir, directory);
//        if (!Files.exists(dirPath)) {
//            Files.createDirectories(dirPath);
//        }
//
//        // 파일명 생성
//        String originalFileName = multipartFile.getOriginalFilename();
//        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
//        String uuid = UUID.randomUUID().toString();
//        String savedFileName = uuid + fileExtension;
//
//        // 파일 저장
//        Path savedPath = dirPath.resolve(savedFileName);
//        Files.copy(multipartFile.getInputStream(), savedPath, StandardCopyOption.REPLACE_EXISTING);
//
//        // URL 생성
//        String url = "/upload/" + directory + "/" + savedFileName;
//
//        return FileStorageResult.builder()
//                .storedName(savedFileName)
//                .url(url)
//                .storageType(StorageType.LOCAL)
//                .build();
//    }
//
//    @Override
//    public void delete(File file) throws IOException {
//        Path targetPath = Paths.get(fileDir, file.getUrl().replace("/upload/", ""));
//        Files.deleteIfExists(targetPath);
//    }
//
//    @Override
//    public Resource loadAsResource(File file) throws IOException {
//        Path filePath = Paths.get(fileDir, file.getUrl().replace("/upload/", ""));
//
//        if (!Files.exists(filePath)) {
//            throw new IOException("파일을 찾을 수 없습니다: " + filePath);
//        }
//
//        return new FileSystemResource(filePath);
//    }
//
//    @Override
//    public String getAccessibleUrl(File file, int expirationMinutes) {
//        // 로컬 파일은 항상 동일한 URL
//        return file.getUrl();
//    }
//}