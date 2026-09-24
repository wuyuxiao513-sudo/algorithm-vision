package top.qtcc.data_structure.service;


import com.mybatisflex.core.service.IService;
import top.qtcc.data_structure.domain.entity.RequestLog;

/**
 * 请求日志服务接口
 *
 * @author qiutuan
 * @date 2024/12/07
 */
public interface RequestLogService extends IService<RequestLog> {
    
    /**
     * 异步保存请求日志
     *
     * @param requestLog 请求日志
     */
    void asyncSave(RequestLog requestLog);
    
    /**
     * 清理过期日志
     */
    void cleanExpiredLogs();
} 