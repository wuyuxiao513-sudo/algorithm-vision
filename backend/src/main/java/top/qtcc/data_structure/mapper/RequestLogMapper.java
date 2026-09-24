package top.qtcc.data_structure.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.qtcc.data_structure.domain.entity.RequestLog;

import java.time.LocalDateTime;

/**
 * 请求日志 Mapper 接口
 *
 * @author qiutuan
 * @date 2024/12/07
 */
@Mapper
public interface RequestLogMapper extends BaseMapper<RequestLog> {

    /**
     * 删除过期日志
     *
     * @param expireTime 过期时间
     * @return 删除的记录数
     */
    int deleteExpiredLogs(LocalDateTime expireTime);
}