package top.qtcc.data_structure.chat.service.impl;

import top.qtcc.data_structure.chat.ai.StreamingAiAlgorithmAnalysisService;
import top.qtcc.data_structure.chat.service.StreamingAlgorithmAnalysisService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * 流式增强算法分析服务实现类
 * 提供算法代码分析和评估的流式返回功能
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
@Slf4j
@Service
public class StreamingEnhancedAlgorithmAnalysisServiceImpl implements StreamingAlgorithmAnalysisService {

    @Resource
    private StreamingAiAlgorithmAnalysisService streamingAiAlgorithmAnalysisService;

    /**
     * 流式生成算法分析报告
     * 
     * @param code 算法代码
     * @param language 编程语言
     * @return 算法分析报告的流式返回
     */
    @Override
    public Flux<String> generateAnalysisReportStream(String code, String language) {
        log.info("开始流式生成算法分析报告，语言: {}", language);
        
        // 检测编程语言
        String detectedLanguage = detectProgrammingLanguage(code, language);
        
        // 调用流式AI服务
        return streamingAiAlgorithmAnalysisService.generateAnalysisReportStream(code, detectedLanguage)
                .doOnNext(chunk -> log.debug("流式返回分析报告片段: {}", chunk))
                .doOnError(error -> log.error("流式生成分析报告失败: {}", error.getMessage()))
                .doOnComplete(() -> log.info("流式生成分析报告完成"));
    }

    /**
     * 流式分析算法代码
     * 
     * @param code 算法代码
     * @param language 编程语言
     * @return 算法分析结果的流式返回
     */
    @Override
    public Flux<String> analyzeAlgorithmStream(String code, String language) {
        log.info("开始流式分析算法代码，语言: {}", language);
        
        // 检测编程语言
        String detectedLanguage = detectProgrammingLanguage(code, language);
        
        // 调用流式AI服务
        return streamingAiAlgorithmAnalysisService.analyzeAlgorithmStream(code, detectedLanguage)
                .doOnNext(chunk -> log.debug("流式返回算法分析片段: {}", chunk))
                .doOnError(error -> log.error("流式分析算法代码失败: {}", error.getMessage()))
                .doOnComplete(() -> log.info("流式分析算法代码完成"));
    }

    /**
     * 流式评估算法性能
     * 
     * @param code 算法代码
     * @param language 编程语言
     * @return 性能评估结果的流式返回
     */
    @Override
    public Flux<String> evaluatePerformanceStream(String code, String language) {
        log.info("开始流式评估算法性能，语言: {}", language);
        
        // 检测编程语言
        String detectedLanguage = detectProgrammingLanguage(code, language);
        
        // 调用流式AI服务
        return streamingAiAlgorithmAnalysisService.evaluatePerformanceStream(code, detectedLanguage)
                .doOnNext(chunk -> log.debug("流式返回性能评估片段: {}", chunk))
                .doOnError(error -> log.error("流式评估算法性能失败: {}", error.getMessage()))
                .doOnComplete(() -> log.info("流式评估算法性能完成"));
    }

    /**
     * 流式提供代码重构建议
     * 
     * @param code 算法代码
     * @param language 编程语言
     * @return 重构建议的流式返回
     */
    @Override
    public Flux<String> suggestRefactoringStream(String code, String language) {
        log.info("开始流式提供代码重构建议，语言: {}", language);
        
        // 检测编程语言
        String detectedLanguage = detectProgrammingLanguage(code, language);
        
        // 调用流式AI服务
        return streamingAiAlgorithmAnalysisService.suggestRefactoringStream(code, detectedLanguage)
                .doOnNext(chunk -> log.debug("流式返回重构建议片段: {}", chunk))
                .doOnError(error -> log.error("流式提供重构建议失败: {}", error.getMessage()))
                .doOnComplete(() -> log.info("流式提供重构建议完成"));
    }

    /**
     * 流式比较算法实现
     * 
     * @param code1 第一个算法代码
     * @param code2 第二个算法代码
     * @param language 编程语言
     * @return 比较结果的流式返回
     */
    @Override
    public Flux<String> compareAlgorithmsStream(String code1, String code2, String language) {
        log.info("开始流式比较算法实现，语言: {}", language);
        
        // 检测编程语言
        String detectedLanguage = detectProgrammingLanguage(code1, language);
        
        // 调用流式AI服务
        return streamingAiAlgorithmAnalysisService.compareAlgorithmsStream(code1, code2, detectedLanguage)
                .doOnNext(chunk -> log.debug("流式返回算法比较片段: {}", chunk))
                .doOnError(error -> log.error("流式比较算法实现失败: {}", error.getMessage()))
                .doOnComplete(() -> log.info("流式比较算法实现完成"));
    }

    /**
     * 流式生成学习路径
     * 
     * @param code 算法代码
     * @param language 编程语言
     * @return 学习路径的流式返回
     */
    @Override
    public Flux<String> generateLearningPathStream(String code, String language) {
        log.info("开始流式生成学习路径，语言: {}", language);
        
        // 检测编程语言
        String detectedLanguage = detectProgrammingLanguage(code, language);
        
        // 调用流式AI服务
        return streamingAiAlgorithmAnalysisService.generateLearningPathStream(code, detectedLanguage)
                .doOnNext(chunk -> log.debug("流式返回学习路径片段: {}", chunk))
                .doOnError(error -> log.error("流式生成学习路径失败: {}", error.getMessage()))
                .doOnComplete(() -> log.info("流式生成学习路径完成"));
    }

    /**
     * 检测编程语言
     */
    private String detectProgrammingLanguage(String code, String specifiedLanguage) {
        if (specifiedLanguage != null && !specifiedLanguage.trim().isEmpty()) {
            return specifiedLanguage;
        }
        
        // 简单的语言检测逻辑
        if (code.contains("public class") || code.contains("import java")) {
            return "java";
        } else if (code.contains("def ") || code.contains("import ") && code.contains("numpy")) {
            return "python";
        } else if (code.contains("function") || code.contains("const ") || code.contains("let ")) {
            return "javascript";
        } else if (code.contains("#include") || code.contains("using namespace")) {
            return "cpp";
        } else {
            return "unknown";
        }
    }
}