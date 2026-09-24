package top.qtcc.data_structure.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.qtcc.data_structure.domain.dto.comment.CommentLikeRequest;
import top.qtcc.data_structure.domain.entity.Comment;
import top.qtcc.data_structure.domain.entity.CommentLike;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.mapper.CommentLikeMapper;
import top.qtcc.data_structure.mapper.CommentMapper;
import top.qtcc.data_structure.service.CommentLikeService;
import top.qtcc.data_structure.service.UserService;

import java.time.LocalDateTime;

import static top.qtcc.data_structure.domain.entity.table.CommentLikeTableDef.COMMENT_LIKE;

/**
 * 评论点赞服务实现类
 */
@Service
@Slf4j
public class CommentLikeServiceImpl extends ServiceImpl<CommentLikeMapper, CommentLike> implements CommentLikeService {

    @Resource
    private UserService userService;

    @Resource
    private CommentMapper commentMapper;

    /**
     * 点赞
     */
    @Override
    public boolean addLike(CommentLikeRequest likeRequest) {
        if (likeRequest == null || likeRequest.getTargetId() == null) {
            throw new IllegalArgumentException("点赞参数不能为空");
        }

        // 检查评论是否存在
        Comment comment = commentMapper.selectOneById(likeRequest.getTargetId());
        if (comment == null || comment.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 检查是否已点赞
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(COMMENT_LIKE.USER_ID.eq(likeRequest.getUserId()))
                .and(COMMENT_LIKE.TARGET_ID.eq(likeRequest.getTargetId()))
                .and(COMMENT_LIKE.DELETED.eq(0));

        CommentLike existingLike = this.getOne(queryWrapper);
        if (existingLike != null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "您已点赞该评论");
        }

        // 创建点赞记录
        CommentLike commentLike = new CommentLike();
        commentLike.setUserId(likeRequest.getUserId());
        commentLike.setTargetType("comment");
        commentLike.setTargetId(likeRequest.getTargetId());
        commentLike.setLikedAt(LocalDateTime.now());
        commentLike.setDeleted(0);

        boolean result = this.save(commentLike);

        // 更新评论点赞数
        if (result) {
            comment.setLikeCount(comment.getLikeCount() + 1);
            commentMapper.update(comment);
        }

        return result;
    }

    /**
     * 取消点赞
     */
    @Override
    public boolean deleteLike(Long likeId) {
        if (likeId == null || likeId <= 0) {
            throw new IllegalArgumentException("点赞ID不能为空");
        }

        CommentLike commentLike = this.getById(likeId);
        if (commentLike == null || commentLike.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "点赞记录不存在或已被删除");
        }

        commentLike.setDeleted(1);
        boolean result = this.updateById(commentLike);

        // 更新评论点赞数
        if (result) {
            Comment comment = commentMapper.selectOneById(commentLike.getTargetId());
            if (comment != null && comment.getDeleted() == 0) {
                comment.setLikeCount(Math.max(0, comment.getLikeCount() - 1));
                commentMapper.update(comment);
            }
        }

        return result;
    }

    /**
     * 获取点赞状态
     */
    @Override
    public boolean getLikeStatus(Long targetId, Long userId) {
        if (targetId == null || userId == null) {
            return false;
        }

        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(COMMENT_LIKE.USER_ID.eq(userId))
                .and(COMMENT_LIKE.TARGET_ID.eq(targetId))
                .and(COMMENT_LIKE.DELETED.eq(0));

        CommentLike commentLike = this.getOne(queryWrapper);
        return commentLike != null;
    }
}