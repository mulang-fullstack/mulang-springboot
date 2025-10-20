package yoonsome.mulang.file.entity;

import jakarta.persistence.*;
import lombok.*;
import yoonsome.mulang.lecture.entity.Lecture;

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

    //사용자가 원래 올린 이름
    @Column(nullable = false, length = 255)
    private String originalName;

    //저장된 파일 이름 uuid
    @Column(nullable = false, length = 255)
    private String savedName;

    //저장경로
    @Column(nullable = false, length = 500)
    private String path;

    //파일크기 byte로?
    @Column(nullable = false)
    private Long size;

    //파일 확장자 형식
    @Column(nullable = false, length = 100)
    private String contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
}
