package yoonsome.mulang.domain.language.service;

import yoonsome.mulang.domain.language.entity.Language;

public interface LanguageService {
    //언어 ID로 언어 이름 조회
    String getLanguageNameById(Long id);
    //언어 ID로 Language 엔티티 조회
    Language getById(Long id);
}
