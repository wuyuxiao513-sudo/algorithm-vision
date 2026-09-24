package top.qtcc.data_structure.chat.algorithm;

import top.qtcc.data_structure.chat.model.AlgorithmAnalysisReport;
import top.qtcc.data_structure.chat.model.SpaceComplexity;
import top.qtcc.data_structure.chat.model.TimeComplexity;

import java.util.List;

/**
 * 算法分析服务接口
 * 提供算法代码的全面分析报告
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
public interface AlgorithmAnalysisService {

    /**
     * 生成算法分析报告
     * 
     * @param code 算法代码
     * @param language 编程语言
     * @return 完整的算法分析报告
     */
    AlgorithmAnalysisReport analyzeAlgorithm(String code, String language);

    /**
     * 分析算法时间复杂度
     * 
     * @param code 算法代码
     * @return 时间复杂度分析结果
     */
    TimeComplexity analyzeTimeComplexity(String code);

    /**
     * 分析算法空间复杂度
     * 
     * @param code 算法代码
     * @return 空间复杂度分析结果
     */
    SpaceComplexity analyzeSpaceComplexity(String code);

    /**
     * 评估算法星级
     * 
     * @param code 算法代码
     * @return 算法星级评估 (1-5星)
     */
    int evaluateAlgorithmStar(String code);

    /**
     * 提供算法优化方向
     * 
     * @param code 算法代码
     * @return 优化建议列表
     */
    List<String> getOptimizationDirections(String code);

    /**
     * 识别算法弱点
     * 
     * @param code 算法代码
     * @return 算法弱点列表
     */
    List<String> identifyWeaknesses(String code);
}