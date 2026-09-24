package top.qtcc.data_structure.chat.controller;

import top.qtcc.data_structure.chat.model.AlgorithmAnalysisReport;
import top.qtcc.data_structure.chat.model.request.*;
import top.qtcc.data_structure.chat.model.response.*;
import top.qtcc.data_structure.chat.service.impl.EnhancedAlgorithmAnalysisServiceImpl;
import top.qtcc.data_structure.chat.service.impl.EnhancedAlgorithmServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 算法服务控制器
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {

    @Resource
    private EnhancedAlgorithmServiceImpl algorithmService;
    
    @Resource
    private EnhancedAlgorithmAnalysisServiceImpl algorithmAnalysisService;

    /**
     * 解答算法问题
     *
     * @param request 算法问题请求
     * @return 算法解答结果
     */
    @PostMapping("/solve")
    public AlgorithmSolutionResponse solveAlgorithmProblem(@RequestBody AlgorithmProblemRequest request) {
        String solution = algorithmService.solveAlgorithmProblem(request.getProblem(), request.getLanguage());
        return new AlgorithmSolutionResponse(solution);
    }

    /**
     * 生成算法代码
     *
     * @param request 算法代码生成请求
     * @return 生成的算法代码
     */
    @PostMapping("/generate")
    public AlgorithmCodeResponse generateAlgorithmCode(@RequestBody AlgorithmCodeRequest request) {
        String code = algorithmService.generateAlgorithmCode(request.getAlgorithmName(), request.getLanguage());
        return new AlgorithmCodeResponse(code);
    }

    /**
     * 分析算法复杂度
     *
     * @param request 算法复杂度分析请求
     * @return 复杂度分析结果
     */
    @PostMapping("/analyze/complexity")
    public ComplexityAnalysisResponse analyzeComplexity(@RequestBody ComplexityAnalysisRequest request) {
        String analysis = algorithmService.analyzeComplexity(request.getCode());
        return new ComplexityAnalysisResponse(analysis);
    }

    /**
     * 获取算法优化建议
     *
     * @param request 优化建议请求
     * @return 优化建议结果
     */
    @PostMapping("/optimize")
    public OptimizationResponse getOptimizationSuggestions(@RequestBody OptimizationRequest request) {
        String suggestions = algorithmService.provideOptimizationSuggestions(request.getCode());
        return new OptimizationResponse(suggestions);
    }

    /**
     * 生成完整算法分析报告
     *
     * @param request 算法分析报告请求
     * @return 完整的算法分析报告
     */
    @PostMapping("/analyze/report")
    public AlgorithmAnalysisReportResponse analyzeAlgorithm(@RequestBody AlgorithmAnalysisRequest request) {
        AlgorithmAnalysisReport report = algorithmAnalysisService.analyzeAlgorithm(request.getCode(), request.getLanguage());
        return new AlgorithmAnalysisReportResponse(report);
    }

    /**
     * 评估算法星级
     *
     * @param request 算法星级评估请求
     * @return 算法星级
     */
    @PostMapping("/evaluate/star")
    public StarRatingResponse evaluateAlgorithmStar(@RequestBody StarRatingRequest request) {
        int starRating = algorithmAnalysisService.evaluateAlgorithmStar(request.getCode());
        return new StarRatingResponse(starRating);
    }

    /**
     * 获取算法优化方向
     * 
     * @param request 优化方向请求
     * @return 优化方向列表
     */
    @PostMapping("/optimize/directions")
    public OptimizationDirectionsResponse getOptimizationDirections(@RequestBody OptimizationDirectionsRequest request) {
        List<String> directions = algorithmAnalysisService.getOptimizationDirections(request.getCode());
        return new OptimizationDirectionsResponse(directions);
    }

    /**
     * 识别算法弱点
     * 
     * @param request 算法弱点识别请求
     * @return 算法弱点列表
     */
    @PostMapping("/identify/weaknesses")
    public WeaknessesResponse identifyWeaknesses(@RequestBody WeaknessesRequest request) {
        List<String> weaknesses = algorithmAnalysisService.identifyWeaknesses(request.getCode());
        return new WeaknessesResponse(weaknesses);
    }

    /**
     * 获取算法模板统计信息
     * 
     * @return 模板统计信息
     */
    @GetMapping("/templates/stats")
    public String getTemplateStats() {
        return algorithmService.getTemplateStats();
    }


    /**
     * 获取特定算法的模板内容
     * 
     * @param algorithmName 算法名称
     * @return 算法模板内容
     */
    @GetMapping("/templates/{algorithmName}")
    public String getAlgorithmTemplate(@PathVariable String algorithmName) {
        return algorithmService.getAlgorithmTemplate(algorithmName);
    }

    /**
     * 获取算法分析的情绪价值统计
     * 
     * @return 情绪价值统计信息
     */
    @GetMapping("/analysis/emotion-stats")
    public String getAnalysisEmotionStats() {
        return algorithmAnalysisService.getAnalysisEmotionStats();
    }

    /**
     * 获取增强版算法分析报告（包含情绪价值）
     * 
     * @param request 算法分析报告请求
     * @return 包含情绪价值的算法分析报告
     */
    @PostMapping("/analyze/enhanced-report")
    public AlgorithmAnalysisReportResponse analyzeAlgorithmEnhanced(@RequestBody AlgorithmAnalysisRequest request) {
        AlgorithmAnalysisReport report = algorithmAnalysisService.analyzeAlgorithm(request.getCode(), request.getLanguage());
        return new AlgorithmAnalysisReportResponse(report);
    }
}

