package yoonsome.mulang.infra.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.infra.file.entity.File;
import yoonsome.mulang.infra.file.repo.FileRepository;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FileServiceImpl implements FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;


    @Override
    public void deleteFile(File file) {
        if (file == null) return;

        Path targetPath = Paths.get(fileDir + file.getStoredName());
        try {
            Files.deleteIfExists(targetPath);
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 실패: " + file.getUrl(), e);
        }
        fileRepository.delete(file);
    }

    @Override
    public ResponseEntity<Resource> downloadFile(long id) {
        Optional<File> optionalFile = fileRepository.findById(id);
        if (optionalFile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        File fileEntity = optionalFile.get();
        Path filePath = Paths.get(fileDir + fileEntity.getStoredName());

        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(filePath);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + fileEntity.getOriginalName() + "\"")
                .body(resource);
    }

    @Override
    public File createFile(MultipartFile multipartFile) throws IOException {
        Path dirPath = Paths.get(fileDir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String savedFileName = uuid + fileExtension;
        String savedFilePath = fileDir + savedFileName;

        Path savedPath = Paths.get(savedFilePath);
        Files.copy(multipartFile.getInputStream(), savedPath, StandardCopyOption.REPLACE_EXISTING);

        String url = "/upload/" + savedFileName;

        File entity = File.builder()
                .originalName(originalFileName)
                .storedName(savedFileName)
                .url(url)
                .size(multipartFile.getSize())
                .type(multipartFile.getContentType())
                .uploadedAt(LocalDateTime.now())
                .build();

        return fileRepository.save(entity);
    }
}
