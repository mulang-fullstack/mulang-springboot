package yoonsome.mulang.api.admin.payment.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import yoonsome.mulang.domain.payment.entity.PaymentMethod;

import java.time.LocalDateTime;

@Data
public class PaymentSearchRequest {

    private PaymentMethod type;
    private String status;
    private String keyword;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
    private int page = 0;
    private int size = 10;

}
