package yoonsome.mulang.domain.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yoonsome.mulang.domain.ai.entity.LevelTest;

import java.util.List;
import java.util.Optional;

@Repository
public interface LevelTestRepository extends JpaRepository<LevelTest, Long> {

    /**
     * 사용자별 테스트 결과 조회 (최신순)
     */
    List<LevelTest> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * 사용자별 특정 언어 테스트 결과 조회
     */
    List<LevelTest> findByUserIdAndLanguageOrderByCreatedAtDesc(Long userId, LevelTest.Language language);

    /**
     * 사용자의 가장 최근 테스트 조회
     */
    Optional<LevelTest> findFirstByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * 완료되지 않은 테스트 조회
     */
    Optional<LevelTest> findByIdAndCompletedAtIsNull(Long testId);

    /**
     * 사용자별 완료된 테스트 조회 (최신순)
     * - 30일 제한 체크용
     */
    List<LevelTest> findByUserIdAndCompletedAtIsNotNullOrderByCompletedAtDesc(Long userId);

    /**
     * 사용자별 특정 언어의 가장 최근 완료된 테스트 조회
     * - 30일 제한 체크용 (언어별)
     */
    Optional<LevelTest> findTopByUserIdAndLanguageAndCompletedAtIsNotNullOrderByCompletedAtDesc(
            Long userId,
            LevelTest.Language language
    );
}