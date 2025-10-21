package yoonsome.mulang.infra.file.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.infra.file.entity.File;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * ============================================================
 * FileService 인터페이스
 * ============================================================
 * <개요>
 *  - 모든 파일 관련 기능(업로드, 다운로드, 삭제, 조회)을 정의하는 서비스 인터페이스입니다.
 *  - 실제 구현체는 {@link yoonsome.mulang.infra.file.service.FileServiceImpl} 에서 담당합니다.
 *
 * <주요 역할>
 *  1. 파일 업로드
 *      - 사용자가 업로드한 MultipartFile을 로컬 디스크에 저장.
 *      - DB에는 파일의 메타데이터(파일명, 용량, MIME 타입, URL 등)를 기록.
 *      - FileResourceConfig 설정을 통해 브라우저에서 /upload/** URL로 접근 가능.
 *
 *  2. 파일 조회 / 다운로드
 *      - 저장된 파일 정보를 불러오거나, 실제 파일을 Resource 형태로 내려줍니다.
 *
 *  3. 파일 삭제
 *      - 로컬 저장소와 DB 양쪽에서 파일을 제거합니다.
 *
 * ============================================================
 * 작성자: 김보카
 * 수정자: 양진석
 * 작성일: 2025-10-21
 * ============================================================
 */
public interface FileService {

    /**
     * 강의(Lecture)와 연관된 파일을 업로드하고 DB에 저장한다.
     *
     * @param multipartFile 업로드할 파일 객체 (Spring MultipartFile)
     * @param lecture       연관된 Lecture 엔티티 (없을 경우 null 가능)
     * @return 저장된 File 엔티티 객체
     * @throws IOException  파일 저장 중 오류 발생 시
     *
     * <사용 예시>
     *   File savedFile = fileService.createFile(uploadedFile, lectureEntity);
     *   lectureEntity.setFile(savedFile);
     */
    File createFile(MultipartFile multipartFile, Lecture lecture) throws IOException;


    /**
     * 강의와 관계없이 독립적으로 파일을 업로드한다.
     * (예: 프로필 이미지, 썸네일 등)
     *
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
     * 저장된 모든 파일 목록을 조회한다.
     *
     * @return File 엔티티의 전체 리스트
     *
     * <사용 예시>
     *   List<File> files = fileService.getFileList();
     */
    List<File> getFileList();


    /**
     * 파일 ID로 단일 파일을 조회한다.
     *
     * @param id 조회할 파일의 고유 ID
     * @return Optional<File> (존재하지 않으면 Optional.empty())
     *
     * <사용 예시>
     *   Optional<File> fileOpt = fileService.getFileById(10L);
     */
    Optional<File> getFileById(long id);


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
