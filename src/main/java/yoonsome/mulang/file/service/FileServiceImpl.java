package yoonsome.mulang.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.course.entity.Lecture;
import yoonsome.mulang.file.entity.File;
import yoonsome.mulang.file.repo.FileRepository;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * <p>VOD, 첨부자료 등 모든 파일 업로드 / 다운로드 / 삭제 기능을 담당한다.</p>
 * @author 양진석
 * @version 1.0
 * @since 2025-10-18
 */
@Service
@RequiredArgsConstructor
@Transactional
public class FileServiceImpl implements FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

    /**
     * 파일을 저장하고 DB에 메타데이터를 기록한다.
     *
     * @param multipartFile 업로드된 파일
     * @param lecture 연관된 Lecture 엔티티
     * @return 저장된 File 엔티티
     * @throws IOException 파일 저장 실패 시
     */

    @Override
    public File createFile(MultipartFile multipartFile, Lecture lecture) throws IOException {
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

        File entity = File.builder()
                .originalName(originalFileName)
                .savedName(savedFileName)
                .path(savedFilePath)
                .size(multipartFile.getSize())
                .contentType(multipartFile.getContentType())
                .lecture(lecture)
                .build();

        return fileRepository.save(entity);
    }

    /**
     * 저장된 모든 파일 목록을 조회한다.
     *
     * @return File 엔티티 리스트
     */
    @Override
    public List<File> getFileList() {
        return fileRepository.findAll();
    }

    /**
     * ID로 단일 파일을 조회한다.
     *
     * @param id 파일 ID
     * @return Optional<File>
     */
    @Override
    public Optional<File> getFileById(long id) {
        return fileRepository.findById(id);
    }

    /**
     * 파일을 시스템과 DB에서 모두 삭제한다.
     *
     * @param file 삭제할 File 엔티티
     */
    @Override
    public void deleteFile(File file) {
        if (file == null) return;

        Path targetPath = Paths.get(file.getPath());
        try {
            Files.deleteIfExists(targetPath);
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 실패: " + file.getPath(), e);
        }
        fileRepository.delete(file);
    }

    /**
     * 파일을 다운로드한다.
     *
     * @param id 파일 ID
     * @return ResponseEntity<Resource> 파일 리소스 응답
     */
    @Override
    public ResponseEntity<Resource> downloadFile(long id) {
        Optional<File> optionalFile = fileRepository.findById(id);
        if (optionalFile.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        File fileEntity = optionalFile.get();
        Path filePath = Paths.get(fileEntity.getPath());
        if (!Files.exists(filePath)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        Resource resource = new FileSystemResource(filePath);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + fileEntity.getOriginalName() + "\"")
                .body(resource);
    }
}
