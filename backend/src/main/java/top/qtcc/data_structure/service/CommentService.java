package top.qtcc.data_structure.service;

import top.qtcc.data_structure.domain.dto.comment.CommentRequest;
import top.qtcc.data_structure.domain.entity.Comment;
import top.qtcc.data_structure.domain.vo.comment.CommentVO;

import java.util.List;

/**
 * 评论服务接口
 */
public interface CommentService {

    /**
     * 添加评论
     */
    boolean addComment(CommentRequest commentRequest);

    /**
     * 删除评论
     */
    boolean deleteComment(Long commentId);

    /**
     * 根据文章ID获取评论列表
     */
    List<CommentVO> getCommentsByArticleId(Long articleId, Long userId);

    /**
     * 根据评论ID获取评论详情
     */
    CommentVO getCommentById(Long commentId, Long userId);

    /**
     * 根据用户ID获取评论列表
     */
    List<CommentVO> getCommentsByUserId(Long userId);

    /**
     * 获取用户收到的评论
     */
    List<CommentVO> getCommentsByUserArticles(Long userId);
}