package top.qtcc.data_structure.domain.dto.comment;

import lombok.Data;

/**
 * 评论点赞请求DTO
 */
@Data
public class CommentLikeRequest {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 目标类型：comment-评论
     */
    private String targetType;

    /**
     * 目标id（评论id）
     */
    private Long targetId;
}