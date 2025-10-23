package yoonsome.mulang.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yoonsome.mulang.infra.file.entity.File;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"}),
                @UniqueConstraint(columnNames = {"nickname"})
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    private String provider;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 50, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false, length = 10)
    private UserStatus userStatus = UserStatus.ACTIVE; // 기본값: 활성화

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.userStatus = UserStatus.ACTIVE;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum Role {
        STUDENT, TEACHER, ADMIN
    }

    public enum UserStatus {
        ACTIVE, TEACHER, ADMIN
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;
}
