package yoonsome.mulang.api.teacher.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yoonsome.mulang.api.teacher.dto.TeacherSalesResponse;


public interface TeacherSalesService {

    Page<TeacherSalesResponse> getTeacherSalesPage(Long teacherId, Pageable pageable);

    int getTeacherTotalSales(Long teacherId);
}
