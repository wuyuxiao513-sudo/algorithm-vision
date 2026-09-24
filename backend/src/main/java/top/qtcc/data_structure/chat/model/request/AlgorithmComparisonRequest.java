package top.qtcc.data_structure.chat.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 算法比较请求类
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlgorithmComparisonRequest {
    private String code1;
    private String code2;
    private String language;
}