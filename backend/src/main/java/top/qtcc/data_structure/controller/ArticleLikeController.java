package top.qtcc.data_structure.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.qtcc.data_structure.annotation.AuthCheck;
import top.qtcc.data_structure.annotation.RateLimit;
import top.qtcc.data_structure.annotation.RepeatSubmit;
import top.qtcc.data_structure.common.BaseResponse;
import top.qtcc.data_structure.common.ResultUtils;
import top.qtcc.data_structure.constant.UserConstant;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.service.ArticleLikeService;
import top.qtcc.data_structure.service.UserService;

import java.util.List;

/**
 * 文章点赞控制器
 *
 * @author qiutuan
 * @date 2024/11/16
 */
@RestController
@RequestMapping("/article/like")
public class ArticleLikeController {

    @Resource
    private ArticleLikeService articleLikeService;

    @Resource
    private UserService userService;

    /**
     * 点赞文章
     *
     * @param articleId 文章ID
     * @return 操作结果
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 20)
    @RepeatSubmit(interval = 2000)
    @PostMapping("/{articleId}")
    public BaseResponse<Boolean> likeArticle(@PathVariable Long articleId) {

        Long currentUserId = userService.getCurrentUser().getId();

        if (articleId == null || articleId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章ID不能为空");
        }

        boolean result = articleLikeService.likeArticle(articleId, currentUserId);
        if (result) {
            return ResultUtils.success(true);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "点赞失败");
        }

    }

    /**
     * 取消点赞文章
     *
     * @param articleId 文章ID
     * @return 操作结果
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 20)
    @RepeatSubmit(interval = 2000)
    @DeleteMapping("/{articleId}")
    public BaseResponse<Boolean> unlikeArticle(@PathVariable Long articleId) {

        Long currentUserId = userService.getCurrentUser().getId();

        if (articleId == null || articleId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章ID不能为空");
        }

        boolean result = articleLikeService.unlikeArticle(articleId, currentUserId);
        if (result) {
            return ResultUtils.success(true);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "取消点赞失败");
        }

    }

    /**
     * 切换点赞状态
     *
     * @param articleId 文章ID
     * @return 操作结果
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 30)
    @RepeatSubmit(interval = 1000)
    @PostMapping("/toggle/{articleId}")
    public BaseResponse<Boolean> toggleLike(@PathVariable Long articleId) {
        Long currentUserId = userService.getCurrentUser().getId();

        if (articleId == null || articleId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章ID不能为空");
        }

        boolean result = articleLikeService.toggleLike(articleId, currentUserId);
        if (result) {
            return ResultUtils.success(true);
        } else {
            return ResultUtils.success(false);
        }

    }

    /**
     * 查询用户是否点赞了某篇文章
     *
     * @param articleId 文章ID
     * @return 点赞状态
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 50)
    @GetMapping("/{articleId}/status")
    public BaseResponse<Boolean> getLikeStatus(@PathVariable Long articleId) {
        Long currentUserId = userService.getCurrentUser().getId();

        if (articleId == null || articleId <= 0) {
            return ResultUtils.error(400, "文章ID不能为空");
        }

        try {
            boolean result = articleLikeService.getLikeStatus(articleId, currentUserId);
            return ResultUtils.success(result);
        } catch (Exception e) {
            return ResultUtils.error(500, "查询失败：" + e.getMessage());
        }
    }

    /**
     * 获取文章点赞数量
     *
     * @param articleId 文章ID
     * @return 点赞数量
     */
    @RateLimit(time = 60, count = 100)
    @GetMapping("/count/{articleId}")
    public BaseResponse<Integer> getLikeCount(@PathVariable Long articleId) {
        if (articleId == null || articleId <= 0) {
            return ResultUtils.error(400, "文章ID不能为空");
        }

        try {
            int likeCount = articleLikeService.getLikeCount(articleId);
            return ResultUtils.success(likeCount);
        } catch (Exception e) {
            return ResultUtils.error(500, "查询失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户点赞的文章列表
     *
     * @return 点赞的文章列表
     */
    @GetMapping("/user")
    public BaseResponse<List<Long>> getUserLikedArticles() {
        Long currentUserId = userService.getCurrentUser().getId();

        try {
            List<Long> likedArticles = articleLikeService.getUserLikedArticles(currentUserId);
            return ResultUtils.success(likedArticles);
        } catch (Exception e) {
            return ResultUtils.error(500, "查询失败：" + e.getMessage());
        }
    }
}