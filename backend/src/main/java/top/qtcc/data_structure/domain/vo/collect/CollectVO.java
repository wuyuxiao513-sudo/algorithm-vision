package top.qtcc.data_structure.domain.vo.collect;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 收藏视图对象VO
 */
@Data
public class CollectVO {

    /**
     * 收藏id
     */
    private Long collectId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章摘要
     */
    private String paratext;

    /**
     * 文章正文
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
     * 收藏量
     */
    private Integer collectCount;

    /**
     * 评论量
     */
    private Integer commentCount;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String headPhoto;

    /**
     * 是否已收藏
     */
    private Boolean collectFlag;
}