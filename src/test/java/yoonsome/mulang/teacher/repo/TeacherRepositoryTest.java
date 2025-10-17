package yoonsome.mulang.teacher.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yoonsome.mulang.teacher.entity.Tutor;
import yoonsome.mulang.lecture.entity.Language;
import yoonsome.mulang.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TutorRepositoryCrudTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @PersistenceContext
    private EntityManager em;

    private Language persistLanguage() {
        Language language = new Language();
        language.setName("English");
        em.persist(language);
        return language;
    }

    private User persistUser() {
        User user = new User();
        user.setName("Test User");
        user.setPassword("1234");
        user.setNickname("tester");
        user.setEmail("test@example.com");
        user.setPhone("010-1234-5678");
        user.setRole("TUTOR");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        em.persist(user);
        return user;
    }

    @Test
    @DisplayName("C - Tutor 저장 테스트")
    void createTutor() {
        Language language = persistLanguage();
        User user = persistUser();

        Tutor tutor = new Tutor();
        tutor.setDisc("English Tutor");
        tutor.setPhoto("photo.jpg");
        tutor.setLanguage(language);
        tutor.setUser(user);

        Tutor saved = teacherRepository.save(tutor);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUser().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("R - Tutor 조회 테스트")
    void readTutor() {
        Language language = persistLanguage();
        User user = persistUser();

        Tutor tutor = new Tutor();
        tutor.setDisc("Read Test");
        tutor.setPhoto("read.jpg");
        tutor.setLanguage(language);
        tutor.setUser(user);

        Tutor saved = teacherRepository.save(tutor);
        Optional<Tutor> found = teacherRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getDisc()).isEqualTo("Read Test");
    }

    @Test
    @DisplayName("U - Tutor 수정 테스트")
    void updateTutor() {
        Language language = persistLanguage();
        User user = persistUser();

        Tutor tutor = new Tutor();
        tutor.setDisc("Before Update");
        tutor.setPhoto("update.jpg");
        tutor.setLanguage(language);
        tutor.setUser(user);
        Tutor saved = teacherRepository.save(tutor);

        saved.setDisc("After Update");
        teacherRepository.save(saved);

        Tutor updated = teacherRepository.findById(saved.getId()).orElseThrow();
        assertThat(updated.getDisc()).isEqualTo("After Update");
    }

    @Test
    @DisplayName("D - Tutor 삭제 테스트")
    void deleteTutor() {
        Language language = persistLanguage();
        User user = persistUser();

        Tutor tutor = new Tutor();
        tutor.setDisc("Delete Me");
        tutor.setPhoto("delete.jpg");
        tutor.setLanguage(language);
        tutor.setUser(user);
        Tutor saved = teacherRepository.save(tutor);

        teacherRepository.delete(saved);
        Optional<Tutor> deleted = teacherRepository.findById(saved.getId());
        assertThat(deleted).isEmpty();
    }

    @Test
    @DisplayName("R - Tutor 전체 조회 테스트")
    void listTutors() {
        Language language = persistLanguage();
        User user = persistUser();

        Tutor t1 = new Tutor();
        t1.setDisc("Tutor A");
        t1.setPhoto("a.jpg");
        t1.setLanguage(language);
        t1.setUser(user);

        Tutor t2 = new Tutor();
        t2.setDisc("Tutor B");
        t2.setPhoto("b.jpg");
        t2.setLanguage(language);
        t2.setUser(user);

        teacherRepository.save(t1);
        teacherRepository.save(t2);

        List<Tutor> tutors = teacherRepository.findAll();
        assertThat(tutors).hasSizeGreaterThanOrEqualTo(2);
    }
}
