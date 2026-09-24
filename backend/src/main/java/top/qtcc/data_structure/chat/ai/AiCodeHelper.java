package top.qtcc.data_structure.chat.ai;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * AI 编程助手核心服务类
 * 提供基础的AI对话功能，专注于编程学习和求职面试指导
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
@Service
@Slf4j
public class AiCodeHelper {

    /** 小米MIMO聊天模型 */
    @Resource
    private ChatModel chatModel;

    /**
     * 系统提示词，定义AI助手的角色和能力范围
     * 专注于编程学习和求职面试指导
     */
    private static final String SYSTEM_MESSAGE = """
            你是编程领域的小助手，帮助用户解答编程学习和求职面试相关的问题，并给出建议。重点关注 4 个方向：
            1. 规划清晰的编程学习路线
            2. 提供项目学习建议
            3. 给出程序员求职全流程指南（比如简历优化、投递技巧）
            4. 分享高频面试题和面试技巧
            请用简洁易懂的语言回答，助力用户高效学习与求职。
            """;

    /**
     * 基础对话方法
     * 处理用户的编程学习和求职面试相关问题
     * 
     * @param message 用户输入的消息
     * @return AI助手的回复内容
     */
    public String chat(String message) {
        // 构建系统消息和用户消息
        SystemMessage systemMessage = SystemMessage.from(SYSTEM_MESSAGE);
        UserMessage userMessage = UserMessage.from(message);
        
        // 调用AI模型进行对话
        ChatResponse chatResponse = chatModel.chat(systemMessage, userMessage);
        AiMessage aiMessage = chatResponse.aiMessage();
        
        // 记录AI输出日志
        log.info("AI 输出：" + aiMessage.toString());
        
        return aiMessage.text();
    }

    /**
     * 使用预定义用户消息进行对话
     * 适用于需要自定义消息格式的场景
     * 
     * @param userMessage 预定义的用户消息对象
     * @return AI助手的回复内容
     */
    public String chatWithMessage(UserMessage userMessage) {
        // 直接使用预定义的用户消息进行对话
        ChatResponse chatResponse = chatModel.chat(userMessage);
        AiMessage aiMessage = chatResponse.aiMessage();
        
        // 记录AI输出日志
        log.info("AI 输出：" + aiMessage.toString());
        
        return aiMessage.text();
    }
}
