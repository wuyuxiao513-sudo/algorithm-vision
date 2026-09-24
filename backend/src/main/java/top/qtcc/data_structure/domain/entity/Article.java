package top.qtcc.data_structure.domain.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 文章表实体类
 */
@Data
@Table("article") // 关联数据库表名
public class Article {

    /**
     * 主键，文章id
     */
    @Id(keyType = KeyType.Auto) // MyBatis-Flex 自增主键注解
    private Long articleId;

    /**
     * 作者id
     */
    private Long userId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章摘要/副文
     */
    private String paratext;

    /**
     * 文章封面图URL（支持OSS/CDN地址）
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
     * 文章字数（自动计算填充）
     */
    private Integer wordCount;

    /**
     * 最后更新时间（编辑/修改时自动更新）
     */
    private LocalDateTime updateTime;

    /**
     * 发布时间
     */
    private LocalDateTime createTime;

    /**
     * 正文
     */
    private String mainBody;

    /**
     * 阅读量
     */
    private Integer pageview;

    /**
     * 赞量
     */
    private Integer likeCount;

    /**
     * 踩量
     */
    private Integer unlikeCount;

    /**
     * 收藏量
     */
    private Integer collectCount;

    /**
     * 评论量
     */
    private Integer commentCount;

    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    private Integer deleted;
}