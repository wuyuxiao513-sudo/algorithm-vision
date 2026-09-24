package top.qtcc.data_structure.chat.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 算法分析报告请求类
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlgorithmAnalysisRequest {
    private String code;
    private String language;
}