package yoonsome.mulang.domain.course.entity;

import lombok.Getter;

@Getter
public enum StatusType {
    PUBLIC("public", "공개"),
    PRIVATE("private", "비공개"),
    PENDING("pending", "심사중"),
    REJECTED("rejected", "거절됨");

    private final String badgeClass;
    private final String badgeText;

    StatusType(String badgeClass, String badgeText) {
        this.badgeClass = badgeClass;
        this.badgeText = badgeText;
    }
}