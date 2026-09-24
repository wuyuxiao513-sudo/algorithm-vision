package top.qtcc.data_structure.domain.vo.article;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 文章视图对象
 */
@Data
public class ArticleVO {
    
    /**
     * 文章id
     */
    private Long articleId;
    
    /**
     * 作者id
     */
    private Long userId;
    
    /**
     * 作者用户名
     */
    private String userName;
    
    /**
     * 作者头像地址
     */
    private String headPhoto;
    
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
     * 文章分类id
     */
    private Long categoryId;
    
    /**
     * 文章分类名称
     */
    private String categoryName;
    
    /**
     * 文章标签
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
     * 转载来源URL
     */
    private String sourceUrl;
    
    /**
     * 转载来源作者
     */
    private String sourceAuthor;
    
    /**
     * 文章字数
     */
    private Integer wordCount;
    
    /**
     * 发布时间
     */
    private LocalDateTime createTime;
    
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;
    
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
     * 点赞ID（用于判断用户是否点赞）
     */
    private Long likeId;
    
    /**
     * 点赞时间
     */
    private LocalDateTime likeTime;
    
    /**
     * 点赞标志
     */
    private Boolean likeFlag;
    
    /**
     * 收藏ID
     */
    private Long collectId;
    
    /**
     * 收藏时间
     */
    private LocalDateTime collectTime;
    
    /**
     * 收藏标志
     */
    private Boolean collectFlag;
}
