package top.qtcc.data_structure.chat.controller;

import top.qtcc.data_structure.chat.model.request.*;
import top.qtcc.data_structure.chat.service.StreamingAlgorithmAnalysisService;
import top.qtcc.data_structure.chat.service.StreamingAlgorithmService;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 流式算法服务控制器
 * 提供算法问题解答和算法分析的流式返回REST API接口
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
@RestController
@RequestMapping("/streaming/algorithm")
public class StreamingAlgorithmController {

    @Resource
    private StreamingAlgorithmService streamingAlgorithmService;

    @Resource
    private StreamingAlgorithmAnalysisService streamingAlgorithmAnalysisService;

    /**
     * 流式解答算法问题
     * 
     * @param request 算法问题请求
     * @return 算法解答的流式返回
     */
    @PostMapping(value = "/solve", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> solveAlgorithmProblemStream(@RequestBody AlgorithmProblemRequest request) {
        return streamingAlgorithmService.solveAlgorithmProblemStream(
                request.getProblem(), 
                request.getLanguage()
        );
    }

    /**
     * 流式生成算法代码
     * 
     * @param request 算法代码生成请求
     * @return 算法代码的流式返回
     */
    @PostMapping(value = "/generate", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateAlgorithmCodeStream(@RequestBody AlgorithmCodeRequest request) {
        return streamingAlgorithmService.generateAlgorithmCodeStream(
                request.getAlgorithmName(), 
                request.getLanguage()
        );
    }

    /**
     * 流式分析算法复杂度
     * 
     * @param request 算法复杂度分析请求
     * @return 复杂度分析的流式返回
     */
    @PostMapping(value = "/analyze/complexity", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> analyzeComplexityStream(@RequestBody ComplexityAnalysisRequest request) {
        return streamingAlgorithmService.analyzeComplexityStream(request.getCode());
    }

    /**
     * 流式提供算法优化建议
     * 
     * @param request 优化建议请求
     * @return 优化建议的流式返回
     */
    @PostMapping(value = "/optimize", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getOptimizationSuggestionsStream(@RequestBody OptimizationRequest request) {
        return streamingAlgorithmService.provideOptimizationSuggestionsStream(request.getCode());
    }

    /**
     * 流式生成算法分析报告
     * 
     * @param request 算法分析报告请求
     * @return 算法分析报告的流式返回
     */
    @PostMapping(value = "/analyze/report", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateAnalysisReportStream(@RequestBody AlgorithmAnalysisRequest request) {
        return streamingAlgorithmAnalysisService.generateAnalysisReportStream(
                request.getCode(), 
                request.getLanguage()
        );
    }

    /**
     * 流式分析算法代码
     * 
     * @param request 算法分析请求
     * @return 算法分析结果的流式返回
     */
    @PostMapping(value = "/analyze", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> analyzeAlgorithmStream(@RequestBody AlgorithmAnalysisRequest request) {
        return streamingAlgorithmAnalysisService.analyzeAlgorithmStream(
                request.getCode(), 
                request.getLanguage()
        );
    }

    /**
     * 流式评估算法性能
     * 
     * @param request 算法性能评估请求
     * @return 性能评估结果的流式返回
     */
    @PostMapping(value = "/evaluate/performance", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> evaluatePerformanceStream(@RequestBody AlgorithmAnalysisRequest request) {
        return streamingAlgorithmAnalysisService.evaluatePerformanceStream(
                request.getCode(), 
                request.getLanguage()
        );
    }

    /**
     * 流式提供代码重构建议
     * 
     * @param request 代码重构请求
     * @return 重构建议的流式返回
     */
    @PostMapping(value = "/suggest/refactoring", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> suggestRefactoringStream(@RequestBody AlgorithmAnalysisRequest request) {
        return streamingAlgorithmAnalysisService.suggestRefactoringStream(
                request.getCode(), 
                request.getLanguage()
        );
    }

    /**
     * 流式比较算法实现
     * 
     * @param request 算法比较请求
     * @return 比较结果的流式返回
     */
    @PostMapping(value = "/compare", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> compareAlgorithmsStream(@RequestBody AlgorithmComparisonRequest request) {
        return streamingAlgorithmAnalysisService.compareAlgorithmsStream(
                request.getCode1(), 
                request.getCode2(), 
                request.getLanguage()
        );
    }

    /**
     * 流式生成学习路径
     * 
     * @param request 学习路径生成请求
     * @return 学习路径的流式返回
     */
    @PostMapping(value = "/generate/learning-path", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateLearningPathStream(@RequestBody AlgorithmAnalysisRequest request) {
        return streamingAlgorithmAnalysisService.generateLearningPathStream(
                request.getCode(), 
                request.getLanguage()
        );
    }
}