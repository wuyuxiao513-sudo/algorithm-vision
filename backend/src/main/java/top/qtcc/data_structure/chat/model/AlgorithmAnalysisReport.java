package top.qtcc.data_structure.chat.model;

import lombok.Data;

import java.util.List;

/**
 * 算法分析报告模型类
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
@Data
public class AlgorithmAnalysisReport {
    private String algorithmName;
    private TimeComplexity timeComplexity;
    private SpaceComplexity spaceComplexity;
    private int starRating;
    private String starRatingComment; // 星级评语
    private List<String> optimizationDirections;
    private List<String> weaknesses;
    private String overallAssessment;
    private String improvementSuggestions;
    private String emotionalValue; // 情绪价值内容
}