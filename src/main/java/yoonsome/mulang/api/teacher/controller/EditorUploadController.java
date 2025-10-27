package yoonsome.mulang.api.teacher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.infra.file.entity.File;
import yoonsome.mulang.infra.file.service.FileService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/editor")
public class EditorUploadController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadEditorImage(@RequestParam("upload") MultipartFile upload) throws IOException {
        if (upload.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "파일이 비어 있습니다."));
        }

        File savedFile = fileService.createFile(upload);
        return ResponseEntity.ok(Map.of("url", savedFile.getUrl()));
    }
}
