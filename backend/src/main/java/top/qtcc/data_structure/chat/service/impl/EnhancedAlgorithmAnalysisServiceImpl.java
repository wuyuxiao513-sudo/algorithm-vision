package top.qtcc.data_structure.chat.service.impl;

import top.qtcc.data_structure.chat.ai.AiAlgorithmAnalysisService;
import top.qtcc.data_structure.chat.algorithm.AlgorithmAnalysisService;
import top.qtcc.data_structure.chat.model.AlgorithmAnalysisReport;
import top.qtcc.data_structure.chat.model.SpaceComplexity;
import top.qtcc.data_structure.chat.model.TimeComplexity;
import top.qtcc.data_structure.chat.service.AlgorithmTemplateService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 增强版算法分析服务实现
 * 提供更好的用户体验和情绪价值，整合模板服务和智能分析
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
@Service
public class EnhancedAlgorithmAnalysisServiceImpl implements AlgorithmAnalysisService {
    
    private final AiAlgorithmAnalysisService aiAlgorithmAnalysisService;
    private final AlgorithmTemplateService templateService;
    private final Random random = new Random();
    
    public EnhancedAlgorithmAnalysisServiceImpl(AiAlgorithmAnalysisService aiAlgorithmAnalysisService, 
                                              AlgorithmTemplateService templateService) {
        this.aiAlgorithmAnalysisService = aiAlgorithmAnalysisService;
        this.templateService = templateService;
    }

    /**
     *  增强版算法分析
     *
     * @param code 算法代码
     * @param language 编程语言
     * @return {@link AlgorithmAnalysisReport }
     */
    @Override
    public AlgorithmAnalysisReport analyzeAlgorithm(String code, String language) {

        if (language == null || language.isEmpty()) {
            language = detectProgrammingLanguage(code);
        }


        
        // 获取相关模板内容作为分析参考
        String templateContext = extractRelevantTemplateContext(code);
        
        // 构建增强的提示词
        String enhancedPrompt = code + "\n\n" + 
                               "分析参考：\n" + 
                               templateContext + "\n\n" +
                               "请使用温暖、鼓励的语言进行分析，既要专业准确，又要体现对用户努力的认可。";
        
        String analysisResult = aiAlgorithmAnalysisService.generateAnalysisReport(enhancedPrompt, language);
        return parseEnhancedAnalysisReport(analysisResult, code);
    }

    /**
     *  分析时间复杂度
     *
     * @param code 算法代码
     * @return {@link TimeComplexity }
     */
    @Override
    public TimeComplexity analyzeTimeComplexity(String code) {
        AlgorithmAnalysisReport report = analyzeAlgorithm(code, null);
        return report.getTimeComplexity();
    }

    /**
     *  分析空间复杂度
     *
     * @param code 算法代码
     * @return {@link SpaceComplexity }
     */
    @Override
    public SpaceComplexity analyzeSpaceComplexity(String code) {
        AlgorithmAnalysisReport report = analyzeAlgorithm(code, null);
        return report.getSpaceComplexity();
    }

    /**
     *  评估算法星级
     *
     * @param code 算法代码
     * @return int 星级评分
     */
    @Override
    public int evaluateAlgorithmStar(String code) {
        AlgorithmAnalysisReport report = analyzeAlgorithm(code, null);
        return report.getStarRating();
    }

    /**
     *  获取优化方向
     *
     * @param code 算法代码
     * @return {@link List }<{@link String }> 优化方向列表
     */
    @Override
    public List<String> getOptimizationDirections(String code) {
        AlgorithmAnalysisReport report = analyzeAlgorithm(code, null);
        return report.getOptimizationDirections();
    }

    /**
     *  识别算法弱点
     *
     * @param code 算法代码
     * @return {@link List }<{@link String }>
     */
    @Override
    public List<String> identifyWeaknesses(String code) {
        AlgorithmAnalysisReport report = analyzeAlgorithm(code, null);
        return report.getWeaknesses();
    }

