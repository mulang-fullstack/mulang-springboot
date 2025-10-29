package yoonsome.mulang.api.admin.content.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminCourseResponse {
    private Long id;
    private String courseName;      // 강좌명
    private String languageName;    // 언어
    private String teacherName;     // 강사 이름
    private String teacherNickname; // 강사 닉네임
    private String createdAt; // 등록일
    private StatusType status;      // 상태
    private String rejectionReason; //거절 사유

    public static AdminCourseResponse from(Course course) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return AdminCourseResponse.builder()
                .id(course.getId())
                .courseName(course.getTitle())
                .languageName(course.getLanguage().getName())
                .teacherName(course.getTeacher().getUser().getUsername())
                .teacherNickname(course.getTeacher().getUser().getNickname())
                .createdAt(course.getTeacher().getUser().getCreatedAt()!= null ?
                        course.getTeacher().getUser().getCreatedAt().format(fmt)  : "")
                .status(course.getStatus())
                .rejectionReason(course.getRejectionReason())
                .build();
    }
}