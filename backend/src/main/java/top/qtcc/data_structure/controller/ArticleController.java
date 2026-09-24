package top.qtcc.data_structure.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import top.qtcc.data_structure.annotation.AuthCheck;
import top.qtcc.data_structure.annotation.RateLimit;
import top.qtcc.data_structure.annotation.RepeatSubmit;
import top.qtcc.data_structure.common.BaseResponse;
import top.qtcc.data_structure.common.PageResponse;
import top.qtcc.data_structure.common.ResultUtils;
import top.qtcc.data_structure.constant.UserConstant;
import top.qtcc.data_structure.domain.dto.article.ArticleRequest;
import top.qtcc.data_structure.domain.entity.Article;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.domain.vo.article.ArticleVO;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.service.ArticleService;

import java.util.List;

/**
 * 文章控制器
 *
 * @author qiutuan
 * @date 2024/11/16
 */
@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 获取文章详情
     *
     * @param id         文章ID
     * @param lookUserId 查看用户ID（可选，用于记录浏览历史）
     * @return 文章详情
     */
    @RateLimit()
    @GetMapping("/getById")
    public BaseResponse<ArticleVO> getArticleById(@RequestParam(value = "id", required = false) Long id,
                                                  @RequestParam(value = "lookUserId", required = false) Long lookUserId) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("文章ID不能为空");
        }

        try {
            Article article = articleService.getArticleById(id);
            if (article == null) {
                return ResultUtils.error(404, "文章不存在");
            }
            ArticleVO articleVO = articleService.getArticleVO(article);
            return ResultUtils.success(articleVO);
        } catch (Exception e) {
            return ResultUtils.error(500, "查询失败：" + e.getMessage());
        }
    }

    /**
     * 根据标题查询文章列表
     *
     * @param articleTitle 文章标题
     * @return 文章列表
     */
    @RateLimit(count = 50)
    @GetMapping("/title")
    public BaseResponse<List<ArticleVO>> getArticlesByTitle(@RequestParam(value = "articleTitle", required = false) String articleTitle) {
        List<ArticleVO> articles = articleService.getArticlesByTitle(articleTitle);
        return ResultUtils.success(articles);
    }

    /**
     * 发布文章
     *
     * @param articleRequest 文章请求信息
     * @return 操作结果
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 10)
    @RepeatSubmit(interval = 3000)
    @PostMapping("/add")
    public BaseResponse<Boolean> addArticle(@RequestBody ArticleRequest articleRequest) {
        try {
            Article article = convertToArticle(articleRequest);
            boolean result = articleService.addArticle(article);
            return ResultUtils.success(true);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * 修改文章
     *
     * @param articleRequest 文章请求信息
     * @return 操作结果
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 10)
    @RepeatSubmit(interval = 3000)
    @PutMapping("/edit")
    public BaseResponse<Boolean> editArticle(@RequestBody ArticleRequest articleRequest) {
        try {
            Article article = convertToArticle(articleRequest);
            boolean result = articleService.editArticle(article);
            return ResultUtils.success(true);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * 将ArticleRequest转换为Article实体
     *
     * @param articleRequest 文章请求信息
     * @return Article实体
     */
    private Article convertToArticle(ArticleRequest articleRequest) {
        Article article = new Article();
        BeanUtils.copyProperties(articleRequest, article);
        return article;
    }

    /**
     * 删除文章
     *
     * @param id 文章ID
     * @return 操作结果
     */
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    @RateLimit(time = 60, count = 5)
    @RepeatSubmit(interval = 5000)
    @DeleteMapping("/del")
    public BaseResponse<Boolean> deleteArticle(@RequestParam("id") Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("文章ID不能为空");
            }

            boolean result = articleService.deleteArticle(id);
            return ResultUtils.success(true);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * 更新阅读数
     *
     * @param id 文章ID
     * @return 操作结果
     */
    @RateLimit(time = 60, count = 30)
    @PostMapping("/updatePageview")
    public BaseResponse<Boolean> updatePageview(@RequestParam("id") Long id) {
        try {
            boolean result = articleService.updatePageview(id);
            return ResultUtils.success(true);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * 根据用户ID查询文章列表
     *
     * @param userId 用户ID
     * @return 文章列表
     */
    @RateLimit(time = 60, count = 50)
    @GetMapping("/getByUserId")
    public BaseResponse<List<ArticleVO>> getArticlesByUserId(@RequestParam("userId") Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        List<ArticleVO> articles = articleService.getArticlesByUserId(userId);
        return ResultUtils.success(articles);
    }

    /**
     * 获取文章列表（分页）- 兼容旧版本
     *
     * @param pageSize 每页大小
     * @param pageNum  页码
     * @return 文章列表
     */
    @RateLimit()
    @GetMapping("/list")
    public BaseResponse<List<ArticleVO>> getArticleList(@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        List<ArticleVO> articles = articleService.getArticleList(pageSize, (pageNum - 1) * pageSize);
        return ResultUtils.success(articles);
    }

    /**
     * 获取文章分页列表（包含总条数）- 新版本
     *
     * @param pageSize 每页大小
     * @param pageNum  页码
     * @return 分页响应对象
     */
    @RateLimit()
    @GetMapping("/page")
    public BaseResponse<PageResponse<ArticleVO>> getArticlePageList(@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                                    @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        PageResponse<ArticleVO> pageResponse = articleService.getArticlePageList(pageNum, pageSize);
        return ResultUtils.success(pageResponse);
    }
}
