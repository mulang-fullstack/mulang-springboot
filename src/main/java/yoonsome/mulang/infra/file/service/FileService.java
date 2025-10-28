package yoonsome.mulang.infra.file.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.infra.file.entity.File;

import java.io.IOException;
/**
 *  - 모든 파일 관련 기능(업로드, 다운로드, 삭제, 조회)을 정의하는 서비스 인터페이스입니다.
 *  - 실제 구현체는 {@link yoonsome.mulang.infra.file.service.FileServiceImpl} 에서 담당합니다.
 * <주요 역할>
 *  1. 파일 업로드
 *      - 사용자가 업로드한 MultipartFile을 로컬 디스크에 저장.
 *      - DB에는 파일의 메타데이터(파일명, 용량, MIME 타입, URL 등)를 기록.
 *      - FileResourceConfig 설정을 통해 브라우저에서 /upload/** URL로 접근 가능.
 *  2. 파일 조회 / 다운로드
 *      - 저장된 파일 정보를 불러오거나, 실제 파일을 Resource 형태로 내려줍니다.
 *  3. 파일 삭제
 *      - 로컬 저장소와 DB 양쪽에서 파일을 제거.
 *
 */
public interface FileService {


    /**
     *  파일을 업로드한다.
     *  이제그냥 이걸로 다처리한다
     * @param multipartFile 업로드할 파일 객체
     * @return 저장된 File 엔티티 객체
     * @throws IOException  파일 저장 중 오류 발생 시
     *
     * <사용 예시>
     *   File savedThumbnail = fileService.createFile(thumbnailFile);
     *   user.setFile(savedThumbnail);
     */
    File createFile(MultipartFile multipartFile) throws IOException;


    /**
     * 특정 파일을 시스템과 DB 양쪽에서 삭제한다.
     *
     * @param file 삭제할 File 엔티티
     *
     * <주의>
     *   - 로컬 파일이 존재하지 않아도 DB 레코드는 삭제됩니다.
     *   - null 전달 시 무시됩니다.
     *
     * <사용 예시>
     *   fileService.deleteFile(fileEntity);
     */
    void deleteFile(File file);


    /**
     * 파일을 다운로드용으로 반환한다.
     * (Spring ResponseEntity<Resource> 형태)
     *
     * @param id 다운로드할 파일의 ID
     * @return ResponseEntity<Resource> (파일 스트림 응답)
     *
     * <사용 예시>
     *   ResponseEntity<Resource> response = fileService.downloadFile(5L);
     */
    ResponseEntity<Resource> downloadFile(long id);
}
