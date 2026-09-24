package top.qtcc.data_structure.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import top.qtcc.data_structure.domain.dto.comment.CommentRequest;
import top.qtcc.data_structure.domain.entity.Comment;
import top.qtcc.data_structure.domain.entity.User;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.domain.vo.comment.CommentVO;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.mapper.CommentMapper;
import top.qtcc.data_structure.service.CommentLikeService;
import top.qtcc.data_structure.service.CommentService;
import top.qtcc.data_structure.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static top.qtcc.data_structure.domain.entity.table.CommentTableDef.COMMENT;

/**
 * 评论服务实现类
 */
@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private UserService userService;

    @Resource
    private CommentLikeService commentLikeService;

    /**
     * 添加评论
     */
    @Override
    public boolean addComment(CommentRequest commentRequest) {
        if (commentRequest == null || StringUtils.isBlank(commentRequest.getContent())) {
            throw new IllegalArgumentException("评论内容不能为空");
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentRequest, comment);

        // 设置默认值
        if (comment.getParentId() == null) {
            comment.setParentId(0L); // 默认顶级评论
        }
        if (comment.getLikeCount() == null) {
            comment.setLikeCount(0);
        }
        if (comment.getCreateTime() == null) {
            comment.setCreateTime(LocalDateTime.now());
        }
        if (comment.getUpdateTime() == null) {
            comment.setUpdateTime(LocalDateTime.now());
        }
        if (comment.getDeleted() == null) {
            comment.setDeleted(0);
        }

        // 设置用户ID
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            comment.setUserId(currentUser.getId());
        } else {
            log.warn("无法获取当前用户，发布评论失败");
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无法获取当前用户");
        }

        return this.save(comment);
    }

    /**
     * 删除评论（逻辑删除）
     */
    @Override
    public boolean deleteComment(Long commentId) {
        if (commentId == null || commentId <= 0) {
            throw new IllegalArgumentException("评论ID不能为空");
        }

        Comment comment = this.getById(commentId);
        if (comment == null || comment.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "评论不存在或已被删除");
        }

        comment.setDeleted(1);
        comment.setUpdateTime(LocalDateTime.now());
        return this.updateById(comment);
    }

    /**
     * 根据文章ID获取评论列表
     */
    @Override
    public List<CommentVO> getCommentsByArticleId(Long articleId, Long userId) {
        if (articleId == null || articleId <= 0) {
            return List.of();
        }

        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(COMMENT.ARTICLE_ID.eq(articleId))
                .and(COMMENT.DELETED.eq(0))
                .and(COMMENT.PARENT_ID.eq(0)) // 只查询顶级评论
                .orderBy(COMMENT.CREATE_TIME.desc());

        List<Comment> comments = this.list(queryWrapper);
        return comments.stream().map(comment -> getCommentVO(comment, userId)).collect(Collectors.toList());
    }

    /**
     * 根据评论ID获取评论详情
     */
    @Override
    public CommentVO getCommentById(Long commentId, Long userId) {
        if (commentId == null || commentId <= 0) {
            return null;
        }

        Comment comment = this.getById(commentId);
        if (comment == null || comment.getDeleted() == 1) {
            return null;
        }

        return getCommentVO(comment, userId);
    }

    /**
     * 根据用户ID获取评论列表
     */
    @Override
    public List<CommentVO> getCommentsByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            return List.of();
        }

        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(COMMENT.USER_ID.eq(userId))
                .and(COMMENT.DELETED.eq(0))
                .orderBy(COMMENT.CREATE_TIME.desc());

        List<Comment> comments = this.list(queryWrapper);
        return comments.stream().map(comment -> getCommentVO(comment, userId)).collect(Collectors.toList());
    }

    /**
     * 获取用户收到的评论（用户文章下的评论）
     */
    @Override
    public List<CommentVO> getCommentsByUserArticles(Long userId) {
        // 这里需要查询用户所有文章下的评论
        // 由于涉及多表关联，这里简化处理，实际项目中需要根据具体业务实现
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(COMMENT.DELETED.eq(0))
                .orderBy(COMMENT.CREATE_TIME.desc());

        List<Comment> comments = this.list(queryWrapper);
        return comments.stream().map(comment -> getCommentVO(comment, userId)).collect(Collectors.toList());
    }

    /**
     * 获取评论视图对象
     */
    private CommentVO getCommentVO(Comment comment, Long currentUserId) {
        if (comment == null) {
            return null;
        }

        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(comment, commentVO);

        // 查询用户信息
        try {
            User user = userService.getById(comment.getUserId());
            if (user != null) {
                commentVO.setUserName(user.getUserName());
                commentVO.setUserAvatar(user.getUserAvatar());
            }
        } catch (Exception e) {
            log.warn("查询用户信息失败，用户ID: {}", comment.getUserId(), e);
        }

        // 查询回复的用户信息
        if (comment.getReplyUserId() != null && comment.getReplyUserId() > 0) {
            try {
                User replyUser = userService.getById(comment.getReplyUserId());
                if (replyUser != null) {
                    commentVO.setReplyUserName(replyUser.getUserName());
                }
            } catch (Exception e) {
                log.warn("查询回复用户信息失败，用户ID: {}", comment.getReplyUserId(), e);
            }
        }

        // 查询点赞状态
        try {
            boolean isLiked = commentLikeService.getLikeStatus(comment.getCommentId(), currentUserId);
            commentVO.setLikeFlag(isLiked);
        } catch (Exception e) {
            log.warn("查询点赞状态失败，评论ID: {}, 用户ID: {}", comment.getCommentId(), currentUserId, e);
            commentVO.setLikeFlag(false);
        }

        // 查询子评论
        if (comment.getParentId() != null && comment.getParentId().equals(0L)) {
            QueryWrapper queryWrapper = QueryWrapper.create()
                    .where(COMMENT.PARENT_ID.eq(comment.getCommentId()))
                    .and(COMMENT.DELETED.eq(0))
                    .orderBy(COMMENT.CREATE_TIME.asc());

            List<Comment> replies = this.list(queryWrapper);
            List<CommentVO> replyVOs = replies.stream()
                    .map(reply -> getCommentVO(reply, currentUserId))
                    .collect(Collectors.toList());
            commentVO.setRepliesChildren(replyVOs);
        }

        return commentVO;
    }
}