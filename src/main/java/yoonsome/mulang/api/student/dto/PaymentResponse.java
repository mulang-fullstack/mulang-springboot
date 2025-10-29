package yoonsome.mulang.api.student.dto;

import lombok.Data;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.payment.entity.Payment;
import yoonsome.mulang.domain.user.entity.User;


@Data
public class PaymentResponse {
    private Long paymentId;
    private Course course;
    private User student;
    private String paymentMethod;

    public static PaymentResponse from(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(payment.getId());        
        response.setCourse(payment.getCourse());            
        response.setStudent(payment.getUser());
        if (payment.getPaymentMethod() != null) {
            response.setPaymentMethod(payment.getPaymentMethod().name());// enum 타입이라서 String 변환과정필요
        } else {
            response.setPaymentMethod("미확인"); // 또는 null
        }
        return response;
    }
}
