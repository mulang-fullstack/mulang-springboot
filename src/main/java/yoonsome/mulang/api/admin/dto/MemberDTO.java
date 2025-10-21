// MemberDTO.java
package yoonsome.mulang.api.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 회원 정보 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String name;
    private String email;
    private String role;  // USER, TUTOR
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}