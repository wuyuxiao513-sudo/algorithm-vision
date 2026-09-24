package top.qtcc.data_structure.chat.controller;

import top.qtcc.data_structure.chat.ai.AiCodeHelperService;
import jakarta.annotation.Resource;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * AI 编程助手控制器
 * 提供AI对话的REST API接口
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
@RestController
@RequestMapping("/ai")
public class AiController {

    /** AI编程助手服务 */
    @Resource
    private AiCodeHelperService aiCodeHelperService;

    /**
     * 流式对话接口
     * 使用Server-Sent Events (SSE) 实现实时流式响应
     * 
     * @param memoryId 会话记忆ID，用于维护对话上下文
     * @param message 用户输入的消息内容
     * @return Server-Sent Events流，包含AI的实时回复
     */
    @GetMapping("/chat")
    public Flux<ServerSentEvent<String>> chat(int memoryId, String message) {
        // 调用AI服务获取流式响应，并转换为SSE格式
        return aiCodeHelperService.chatStream(memoryId, message)
                .map(chunk -> ServerSentEvent.<String>builder()
                        .data(chunk)
                        .build());
    }
}

