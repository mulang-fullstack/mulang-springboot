package yoonsome.mulang.domain.coursefavorite.service;

import yoonsome.mulang.domain.coursefavorite.entity.CourseFavorite;

import java.util.Set;

public interface CourseFavoriteService {
    /*찜 존재 여부 확인*/
    boolean existsCourseFavorite(Long studentId, Long courseId);
    /*이미 찜한 강좌라면 찜 제거, 찜하지 않은 강좌라면 찜 추가*/
    void addOrRemoveCourseFavorite(Long studentId, Long courseId);
    /*해당 user 찜한 courseId 가져오기*/
    Set<Long> getCourseIdsByUserId(Long studentId);
}
