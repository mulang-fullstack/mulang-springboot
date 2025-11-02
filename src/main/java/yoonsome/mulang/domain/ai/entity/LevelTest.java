package yoonsome.mulang.domain.ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yoonsome.mulang.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "level_tests")
@Getter
@Setter
@NoArgsConstructor
public class LevelTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Language language;  // ENGLISH, JAPANESE, CHINESE

    @Column(name = "test_level", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TestLevel testLevel;  // A1, A2, B1, B2

    @Column(name = "total_questions", nullable = false)
    private Integer totalQuestions = 10;

    @Column(name = "correct_answers", nullable = false)
    private Integer correctAnswers = 0;

    @Column(nullable = false)
    private Integer score = 0;  // 점수 (100점 만점)

    // ✅ nullable로 변경! (테스트 시작 시에는 null)
    @Column(name = "result_level", length = 20)
    @Enumerated(EnumType.STRING)
    private ResultLevel resultLevel;  // BASIC, GRAMMAR, CONVERSATION

    @Column(columnDefinition = "TEXT")
    private String feedback;  // AI 피드백

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (startedAt == null) {
            startedAt = LocalDateTime.now();
        }
    }

    /**
     * 언어 타입
     */
    public enum Language {
        ENGLISH("영어"),
        JAPANESE("일본어"),
        CHINESE("중국어");

        private final String korean;

        Language(String korean) {
            this.korean = korean;
        }

        public String getKorean() {
            return korean;
        }
    }

    /**
     * 테스트 난이도
     */
    public enum TestLevel {
        A1("초급 1단계"),
        A2("초급 2단계"),
        B1("중급 1단계"),
        B2("중급 2단계");

        private final String description;

        TestLevel(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * 결과 레벨
     */
    public enum ResultLevel {
        BASIC("기초", "기본 어휘와 간단한 문장을 이해할 수 있는 수준입니다."),
        GRAMMAR("문법", "기초 문법을 활용하여 의사소통이 가능한 수준입니다."),
        CONVERSATION("회화", "자연스러운 대화와 문맥 이해가 가능한 수준입니다.");

        private final String korean;
        private final String description;

        ResultLevel(String korean, String description) {
            this.korean = korean;
            this.description = description;
        }

        public String getKorean() {
            return korean;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * 점수 계산
     */
    public void calculateScore() {
        if (totalQuestions > 0) {
            this.score = (correctAnswers * 100) / totalQuestions;
        }
    }

    /**
     * 결과 레벨 판정
     */
    public void determineResultLevel() {
        if (correctAnswers <= 3) {
            this.resultLevel = ResultLevel.BASIC;
        } else if (correctAnswers <= 6) {
            this.resultLevel = ResultLevel.GRAMMAR;
        } else {
            this.resultLevel = ResultLevel.CONVERSATION;
        }
    }
}