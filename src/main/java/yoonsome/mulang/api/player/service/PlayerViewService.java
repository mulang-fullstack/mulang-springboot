package yoonsome.mulang.api.player.service;

import yoonsome.mulang.domain.user.entity.User;
import java.util.Map;

/**
 * VOD 재생 페이지 데이터 조립 서비스
 */
public interface PlayerViewService {

    //강좌와 강의 목록, 현재 재생 강의 정보를 조회하여 반환한다.
    Map<String, Object> getPlayerView(Long courseId, Long lectureId, User user);
}
