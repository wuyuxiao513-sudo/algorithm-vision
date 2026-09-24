package top.qtcc.data_structure.domain.vo.comment;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论视图对象VO
 */
@Data
public class CommentVO {

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论id（0表示顶级评论）
     */
    private Long parentId;

    /**
     * 回复的用户id（如果是回复评论）
     */
    private Long replyUserId;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 回复的用户名
     */
    private String replyUserName;

    /**
     * 是否已点赞
     */
    private Boolean likeFlag;

    /**
     * 子评论列表
     */
    private List<CommentVO> repliesChildren;
}