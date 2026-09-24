package top.qtcc.data_structure.chat.service;

import reactor.core.publisher.Flux;

/**
 * 流式算法分析服务接口
 * 提供算法代码分析和评估的流式返回功能
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
public interface StreamingAlgorithmAnalysisService {

    /**
     * 流式生成算法分析报告
     * 
     * @param code 算法代码
     * @param language 编程语言
     * @return 算法分析报告的流式返回
     */
    Flux<String> generateAnalysisReportStream(String code, String language);

    /**
     * 流式分析算法代码
     * 
     * @param code 算法代码
     * @param language 编程语言
     * @return 算法分析结果的流式返回
     */
    Flux<String> analyzeAlgorithmStream(String code, String language);

    /**
     * 流式评估算法性能
     * 
     * @param code 算法代码
     * @param language 编程语言
     * @return 性能评估结果的流式返回
     */
    Flux<String> evaluatePerformanceStream(String code, String language);

    /**
     * 流式提供代码重构建议
     * 
     * @param code 算法代码
     * @param language 编程语言
     * @return 重构建议的流式返回
     */
    Flux<String> suggestRefactoringStream(String code, String language);

    /**
     * 流式比较算法实现
     * 
     * @param code1 第一个算法代码
     * @param code2 第二个算法代码
     * @param language 编程语言
     * @return 比较结果的流式返回
     */
    Flux<String> compareAlgorithmsStream(String code1, String code2, String language);

    /**
     * 流式生成学习路径
     * 
     * @param code 算法代码
     * @param language 编程语言
     * @return 学习路径的流式返回
     */
    Flux<String> generateLearningPathStream(String code, String language);
}