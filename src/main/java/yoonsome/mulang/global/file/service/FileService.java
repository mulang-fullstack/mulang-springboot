package yoonsome.mulang.global.file.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.lecture.entity.Lecture;
import yoonsome.mulang.global.file.entity.File;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileService {

    File createFile(MultipartFile multipartFile, Lecture lecture) throws IOException;
    File createFile(MultipartFile multipartFile) throws IOException;
    List<File> getFileList();
    Optional<File> getFileById(long id);
    void deleteFile(File file);
    ResponseEntity<Resource> downloadFile(long id);
}
