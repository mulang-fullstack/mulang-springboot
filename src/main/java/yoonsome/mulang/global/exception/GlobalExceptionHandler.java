package yoonsome.mulang.infra.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @PreAuthorize에서 발생하는 AccessDeniedException 처리
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(AccessDeniedException ex) {
        log.warn("Access Denied: {}", ex.getMessage());

        ModelAndView mav = new ModelAndView();
        mav.setViewName("error/access-denied");
        mav.addObject("errorMessage", "접근 권한이 없습니다.");
        return mav;
    }

    /**
     * 기타 모든 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        log.error("Unexpected error: ", ex);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("error/error");
        mav.addObject("errorMessage", "요청을 처리하는 중 오류가 발생했습니다.");
        return mav;
    }
}