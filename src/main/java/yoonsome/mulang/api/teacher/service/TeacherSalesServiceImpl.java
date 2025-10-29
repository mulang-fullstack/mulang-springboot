package yoonsome.mulang.api.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoonsome.mulang.api.teacher.dto.TeacherSalesResponse;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.payment.dto.PaymentResponseDto;
import yoonsome.mulang.domain.payment.entity.PaymentStatus;
import yoonsome.mulang.domain.payment.service.PaymentService;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.teacher.service.TeacherService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherSalesServiceImpl implements TeacherSalesService {

    private final TeacherService teacherService;
    private final CourseService courseService;
    private final PaymentService paymentService;

    @Override
    public Page<TeacherSalesResponse> getTeacherSalesPage(Long teacherId, Pageable pageable) {
        Teacher teacher = teacherService.getTeacherById(teacherId);

        // 페이징된 코스 조회
        Page<Course> coursePage = courseService.getTeacherCoursePage(
                teacher,
                List.of(StatusType.values()),
                pageable
        );

        List<TeacherSalesResponse> responseList = new ArrayList<>();

        // 각 코스별 결제 정보 집계
        for (Course course : coursePage.getContent()) {
            int totalAmount = 0;
            int totalCount = 0;

            List<PaymentResponseDto> payments = paymentService.getPaymentsByCourseId(course.getId());

            for (PaymentResponseDto payment : payments) {
                if (PaymentStatus.COMPLETED.name().equals(payment.getStatus())) {
                    totalAmount += payment.getAmount();
                    totalCount++;
                }
            }

            // 매출이 0이어도 포함 (강좌 목록 표시를 위해)
            TeacherSalesResponse response = TeacherSalesResponse.builder()
                    .courseId(course.getId())
                    .courseTitle(course.getTitle())
                    .totalAmount(totalAmount)
                    .totalCount(totalCount)
                    .build();

            responseList.add(response);
        }
        // 이렇게 해야 페이지네이션이 정확하게 작동
        return new PageImpl<>(responseList, pageable, coursePage.getTotalElements());
    }

    @Override
    public int getTeacherTotalSales(Long teacherId) {
        Teacher teacher = teacherService.getTeacherById(teacherId);

        // 전체 코스 조회 (페이징 없이)
        Page<Course> coursePage = courseService.getTeacherCoursePage(
                teacher,
                List.of(StatusType.values()),
                Pageable.unpaged()
        );

        int totalSales = 0;

        // 모든 코스의 완료된 결제 합계
        for (Course course : coursePage.getContent()) {
            List<PaymentResponseDto> payments = paymentService.getPaymentsByCourseId(course.getId());

            for (PaymentResponseDto payment : payments) {
                if (PaymentStatus.COMPLETED.name().equals(payment.getStatus())) {
                    totalSales += payment.getAmount();
                }
            }
        }
        return totalSales;
    }
}