package yoonsome.mulang.infra.file.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.infra.file.entity.File;

import java.io.IOException;

/**
 * 파일 저장소 추상화 인터페이스
 * - 로컬 파일 시스템, S3 등 다양한 저장소를 동일한 방식으로 사용
 */
public interface FileStorage {

    /**
     * 파일을 저장하고 저장 정보를 반환
     *
     * @param multipartFile 업로드할 파일
     * @param directory 저장할 디렉토리 (예: "thumbnails", "videos")
     * @return 저장된 파일 정보 (storedName, url 등)
     */
    FileStorageResult upload(MultipartFile multipartFile, String directory) throws IOException;

    /**
     * 파일 삭제
     *
     * @param file 삭제할 파일 엔티티
     */
    void delete(File file) throws IOException;

    /**
     * 파일 다운로드용 Resource 반환
     *
     * @param file 다운로드할 파일 엔티티
     * @return 파일 Resource
     */
    Resource loadAsResource(File file) throws IOException;

    /**
     * 접근 가능한 URL 반환
     * - PUBLIC 파일: 직접 접근 가능한 URL
     * - PRIVATE 파일: Pre-signed URL (S3) 또는 서버 경유 URL
     *
     * @param file 파일 엔티티
     * @param expirationMinutes Pre-signed URL 유효 시간 (분)
     * @return 접근 가능한 URL
     */
    String getAccessibleUrl(File file, int expirationMinutes);
}