package top.qtcc.data_structure.domain.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *  请求日志
 *
 * @author qiutuan
 * @date 2024/12/10
 */
@Data
@Table("request_log")
public class RequestLog {
    private Long id;
    private String requestId;
    private String url;
    private String method;
    private String params;
    private String ip;
    private Long userId;
    private Integer status;
    private String errorMsg;
    private Long costTime;
    private LocalDateTime createTime;
} 