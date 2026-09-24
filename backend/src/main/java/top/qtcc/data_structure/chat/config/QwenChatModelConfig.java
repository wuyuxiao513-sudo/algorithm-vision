package top.qtcc.data_structure.chat.config;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 小米MIMO模型配置类 (OpenAI兼容)
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@Configuration
public class QwenChatModelConfig {

    @Value("${langchain4j.anthropic.api-key}")
    private String apiKey;

    @Value("${langchain4j.anthropic.base-url}")
    private String baseUrl;

    @Value("${langchain4j.anthropic.chat-model.model-name:mimo-v2.5}")
    private String chatModelName;

    @Value("${langchain4j.anthropic.streaming-chat-model.model-name:mimo-v2.5}")
    private String streamingChatModelName;

    @Bean
    public ChatModel chatModel() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl + "/v1")
                .modelName(chatModelName)
                .build();
    }

    @Bean
    public StreamingChatModel streamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl + "/v1")
                .modelName(streamingChatModelName)
                .build();
    }
}
