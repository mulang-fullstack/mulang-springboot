package yoonsome.mulang.domain.language.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yoonsome.mulang.domain.language.entity.Language;
import yoonsome.mulang.domain.language.repository.LanguageRepository;

@RequiredArgsConstructor
@Service
public class LanguageServiceImpl implements LanguageService {
    @Autowired
    private final LanguageRepository languageRepository;

    @Override
    public String getLanguageNameById(Long id){
        String languageName = languageRepository.findById(id).get().getName();
        return languageName;
    }
    @Override
    public Language getById(Long id) {
        return languageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 언어가 존재하지 않습니다. id=" + id));
    }

}
