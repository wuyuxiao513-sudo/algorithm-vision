package top.qtcc.data_structure.chat.algorithm;

/**
 * 算法问题解答服务接口
 *
 * @author qiutuan
 * @date 2024/11/02
 */
public interface AlgorithmService {

    /**
     * 解答算法问题
     *
     * @param problem 算法问题描述
     * @param language 编程语言 (Java, Python, C++等)
     * @return 包含解答代码和解释的完整答案
     */
    String solveAlgorithmProblem(String problem, String language);

    /**
     * 生成算法代码
     *
     * @param algorithmName 算法名称
     * @param language 编程语言
     * @return 算法实现代码
     */
    String generateAlgorithmCode(String algorithmName, String language);

    /**
     * 分析算法复杂度
     *
     * @param code 算法代码
     * @return 时间复杂度和空间复杂度分析
     */
    String analyzeComplexity(String code);

    /**
     * 提供算法优化建议
     *
     * @param code 算法代码
     * @return 优化建议和改进方案
     */
    String provideOptimizationSuggestions(String code);
}