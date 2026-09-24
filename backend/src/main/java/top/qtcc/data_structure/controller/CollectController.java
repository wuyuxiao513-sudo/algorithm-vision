package top.qtcc.data_structure.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.qtcc.data_structure.annotation.AuthCheck;
import top.qtcc.data_structure.annotation.RateLimit;
import top.qtcc.data_structure.annotation.RepeatSubmit;
import top.qtcc.data_structure.common.BaseResponse;
import top.qtcc.data_structure.common.ResultUtils;
import top.qtcc.data_structure.constant.UserConstant;
import top.qtcc.data_structure.domain.dto.collect.CollectRequest;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.domain.vo.collect.CollectVO;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.service.CollectService;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * 收藏控制器
 */
@RestController
@RequestMapping("/collect")
@Slf4j
public class CollectController {

    @Resource
    private CollectService collectService;

    /**
     * 添加收藏
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 10)
    @RepeatSubmit(interval = 3000)
    @PostMapping("/add")
    public BaseResponse<Boolean> addCollect(@RequestBody CollectRequest collectRequest) {
        try {
            boolean result = collectService.addCollect(collectRequest);
            return ResultUtils.success(true);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * 取消收藏
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 10)
    @RepeatSubmit(interval = 3000)
    @DeleteMapping("/del")
    public BaseResponse<Boolean> deleteCollect(@RequestParam("id") Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("收藏ID不能为空");
            }

            boolean result = collectService.deleteCollect(id);
            return ResultUtils.success(true);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * 获取用户收藏的文章列表
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 50)
    @GetMapping("/getCollectArt")
    public BaseResponse<List<CollectVO>> getCollectArticles(@RequestParam("userId") Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        List<CollectVO> collects = collectService.getCollectArticles(userId);
        return ResultUtils.success(collects);
    }
}