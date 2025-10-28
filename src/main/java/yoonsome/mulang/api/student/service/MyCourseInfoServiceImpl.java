package yoonsome.mulang.api.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.student.dto.MycourseResponse;
import yoonsome.mulang.domain.enrollment.entity.Enrollment;
import yoonsome.mulang.domain.enrollment.repository.EnrollmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyCourseInfoServiceImpl implements MyCourseInfoService {

    private final EnrollmentRepository enrollmentRepository;

    @Override
    public List<MycourseResponse> findByUserId(Long userId) {

        List<Enrollment> enrollments = enrollmentRepository
                .findByUserId(userId);

        return enrollments.stream()
                .map(MycourseResponse::from)
                .collect(Collectors.toList());
    }
}
