package yoonsome.mulang.infra.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAI API 설정
 * Spring AI의 자동 설정을 사용합니다.
 */
@Configuration
public class OpenAiConfig {

    /**
     * ChatClient Bean 생성 (선택사항, 더 간편한 API 사용)
     */
    @Bean
    public ChatClient chatClient(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }
}