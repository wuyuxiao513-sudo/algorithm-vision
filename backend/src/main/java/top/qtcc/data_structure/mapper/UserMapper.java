package top.qtcc.data_structure.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.qtcc.data_structure.domain.entity.User;

/**
 * 用户 Mapper 接口
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}