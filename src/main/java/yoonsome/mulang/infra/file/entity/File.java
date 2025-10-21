package yoonsome.mulang.infra.file.entity;

import jakarta.persistence.*;
import lombok.*;
import yoonsome.mulang.domain.lecture.entity.Lecture;
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

}
