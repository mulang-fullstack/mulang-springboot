package yoonsome.mulang.global.util;

import java.util.regex.Pattern;

public class UserValidator {
/**
 * 회원가입 유효성 검증 클래스
 * 클라이언트 측 검증과 동일한 규칙 적용
 */
    // 정규식 패턴
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/~`|\\\\]{6,16}$");

    /**
     * 이름 유효성 검증 (2~4자)
     */
    public static ValidationResult validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return ValidationResult.error("이름을 입력해주세요.");
        }

        String trimmed = username.trim();
        if (trimmed.length() < 2) {
            return ValidationResult.error("이름은 2자 이상이어야 합니다.");
        }
        if (trimmed.length() > 4) {
            return ValidationResult.error("이름은 4자 이하이어야 합니다.");
        }

        return ValidationResult.success();
    }

    /**
     * 닉네임 유효성 검증 (2~8자)
     */
    public static ValidationResult validateNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            return ValidationResult.error("닉네임을 입력해주세요.");
        }

        String trimmed = nickname.trim();
        if (trimmed.length() < 2) {
            return ValidationResult.error("닉네임은 2자 이상이어야 합니다.");
        }
        if (trimmed.length() > 8) {
            return ValidationResult.error("닉네임은 8자 이하이어야 합니다.");
        }

        return ValidationResult.success();
    }

    /**
     * 이메일 유효성 검증 (형식 + 최대 50자)
     */
    public static ValidationResult validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return ValidationResult.error("이메일을 입력해주세요.");
        }

        String trimmed = email.trim();
        if (trimmed.length() > 50) {
            return ValidationResult.error("이메일은 50자 이하이어야 합니다.");
        }

        if (!EMAIL_PATTERN.matcher(trimmed).matches()) {
            return ValidationResult.error("올바른 이메일 형식이 아닙니다.");
        }

        return ValidationResult.success();
    }

    /**
     * 비밀번호 유효성 검증 (6~16자, 영문+숫자 필수)
     */
    public static ValidationResult validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return ValidationResult.error("비밀번호를 입력해주세요.");
        }

        if (password.length() < 6) {
            return ValidationResult.error("비밀번호는 6자 이상이어야 합니다.");
        }
        if (password.length() > 16) {
            return ValidationResult.error("비밀번호는 16자 이하이어야 합니다.");
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return ValidationResult.error("비밀번호는 영문자와 숫자를 포함해야 합니다.");
        }

        return ValidationResult.success();
    }

    /**
     * 비밀번호 확인 검증
     */
    public static ValidationResult validatePasswordConfirm(String password, String passwordConfirm) {
        if (passwordConfirm == null || passwordConfirm.isEmpty()) {
            return ValidationResult.error("비밀번호 확인을 입력해주세요.");
        }

        if (!password.equals(passwordConfirm)) {
            return ValidationResult.error("비밀번호가 일치하지 않습니다.");
        }

        return ValidationResult.success();
    }

    /**
     * 계정 타입 검증
     */
    public static ValidationResult validateAccountType(String accountType) {
        if (accountType == null || accountType.trim().isEmpty()) {
            return ValidationResult.error("계정 타입을 선택해주세요.");
        }

        if (!accountType.equals("S") && !accountType.equals("T")) {
            return ValidationResult.error("올바른 계정 타입이 아닙니다.");
        }

        return ValidationResult.success();
    }

    /**
     * 전체 회원가입 데이터 검증
     */
    public static ValidationResult validateSignupData(
            String username,
            String nickname,
            String email,
            String password,
            String passwordConfirm,
            String accountType) {

        ValidationResult result;

        // 이름 검증
        result = validateUsername(username);
        if (!result.isValid()) return result;

        // 닉네임 검증
        result = validateNickname(nickname);
        if (!result.isValid()) return result;

        // 이메일 검증
        result = validateEmail(email);
        if (!result.isValid()) return result;

        // 비밀번호 검증
        result = validatePassword(password);
        if (!result.isValid()) return result;

        // 비밀번호 확인 검증
        result = validatePasswordConfirm(password, passwordConfirm);
        if (!result.isValid()) return result;

        // 계정 타입 검증
        result = validateAccountType(accountType);
        if (!result.isValid()) return result;

        return ValidationResult.success();
    }

    /**
     * 유효성 검증 결과 클래스
     */
    public static class ValidationResult {
        private boolean valid;
        private String message;

        private ValidationResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }

        public static ValidationResult success() {
            return new ValidationResult(true, null);
        }

        public static ValidationResult error(String message) {
            return new ValidationResult(false, message);
        }

        public boolean isValid() {
            return valid;
        }

        public String getMessage() {
            return message;
        }
    }
}