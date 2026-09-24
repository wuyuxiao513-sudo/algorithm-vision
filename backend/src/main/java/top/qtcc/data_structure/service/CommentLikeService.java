package top.qtcc.data_structure.service;

import top.qtcc.data_structure.domain.dto.comment.CommentLikeRequest;

/**
 * 评论点赞服务接口
 */
public interface CommentLikeService {

    /**
     * 点赞
     */
    boolean addLike(CommentLikeRequest likeRequest);

    /**
     * 取消点赞
     */
    boolean deleteLike(Long likeId);

    /**
     * 获取点赞状态
     */
    boolean getLikeStatus(Long targetId, Long userId);
}