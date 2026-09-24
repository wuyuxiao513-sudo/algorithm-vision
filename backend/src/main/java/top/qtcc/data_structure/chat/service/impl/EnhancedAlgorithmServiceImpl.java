package top.qtcc.data_structure.chat.service.impl;

import top.qtcc.data_structure.chat.ai.AiAlgorithmService;
import top.qtcc.data_structure.chat.algorithm.AlgorithmService;
import top.qtcc.data_structure.chat.service.AlgorithmTemplateService;
import org.springframework.stereotype.Service;

/**
 * 增强版算法服务实现
 * 整合模板服务和AI能力，提供更丰富的算法内容
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
@Service
public class EnhancedAlgorithmServiceImpl implements AlgorithmService {


    
    private final AiAlgorithmService aiAlgorithmService;
    private final AlgorithmTemplateService templateService;
    
    public EnhancedAlgorithmServiceImpl(AiAlgorithmService aiAlgorithmService, 
                                       AlgorithmTemplateService templateService) {
        this.aiAlgorithmService = aiAlgorithmService;
        this.templateService = templateService;
    }

    @Override
    public String solveAlgorithmProblem(String problem, String language) {
        // 获取相关模板内容作为上下文
        String templateContext = templateService.getTemplateByAlgorithm(problem);
        
        // 构建增强的提示词
        String enhancedPrompt = problem + "\n\n" + 
                               "可参考的算法模板内容：\n" + 
                               (templateContext.isEmpty() ? "暂无相关模板" : templateContext.substring(0, Math.min(500, templateContext.length())));
        
        return aiAlgorithmService.solveAlgorithmProblem(enhancedPrompt, language);
    }

    @Override
    public String generateAlgorithmCode(String algorithmName, String language) {
        // 获取相关模板内容
        String templateContent = templateService.getTemplateByAlgorithm(algorithmName);
        
        // 构建增强的提示词
        String enhancedPrompt = algorithmName + "\n\n" + 
                               "可参考的模板内容：\n" + 
                               (templateContent.isEmpty() ? "暂无相关模板" : templateContent);
        
        return aiAlgorithmService.generateAlgorithmCode(enhancedPrompt, language);
    }

    @Override
    public String analyzeComplexity(String code) {
        // 获取通用算法模板作为分析参考
        String templateContent = templateService.getTemplate("算法模板.md");
        
        // 构建增强的提示词
        String enhancedPrompt = code + "\n\n" + 
                               "复杂度分析参考：\n" + 
                               (templateContent.isEmpty() ? "暂无模板参考" : 
                               extractComplexityExamples(templateContent));
        
        return aiAlgorithmService.analyzeComplexity(enhancedPrompt);
    }

    @Override
    public String provideOptimizationSuggestions(String code) {
        // 获取优化相关的模板内容
        String templateContent = templateService.getTemplate("算法模板.md");
        
        // 构建增强的提示词
        String enhancedPrompt = code + "\n\n" + 
                               "优化方向参考：\n" + 
                               (templateContent.isEmpty() ? "暂无模板参考" : 
                               extractOptimizationExamples(templateContent));
        
        return aiAlgorithmService.provideOptimizationSuggestions(enhancedPrompt);
    }
    
    /**
     * 从模板内容中提取复杂度分析示例
     */
    private String extractComplexityExamples(String templateContent) {
        // 提取包含复杂度分析的部分
        if (templateContent.contains("时间复杂度") || templateContent.contains("空间复杂度")) {
            // 返回复杂度分析相关的段落
            return "模板中包含复杂度分析示例，可作为参考";
        }
        return "模板中暂无复杂度分析示例";
    }
    
    /**
     * 从模板内容中提取优化示例
     */
    private String extractOptimizationExamples(String templateContent) {
        // 提取包含优化建议的部分
        if (templateContent.contains("优化") || templateContent.contains("改进") || 
            templateContent.contains("效率") || templateContent.contains("性能")) {
            return "模板中包含优化建议示例，可作为参考";
        }
        return "模板中暂无优化建议示例";
    }
    
    /**
     * 获取算法模板统计信息
     */
    public String getTemplateStats() {
        var templates = templateService.getAllTemplates();
        return String.format("当前系统包含 %d 个算法模板，涵盖经典算法、数据结构、优化技巧等", 
                           templates.size());
    }
    
    /**
     * 获取特定算法的模板内容
     */
    public String getAlgorithmTemplate(String algorithmName) {
        return templateService.getTemplateByAlgorithm(algorithmName);
    }
}