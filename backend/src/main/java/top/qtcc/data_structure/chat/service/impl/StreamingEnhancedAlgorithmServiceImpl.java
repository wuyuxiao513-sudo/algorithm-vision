package top.qtcc.data_structure.chat.service.impl;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import top.qtcc.data_structure.chat.ai.StreamingAiAlgorithmService;
import top.qtcc.data_structure.chat.service.AlgorithmTemplateService;
import top.qtcc.data_structure.chat.service.StreamingAlgorithmService;

/**
 * 流式增强算法服务实现类
 * 提供算法问题解答和代码生成的流式返回功能
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
@Slf4j
@Service
public class StreamingEnhancedAlgorithmServiceImpl implements StreamingAlgorithmService {

    @Resource
    private StreamingAiAlgorithmService streamingAiAlgorithmService;

    @Resource
    private AlgorithmTemplateService templateService;

    /**
     * 流式解答算法问题
     * 
     * @param problem 算法问题描述
     * @param language 编程语言
     * @return 算法解答的流式返回
     */
    @Override
    public Flux<String> solveAlgorithmProblemStream(String problem, String language) {
        log.info("开始流式解答算法问题: {}, 语言: {}", problem, language);
        
        // 获取相关模板内容作为上下文
        String templateContext = templateService.getTemplateByAlgorithm(problem);
        
        // 构建增强提示词
        String enhancedPrompt = buildEnhancedPrompt(problem, language, templateContext);
        
        // 调用流式AI服务
        return streamingAiAlgorithmService.solveAlgorithmProblemStream(enhancedPrompt, language)
                .doOnNext(chunk -> log.debug("流式返回算法解答片段: {}", chunk))
                .doOnError(error -> log.error("流式解答算法问题失败: {}", error.getMessage()))
                .doOnComplete(() -> log.info("流式解答算法问题完成"));
    }

    /**
     * 流式生成算法代码
     * 
     * @param algorithmName 算法名称
     * @param language 编程语言
     * @return 算法代码的流式返回
     */
    @Override
    public Flux<String> generateAlgorithmCodeStream(String algorithmName, String language) {
        log.info("开始流式生成算法代码: {}, 语言: {}", algorithmName, language);
        
        // 获取相关模板内容作为上下文
        String templateContext = templateService.getTemplateByAlgorithm(algorithmName);
        
        // 构建增强提示词
        String enhancedPrompt = buildEnhancedPrompt(algorithmName, language, templateContext);
        
        // 调用流式AI服务
        return streamingAiAlgorithmService.generateAlgorithmCodeStream(enhancedPrompt, language)
                .doOnNext(chunk -> log.debug("流式返回算法代码片段: {}", chunk))
                .doOnError(error -> log.error("流式生成算法代码失败: {}", error.getMessage()))
                .doOnComplete(() -> log.info("流式生成算法代码完成"));
    }

    /**
     * 流式分析算法复杂度
     * 
     * @param code 算法代码
     * @return 复杂度分析的流式返回
     */
    @Override
    public Flux<String> analyzeComplexityStream(String code) {
        log.info("开始流式分析算法复杂度");
        
        // 从模板中提取复杂度分析参考信息
        String complexityReference = templateService.getTemplate("算法模板.md");
        
        // 构建增强提示词
        String enhancedPrompt = buildComplexityAnalysisPrompt(code, complexityReference);
        
        // 调用流式AI服务
        return streamingAiAlgorithmService.analyzeComplexityStream(enhancedPrompt)
                .doOnNext(chunk -> log.debug("流式返回复杂度分析片段: {}", chunk))
                .doOnError(error -> log.error("流式分析算法复杂度失败: {}", error.getMessage()))
                .doOnComplete(() -> log.info("流式分析算法复杂度完成"));
    }

    /**
     * 流式提供算法优化建议
     * 
     * @param code 算法代码
     * @return 优化建议的流式返回
     */
    @Override
    public Flux<String> provideOptimizationSuggestionsStream(String code) {
        log.info("开始流式提供算法优化建议");
        
        // 从模板中提取优化方向参考信息
        String optimizationReference = templateService.getTemplate("算法模板.md");
        
        // 构建增强提示词
        String enhancedPrompt = buildOptimizationPrompt(code, optimizationReference);
        
        // 调用流式AI服务
        return streamingAiAlgorithmService.provideOptimizationSuggestionsStream(enhancedPrompt)
                .doOnNext(chunk -> log.debug("流式返回优化建议片段: {}", chunk))
                .doOnError(error -> log.error("流式提供优化建议失败: {}", error.getMessage()))
                .doOnComplete(() -> log.info("流式提供优化建议完成"));
    }

    /**
     * 构建增强提示词
     */
    private String buildEnhancedPrompt(String content, String language, String templateContext) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("基于以下算法模板库的上下文信息：\n");
        prompt.append(templateContext).append("\n\n");
        prompt.append("请为以下内容提供专业的算法解答：\n");
        prompt.append(content).append("\n\n");
        prompt.append("编程语言要求：").append(language).append("\n");
        
        // 限制上下文长度
        if (prompt.length() > 500) {
            prompt.setLength(500);
            prompt.append("...（内容已截断）");
        }
        
        return prompt.toString();
    }

    /**
     * 构建复杂度分析提示词
     */
    private String buildComplexityAnalysisPrompt(String code, String complexityReference) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("基于以下复杂度分析参考信息：\n");
        prompt.append(complexityReference).append("\n\n");
        prompt.append("请分析以下算法代码的复杂度：\n");
        prompt.append(code).append("\n\n");
        prompt.append("要求：使用大O表示法，提供详细的推导过程");
        
        return prompt.toString();
    }

    /**
     * 构建优化提示词
     */
    private String buildOptimizationPrompt(String code, String optimizationReference) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("基于以下优化方向参考信息：\n");
        prompt.append(optimizationReference).append("\n\n");
        prompt.append("请为以下算法代码提供优化建议：\n");
        prompt.append(code).append("\n\n");
        prompt.append("要求：提供具体的优化方案和代码示例");
        
        return prompt.toString();
    }
}