package yoonsome.mulang.api.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoonsome.mulang.api.payments.dto.PaymentDetailResponse;
import yoonsome.mulang.api.teacher.dto.TeacherSalesResponse;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.api.payments.dto.PaymentSuccessResponse;
import yoonsome.mulang.domain.payment.entity.PaymentStatus;
import yoonsome.mulang.domain.payment.service.PaymentService;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.teacher.service.TeacherService;

import java.util.ArrayList;
import java.util.Comparator;
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

        // [1] 전체 강좌 조회 (페이징 없이)
        Page<Course> coursePage = courseService.getTeacherCoursePage(
                teacher,
                List.of(StatusType.values()),
                Pageable.unpaged()
        );

        // [2] 전체 강좌 매출 데이터 생성
        List<TeacherSalesResponse> responseList = new ArrayList<>();
        for (Course course : coursePage.getContent()) {
            int totalAmount = 0;
            int totalCount = 0;

            List<PaymentDetailResponse> payments = paymentService.getPaymentsByCourseId(course.getId());
            for (PaymentDetailResponse payment : payments) {
                if (PaymentStatus.COMPLETED.name().equals(payment.getStatus())) {
                    totalAmount += payment.getAmount();
                    totalCount++;
                }
            }

            TeacherSalesResponse response = TeacherSalesResponse.builder()
                    .courseId(course.getId())
                    .courseTitle(course.getTitle())
                    .totalAmount(totalAmount)
                    .totalCount(totalCount)
                    .build();

            responseList.add(response);
        }

        // [3] 전체 기준 정렬
        responseList.sort(new Comparator<TeacherSalesResponse>() {
            @Override
            public int compare(TeacherSalesResponse a, TeacherSalesResponse b) {
                return Integer.compare(b.getTotalAmount(), a.getTotalAmount());
            }
        });

        // [4] 수동 페이징
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), responseList.size());
        List<TeacherSalesResponse> pagedList = responseList.subList(start, end);

        // [5] 페이지 객체로 반환
        return new PageImpl<>(pagedList, pageable, responseList.size());
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
            List<PaymentDetailResponse> payments = paymentService.getPaymentsByCourseId(course.getId());

            for (PaymentDetailResponse payment : payments) {
                if (PaymentStatus.COMPLETED.name().equals(payment.getStatus())) {
                    totalSales += payment.getAmount();
                }
            }
        }
        return totalSales;
    }
}