package top.qtcc.data_structure.domain.dto.comment;

import lombok.Data;

/**
 * 评论请求DTO
 */
@Data
public class CommentRequest {

    /**
     * 评论id（编辑时使用）
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
}