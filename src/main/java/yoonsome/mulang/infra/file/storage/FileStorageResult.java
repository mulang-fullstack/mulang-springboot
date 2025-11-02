package yoonsome.mulang.infra.file.storage;

import lombok.Builder;
import lombok.Getter;
import yoonsome.mulang.infra.file.enums.StorageType;

@Getter
@Builder
public class FileStorageResult {
    private String storedName;      // UUID 파일명
    private String url;             // 접근 경로 (S3 Key 또는 로컬 경로)
    private StorageType storageType;
    private String bucketName;      // S3인 경우
}