package top.qtcc.data_structure.chat.service;

import reactor.core.publisher.Flux;

/**
 * 流式算法服务接口
 * 提供算法问题解答和代码生成的流式返回功能
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
public interface StreamingAlgorithmService {

    /**
     * 流式解答算法问题
     * 
     * @param problem 算法问题描述
     * @param language 编程语言
     * @return 算法解答的流式返回
     */
    Flux<String> solveAlgorithmProblemStream(String problem, String language);

    /**
     * 流式生成算法代码
     * 
     * @param algorithmName 算法名称
     * @param language 编程语言
     * @return 算法代码的流式返回
     */
    Flux<String> generateAlgorithmCodeStream(String algorithmName, String language);

    /**
     * 流式分析算法复杂度
     * 
     * @param code 算法代码
     * @return 复杂度分析的流式返回
     */
    Flux<String> analyzeComplexityStream(String code);

    /**
     * 流式提供算法优化建议
     * 
     * @param code 算法代码
     * @return 优化建议的流式返回
     */
    Flux<String> provideOptimizationSuggestionsStream(String code);
}