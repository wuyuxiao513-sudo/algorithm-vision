package top.qtcc.data_structure.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.qtcc.data_structure.annotation.AuthCheck;
import top.qtcc.data_structure.annotation.RateLimit;
import top.qtcc.data_structure.annotation.RepeatSubmit;
import top.qtcc.data_structure.common.BaseResponse;
import top.qtcc.data_structure.common.ResultUtils;
import top.qtcc.data_structure.constant.UserConstant;
import top.qtcc.data_structure.domain.dto.comment.CommentRequest;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.domain.vo.comment.CommentVO;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.service.CommentService;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 添加评论
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 10)
    @RepeatSubmit(interval = 3000)
    @PostMapping("/add")
    public BaseResponse<Boolean> addComment(@RequestBody CommentRequest commentRequest) {
        try {
            boolean result = commentService.addComment(commentRequest);
            return ResultUtils.success(true);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,e.getMessage());
        }
    }

    /**
     * 删除评论
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 5)
    @RepeatSubmit(interval = 5000)
    @DeleteMapping("/del")
    public BaseResponse<Boolean> deleteComment(@RequestParam("id") Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("评论ID不能为空");
            }
            
            boolean result = commentService.deleteComment(id);
            return ResultUtils.success(true);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,e.getMessage());
        }
    }

    /**
     * 根据文章ID获取评论列表
     */
    @RateLimit(time = 60, count = 50)
    @GetMapping("/getByArticleId")
    public BaseResponse<List<CommentVO>> getCommentsByArticleId(@RequestParam("articleId") Long articleId,
                                                                @RequestParam(value = "userId", required = false) Long userId) {
        if (articleId == null || articleId <= 0) {
            throw new IllegalArgumentException("文章ID不能为空");
        }
        
        List<CommentVO> comments = commentService.getCommentsByArticleId(articleId, userId);
        return ResultUtils.success(comments);
    }

    /**
     * 根据评论ID获取评论详情
     */
    @RateLimit(time = 60, count = 50)
    @GetMapping("/getById")
    public BaseResponse<CommentVO> getCommentById(@RequestParam("id") Long id,
                                                  @RequestParam(value = "userId", required = false) Long userId) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("评论ID不能为空");
        }
        
        CommentVO comment = commentService.getCommentById(id, userId);
        if (comment == null) {
            return ResultUtils.error(404, "评论不存在");
        }
        return ResultUtils.success(comment);
    }

    /**
     * 根据用户ID获取评论列表
     */
    @RateLimit(time = 60, count = 50)
    @GetMapping("/getByUserId")
    public BaseResponse<List<CommentVO>> getCommentsByUserId(@RequestParam("userId") Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        
        List<CommentVO> comments = commentService.getCommentsByUserId(userId);
        return ResultUtils.success(comments);
    }

    /**
     * 获取用户收到的评论
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 50)
    @GetMapping("/getByUserIdArt")
    public BaseResponse<List<CommentVO>> getCommentsByUserArticles(@RequestParam("userId") Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        
        List<CommentVO> comments = commentService.getCommentsByUserArticles(userId);
        return ResultUtils.success(comments);
    }
}