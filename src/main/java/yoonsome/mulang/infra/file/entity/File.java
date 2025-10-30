package yoonsome.mulang.infra.file.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    /** 사용자가 업로드한 원본 파일명 */
    @Column(name = "original_name", nullable = false, length = 255)
    private String originalName;

    /** 서버나 스토리지에 저장된 UUID 기반 파일명 */
    @Column(name = "stored_name", nullable = false, length = 255)
    private String storedName;

    /** 접근 가능한 파일 URL (로컬 또는 클라우드 경로) */
    @Column(nullable = false, length = 500)
    private String url;

    /** 파일 크기 (byte 단위) */
    @Column(nullable = false)
    private Long size;

    /** 파일 타입 (예: image/png, video/mp4 등) */
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    /** 업로드된 시각 */
    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    // ===== S3 지원을 위해 추가된 필드들 (NULL 허용, 기존 데이터 영향 없음) =====

    /** 저장소 타입 (NULL이면 기존 로컬 파일) */
    @Column(name = "storage_type", length = 20)
    private String storageType;  // "LOCAL" or "S3"

    /** S3 버킷 이름 */
    @Column(name = "bucket_name", length = 100)
    private String bucketName;

    /** 파일 용도 (NULL이면 일반 파일) */
    @Column(name = "file_type", length = 30)
    private String fileType;  // "VIDEO", "THUMBNAIL", "PROFILE_IMAGE" 등
}