    /**
     * 解析增强版的算法分析报告
     *
     * @param analysisResult AI生成的分析结果
     * @param code 算法代码
     * @return {@link AlgorithmAnalysisReport }
     */
    private AlgorithmAnalysisReport parseEnhancedAnalysisReport(String analysisResult, String code) {
        AlgorithmAnalysisReport report = new AlgorithmAnalysisReport();
        
        // 解析算法名称
        report.setAlgorithmName(extractAlgorithmName(code));
        
        // 解析时间复杂度
        TimeComplexity timeComplexity = new TimeComplexity();
        timeComplexity.setBestCase(extractTimeComplexity(analysisResult, "best"));
        timeComplexity.setAverageCase(extractTimeComplexity(analysisResult, "average"));
        timeComplexity.setWorstCase(extractTimeComplexity(analysisResult, "worst"));
        timeComplexity.setExplanation(generateTimeComplexityExplanation(timeComplexity));
        report.setTimeComplexity(timeComplexity);
        
        // 解析空间复杂度
        SpaceComplexity spaceComplexity = new SpaceComplexity();
        spaceComplexity.setBestCase(extractSpaceComplexity(analysisResult, "best"));
        spaceComplexity.setAverageCase(extractSpaceComplexity(analysisResult, "average"));
        spaceComplexity.setWorstCase(extractSpaceComplexity(analysisResult, "worst"));
        spaceComplexity.setExplanation(generateSpaceComplexityExplanation(spaceComplexity));
        report.setSpaceComplexity(spaceComplexity);
        
        // 星级评估（带鼓励性评语）
        int starRating = calculateStarRating(code, timeComplexity, spaceComplexity);
        report.setStarRating(starRating);
        report.setStarRatingComment(generateStarRatingComment(starRating));
        
        // 优化方向（具体可行）
        report.setOptimizationDirections(generateOptimizationDirections(code, timeComplexity, spaceComplexity));
        
        // 弱点识别（建设性批评）
        report.setWeaknesses(generateConstructiveWeaknesses(code));
        
        // 总体评估（鼓励性语言）
        report.setOverallAssessment(generateEncouragingAssessment(code, starRating));
        
        // 改进建议（个性化）
        report.setImprovementSuggestions(String.join("\n", generatePersonalizedSuggestions(code)));
        
        // 情绪价值内容
        report.setEmotionalValue(generateEmotionalValueContent(starRating));
        
        return report;
    }

    /**
     * 自动检测编程语言
     *
     * @param code 算法代码
     * @return {@link String }
     */
    private String detectProgrammingLanguage(String code) {
        // 基于代码特征自动检测编程语言
        if (code.contains("public class") || code.contains("public static") || code.contains("System.out.println")) {
            return "Java";
        } else if (code.contains("def ") || code.contains("import ") && code.contains("print(")) {
            return "Python";
        } else if (code.contains("function ") || code.contains("const ") || code.contains("let ") || code.contains("console.log")) {
            return "JavaScript";
        } else if (code.contains("#include") || code.contains("int main") || code.contains("printf")) {
            return "C/C++";
        } else if (code.contains("using ") || code.contains("namespace ") || code.contains("Console.WriteLine")) {
            return "C#";
        } else if (code.contains("package ") || code.contains("func ") || code.contains("fmt.Println")) {
            return "Go";
        } else if (code.contains("<?php") || code.contains("echo ")) {
            return "PHP";
        } else if (code.contains("fn ") || code.contains("let mut ") || code.contains("println!")) {
            return "Rust";
        } else if (code.contains("class ") && code.contains("def ")) {
            return "Ruby";
        } else if (code.contains("val ") || code.contains("fun ") || code.contains("println(")) {
            return "Kotlin";
        } else if (code.contains("var ") && code.contains("func ")) {
            return "Swift";
        } else {
            // 如果无法识别，让AI自动判断
            return null;
        }
    }

    /**
     * 从代码中提取算法名称
     */
    private String extractAlgorithmName(String code) {
        // 简化的算法名称识别逻辑
        if (code.contains("sort") || code.contains("Sort")) {
            return "排序算法";
        } else if (code.contains("search") || code.contains("Search")) {
            return "搜索算法";
        } else if (code.contains("fibonacci") || code.contains("Fibonacci")) {
            return "斐波那契数列算法";
        } else if (code.contains("tree") || code.contains("Tree")) {
            return "树结构算法";
        } else if (code.contains("graph") || code.contains("Graph")) {
            return "图算法";
        } else {
            return "通用算法";
        }
    }
    
    /**
     * 提取时间复杂度
     */
    private String extractTimeComplexity(String analysisResult, String type) {
        // 简化的复杂度提取逻辑
        if (analysisResult.contains("O(1)")) return "O(1)";
        if (analysisResult.contains("O(log n)")) return "O(log n)";
        if (analysisResult.contains("O(n)")) return "O(n)";
        if (analysisResult.contains("O(n log n)")) return "O(n log n)";
        if (analysisResult.contains("O(n²)") || analysisResult.contains("O(n^2)")) return "O(n²)";
        return "O(n)"; // 默认值
    }
    
    /**
     * 提取空间复杂度
     */
    private String extractSpaceComplexity(String analysisResult, String type) {
        // 简化的复杂度提取逻辑
        if (analysisResult.contains("O(1)")) return "O(1)";
        if (analysisResult.contains("O(log n)")) return "O(log n)";
        if (analysisResult.contains("O(n)")) return "O(n)";
        return "O(1)"; // 默认值
    }
    
    /**
     * 生成时间复杂度解释
     */
    private String generateTimeComplexityExplanation(TimeComplexity timeComplexity) {
        String[] explanations = {
            "这个时间复杂度表现良好，体现了算法的效率",
            "优秀的时间复杂度控制，算法运行效率很高",
            "合理的时间复杂度设计，符合算法预期",
            "时间复杂度控制得当，算法性能稳定"
        };
        return explanations[random.nextInt(explanations.length)];
    }
    
