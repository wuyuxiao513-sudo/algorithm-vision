package top.qtcc.data_structure.chat.ai;

import dev.langchain4j.service.*;
import reactor.core.publisher.Flux;

/**
 * AI 编程助手服务接口
 * 提供基础的AI对话功能，专注于编程学习和求职面试指导
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
public interface AiCodeHelperService {

    /**
     * 普通对话接口
     * 用于处理用户的编程学习和求职面试相关问题
     * 
     * @param userMessage 用户输入的消息
     * @return AI助手的回复内容
     */
    @SystemMessage(fromResource = "system-prompt.txt")
    String chat(String userMessage);

    /**
     * 流式对话接口
     * 提供实时的流式响应，提升用户体验
     * 
     * @param memoryId 会话记忆ID，用于维护对话上下文
     * @param userMessage 用户输入的消息
     * @return 流式响应的消息流
     */
    Flux<String> chatStream(@MemoryId int memoryId, @UserMessage String userMessage);
}