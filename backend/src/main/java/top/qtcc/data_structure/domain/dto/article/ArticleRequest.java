package top.qtcc.data_structure.domain.dto.article;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author qiutuan
 * @date 2025/11/16
 */
@Data
public class ArticleRequest {

    /**
     * 主键，文章id
     */
    @Id(keyType = KeyType.Auto) // MyBatis-Flex 自增主键注解
    private Long articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章摘要/副文
     */
    private String paratext;

    /**
     * 文章封面图URL
     */
    private String articleCover;

    /**
     * 文章分类id（代码层面约束关联分类表）
     */
    private Long categoryId;

    /**
     * 文章标签（多个标签用逗号分隔，如：Java,MySQL,后端）
     */
    private String tags;

    /**
     * 文章状态：0-草稿，1-已发布，2-下架，3-审核中
     */
    private Integer status;

    /**
     * 是否置顶：0-否，1-是
     */
    private Integer isTop;

    /**
     * 是否原创：0-转载，1-原创
     */
    private Integer isOriginal;

    /**
     * 转载来源URL（非原创时填写）
     */
    private String sourceUrl;

    /**
     * 转载来源作者（非原创时填写）
     */
    private String sourceAuthor;

    /**
     * 正文
     */
    private String mainBody;

    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    private Integer deleted;

}
