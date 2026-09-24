package top.qtcc.data_structure.chat.ai;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AI 算法服务工厂配置
 * 负责创建和配置算法相关的AI服务实例
 *
 * @author qiutuan
 * @date 2025/11/26
 */
@Configuration
public class AiAlgorithmServiceFactory {

    @Resource
    private ChatModel chatModel;

    @Resource
    private StreamingChatModel streamingChatModel;

    /**
     * 创建AI算法服务实例
     * 提供算法问题解答和代码生成功能
     */
    @Bean
    public AiAlgorithmService aiAlgorithmService() {
        // 配置会话记忆，保留最近5条对话记录
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(5);

        // 构造 AI Service
        AiAlgorithmService aiAlgorithmService = AiServices.builder(AiAlgorithmService.class)
                .chatModel(chatModel)
                .streamingChatModel(streamingChatModel)
                .chatMemory(chatMemory)
                .chatMemoryProvider(memoryId ->
                        MessageWindowChatMemory.withMaxMessages(10)) // 每个会话独立存储
                .build();

        return aiAlgorithmService;
    }

    /**
     * 创建AI算法分析服务实例
     * 提供算法代码分析和评估功能
     */
    @Bean
    public AiAlgorithmAnalysisService aiAlgorithmAnalysisService() {
        // 配置会话记忆，保留最近3条对话记录
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(3);

        // 构造AI算法分析服务
        AiAlgorithmAnalysisService aiAlgorithmAnalysisService = AiServices.builder(AiAlgorithmAnalysisService.class)
                .chatModel(chatModel)
                .streamingChatModel(streamingChatModel)
                .chatMemory(chatMemory)
                .chatMemoryProvider(memoryId ->
                        MessageWindowChatMemory.withMaxMessages(3)) // 每个会话独立存储
                .build();

        return aiAlgorithmAnalysisService;
    }

    /**
     * 创建流式AI算法服务实例
     * 提供算法问题解答和代码生成的流式返回功能
     */
    @Bean
    public StreamingAiAlgorithmService streamingAiAlgorithmService() {
        // 配置会话记忆，保留最近5条对话记录
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(5);

        // 构造流式AI算法服务
        StreamingAiAlgorithmService streamingAiAlgorithmService = AiServices.builder(StreamingAiAlgorithmService.class)
                .chatModel(chatModel)
                .streamingChatModel(streamingChatModel)
                .chatMemory(chatMemory)
                .chatMemoryProvider(memoryId ->
                        MessageWindowChatMemory.withMaxMessages(10)) // 每个会话独立存储
                .build();

        return streamingAiAlgorithmService;
    }

    /**
     * 创建流式AI算法分析服务实例
     * 提供算法代码分析和评估的流式返回功能
     */
    @Bean
    public StreamingAiAlgorithmAnalysisService streamingAiAlgorithmAnalysisService() {
        // 配置会话记忆，保留最近3条对话记录
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(3);

        // 构造流式AI算法分析服务
        StreamingAiAlgorithmAnalysisService streamingAiAlgorithmAnalysisService = AiServices.builder(StreamingAiAlgorithmAnalysisService.class)
                .chatModel(chatModel)
                .streamingChatModel(streamingChatModel)
                .chatMemory(chatMemory)
                .chatMemoryProvider(memoryId ->
                        MessageWindowChatMemory.withMaxMessages(3)) // 每个会话独立存储
                .build();

        return streamingAiAlgorithmAnalysisService;
    }
}