    /**
     * 生成空间复杂度解释
     */
    private String generateSpaceComplexityExplanation(SpaceComplexity spaceComplexity) {
        String[] explanations = {
            "空间复杂度控制得很好，内存使用效率高",
            "优秀的内存管理，空间复杂度表现突出",
            "合理的空间使用，体现了良好的编程习惯",
            "空间复杂度设计得当，内存使用高效"
        };
        return explanations[random.nextInt(explanations.length)];
    }
    
    /**
     * 计算星级评估
     */
    private int calculateStarRating(String code, TimeComplexity timeComplexity, SpaceComplexity spaceComplexity) {
        int baseRating = 3; // 基础评分
        
        // 根据代码质量调整评分
        if (code.length() > 100) baseRating++; // 代码较长，可能更复杂
        if (timeComplexity.getWorstCase().equals("O(1)")) baseRating++;
        if (spaceComplexity.getWorstCase().equals("O(1)")) baseRating++;
        
        return Math.min(5, Math.max(1, baseRating)); // 限制在1-5星
    }
    
    /**
     * 生成星级评语
     */
    private String generateStarRatingComment(int starRating) {
        switch (starRating) {
            case 5: return "⭐️⭐️⭐️⭐️⭐️ 五星级算法！代码质量非常高，体现了卓越的编程能力！";
            case 4: return "⭐️⭐️⭐️⭐️ 四星级算法！代码质量优秀，有很大的提升空间！";
            case 3: return "⭐️⭐️⭐️ 三星级算法！代码质量良好，继续保持进步！";
            case 2: return "⭐️⭐️ 二星级算法！代码有改进空间，相信你能做得更好！";
            case 1: return "⭐️ 一星级算法！这是学习的开始，每一步进步都值得肯定！";
            default: return "感谢分享代码，让我们一起学习和进步！";
        }
    }
    
    /**
     * 生成优化方向
     */
    private List<String> generateOptimizationDirections(String code, TimeComplexity timeComplexity, SpaceComplexity spaceComplexity) {
        return Arrays.asList(
            "可以考虑使用更高效的数据结构来优化性能",
            "尝试减少不必要的循环嵌套",
            "优化边界条件处理，提高代码健壮性",
            "增加错误处理机制，提升代码可靠性"
        );
    }
    
    /**
     * 生成建设性弱点识别
     */
    private List<String> generateConstructiveWeaknesses(String code) {
        return Arrays.asList(
            "代码注释可以更详细一些，方便他人理解",
            "部分边界条件处理可以进一步完善",
            "可以考虑增加更多的测试用例",
            "代码结构可以进一步优化，提高可读性"
        );
    }
    
    /**
     * 生成鼓励性总体评估
     */
    private String generateEncouragingAssessment(String code, int starRating) {
        String[] assessments = {
            "这是一个很好的开始！代码逻辑清晰，继续努力会有更大的进步！",
            "代码质量不错！体现了良好的编程思维，继续保持！",
            "优秀的算法实现！展现了扎实的编程基础，为你点赞！",
            "代码结构合理！算法思路清晰，有很大的发展潜力！"
        };
        return assessments[random.nextInt(assessments.length)];
    }
    
    /**
     * 生成个性化建议
     */
    private List<String> generatePersonalizedSuggestions(String code) {
        return Arrays.asList(
            "建议学习更多算法设计模式，提升代码质量",
            "可以尝试实现不同的算法变体，加深理解",
            "推荐阅读相关算法书籍，系统学习算法知识",
            "多参与算法练习，提升实战能力"
        );
    }
    
    /**
     * 生成情绪价值内容
     */
    private String generateEmotionalValueContent(int starRating) {
        switch (starRating) {
            case 5: return "🎉 太棒了！你的算法实现堪称完美！继续保持这种优秀的表现！";
            case 4: return "👍 非常出色！你的代码质量很高，相信你很快就能达到五星级水平！";
            case 3: return "👏 做得很好！你的算法实现很扎实，继续努力会有更大的突破！";
            case 2: return "💪 有进步空间！每一次尝试都是成长的机会，相信你能做得更好！";
            case 1: return "🌟 勇敢的开始！学习算法的道路充满挑战，你的努力值得肯定！";
            default: return "感谢你的分享！学习算法需要耐心和坚持，你已经迈出了重要的一步！";
        }
    }
    
    /**
     * 提取相关模板上下文
     */
    private String extractRelevantTemplateContext(String code) {
        String algorithmName = extractAlgorithmName(code);
        String templateContent = templateService.getTemplateByAlgorithm(algorithmName);
        
        if (!templateContent.isEmpty()) {
            return "相关算法模板参考：" + templateContent.substring(0, Math.min(300, templateContent.length()));
        }
        
        return "暂无相关模板参考";
    }
    
    /**
     * 获取算法分析的情绪价值统计
     */
    public String getAnalysisEmotionStats() {
        return "我们的算法分析服务注重用户体验，提供温暖、鼓励的分析报告，帮助用户建立学习信心！";
    }
}