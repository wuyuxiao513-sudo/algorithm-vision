package top.qtcc.data_structure.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.qtcc.data_structure.domain.entity.ArticleLike;

/**
 * 文章点赞 Mapper 接口
 *
 * @author qiutuan
 * @date 2024/11/16
 */
@Mapper
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {
}