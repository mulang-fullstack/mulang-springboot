package yoonsome.mulang.course.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yoonsome.mulang.course.entity.Course;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseServiceImplTest {
    @Autowired
    private CourseService courseService;

    @Test
    void listS() {
        courseService.listS();
        System.out.println("test");
    }

    @Test
    void contentS() {
    }

    @Test
    void insertS() {
        Course course = new Course();
    }

    @Test
    void deleteS() {
    }

    @Test
    void findBySeqS() {
    }
}