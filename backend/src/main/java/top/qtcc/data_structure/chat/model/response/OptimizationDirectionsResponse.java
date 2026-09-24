package top.qtcc.data_structure.chat.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 算法优化方向响应类
 * 
 * @author qiutuan
 * @date 2025/11/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptimizationDirectionsResponse {
    private List<String> directions;
}