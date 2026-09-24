package top.qtcc.data_structure.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.qtcc.data_structure.domain.entity.Article;

/**
 * 文章 Mapper 接口
 *
 * @author qiutuan
 * @date 2024/11/16
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}