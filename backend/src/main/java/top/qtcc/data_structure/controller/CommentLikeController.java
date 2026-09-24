package top.qtcc.data_structure.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.qtcc.data_structure.annotation.AuthCheck;
import top.qtcc.data_structure.annotation.RateLimit;
import top.qtcc.data_structure.annotation.RepeatSubmit;
import top.qtcc.data_structure.common.BaseResponse;
import top.qtcc.data_structure.common.ResultUtils;
import top.qtcc.data_structure.constant.UserConstant;
import top.qtcc.data_structure.domain.dto.comment.CommentLikeRequest;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.service.CommentLikeService;

import jakarta.annotation.Resource;

/**
 * 评论点赞控制器
 */
@RestController
@RequestMapping("/commentLikes")
@Slf4j
public class CommentLikeController {

    @Resource
    private CommentLikeService commentLikeService;

    /**
     * 点赞
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 20)
    @RepeatSubmit(interval = 2000)
    @PostMapping("/add")
    public BaseResponse<Boolean> addLike(@RequestBody CommentLikeRequest likeRequest) {
        try {
            boolean result = commentLikeService.addLike(likeRequest);
            return ResultUtils.success(true);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,e.getMessage());
        }
    }

    /**
     * 取消点赞
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 20)
    @RepeatSubmit(interval = 2000)
    @DeleteMapping("/del")
    public BaseResponse<Boolean> deleteLike(@RequestParam("id") Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("点赞ID不能为空");
            }
            
            boolean result = commentLikeService.deleteLike(id);
            return ResultUtils.success(true);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,e.getMessage());
        }
    }
}