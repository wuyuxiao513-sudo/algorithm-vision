package top.qtcc.data_structure.chat.model;

import lombok.Data;

/**
 * 时间复杂度模型类
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@Data
public class TimeComplexity {
    private String bestCase;
    private String averageCase;
    private String worstCase;
    private String explanation;
}