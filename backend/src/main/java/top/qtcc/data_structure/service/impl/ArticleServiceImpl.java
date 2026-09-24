package top.qtcc.data_structure.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import top.qtcc.data_structure.common.PageResponse;
import top.qtcc.data_structure.domain.entity.Article;
import top.qtcc.data_structure.domain.entity.User;
import top.qtcc.data_structure.domain.enums.ArticleStatus;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.domain.vo.article.ArticleVO;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.mapper.ArticleMapper;
import top.qtcc.data_structure.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章服务实现
 *
 * @author qiutuan
 * @date 2024/11/16
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private CategoryService categoryService;

    @Resource
    private ArticleLikeService articleLikeService;

    @Lazy
    @Resource
    public void setArticleLikeService(ArticleLikeService articleLikeService) {
        this.articleLikeService = articleLikeService;
    }

    @Resource
    private UserService userService;

    // 在ArticleServiceImpl类中注入CollectService
    @Resource
    private CollectService collectService;

    // 更新getArticleVO方法，正确实现收藏状态查询
    // 在原有的ArticleServiceImpl类中添加以下方法

    /**
     * 更新文章评论数
     */
    public boolean updateCommentCount(Long articleId, int delta) {
        if (articleId == null || articleId <= 0) {
            return false;
        }

        Article article = this.getById(articleId);
        if (article == null || article.getDeleted() == 1) {
            return false;
        }

        article.setCommentCount(Math.max(0, article.getCommentCount() + delta));
        article.setUpdateTime(LocalDateTime.now());

        return this.updateById(article);
    }

    /**
     * 将文章实体转换为文章视图对象
     *
     * @param article 文章实体
     * @return {@link ArticleVO }
     */
    public ArticleVO getArticleVO(Article article) {
        if (article == null) {
            return null;
        }
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);

        // 查询分类信息
        if (article.getCategoryId() != null && article.getCategoryId() > 0) {
            try {
                top.qtcc.data_structure.domain.entity.Category category = categoryService.getCategoryById(article.getCategoryId());
                if (category != null && category.getDeleted() == 0) {
                    articleVO.setCategoryName(category.getCategoryName());
                }
            } catch (Exception e) {
                log.warn("查询分类信息失败，文章ID: {}, 分类ID: {}", article.getArticleId(), article.getCategoryId(), e);
            }
        }

        //查询用户信息
        try {
            User author = userService.getUser(article.getUserId());
            if (author != null) {
                articleVO.setUserName(author.getUserAccount());
                articleVO.setHeadPhoto(author.getUserAvatar());
            }
        } catch (Exception e) {
            log.warn("查询作者信息失败，文章ID: {}, 用户ID: {}", article.getArticleId(), article.getUserId(), e);
        }


        try {
            Long currentUserId = userService.getCurrentUser().getId();
            boolean isLiked = articleLikeService.getLikeStatus(article.getArticleId(), currentUserId);
            articleVO.setLikeFlag(isLiked);
        } catch (Exception e) {
            articleVO.setLikeFlag(false);
        }

        // 查询收藏状态
        try {
            Long currentUserId = userService.getCurrentUser().getId();
            boolean isCollected = collectService.getCollectStatus(article.getArticleId(), currentUserId);
            articleVO.setCollectFlag(isCollected);
        } catch (Exception e) {
            articleVO.setCollectFlag(false); // 默认未收藏
        }

        return articleVO;
    }

    /**
     * 通过文章ID获取文章信息
     *
     * @param articleId 文章ID
     * @return 文章实体
     */
    public Article getArticleById(Long articleId) {
        if (articleId == null || articleId <= 0) {
            return null;
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("article_id", articleId);
        queryWrapper.eq("deleted", 0); // 未删除的文章

        return this.getOne(queryWrapper);
    }

    /**
     * 根据标题查询文章列表
     *
     * @param articleTitle 文章标题
     * @return 文章列表
     */
    public List<ArticleVO> getArticlesByTitle(String articleTitle) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("deleted", 0); // 未删除的文章
        queryWrapper.eq("status", ArticleStatus.PUBLISHED.getCode()); // 已发布的文章

        if (StringUtils.isNotBlank(articleTitle)) {
            queryWrapper.like("article_title", articleTitle);
        }

        queryWrapper.orderBy("create_time desc"); // 按发布时间倒序

        List<Article> articles = this.list(queryWrapper);
        return articles.stream().map(this::getArticleVO).collect(Collectors.toList());
    }

    /**
     * 根据用户ID查询文章列表
     *
     * @param userId 用户ID
     * @return 文章列表
     */
    public List<ArticleVO> getArticlesByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            return List.of();
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("deleted", 0); // 未删除的文章
        queryWrapper.orderBy("create_time desc"); // 按发布时间倒序

        List<Article> articles = this.list(queryWrapper);
        return articles.stream().map(this::getArticleVO).collect(Collectors.toList());
    }

    /**
     * 发布文章
     *
     * @param article 文章实体
     * @return 是否成功
     */
    public boolean addArticle(Article article) {
        if (article == null) {
            return false;
        }

        // 设置默认值
        if (article.getStatus() == null) {
            article.setStatus(ArticleStatus.DRAFT.getCode()); // 默认草稿状态
        }
        if (article.getCreateTime() == null) {
            article.setCreateTime(LocalDateTime.now());
        }
        if (article.getUpdateTime() == null) {
            article.setUpdateTime(LocalDateTime.now());
        }
        if (article.getPageview() == null) {
            article.setPageview(0);
        }
        if (article.getLikeCount() == null) {
            article.setLikeCount(0);
        }
        if (article.getUnlikeCount() == null) {
            article.setUnlikeCount(0);
        }
        if (article.getCollectCount() == null) {
            article.setCollectCount(0);
        }
        if (article.getCommentCount() == null) {
            article.setCommentCount(0);
        }
        if (article.getDeleted() == null) {
            article.setDeleted(0);
        }
        if (article.getIsTop() == null) {
            article.setIsTop(0);
        }
        if (article.getIsOriginal() == null) {
            article.setIsOriginal(1); // 默认原创
        }

        // 计算字数
        if (StringUtils.isNotBlank(article.getMainBody())) {
            article.setWordCount(article.getMainBody().length());
        } else {
            article.setWordCount(0);
        }

        //设置用户ID
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            article.setUserId(currentUser.getId());
        } else {
            log.warn("无法获取当前用户，发布文章失败");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return this.save(article);
    }

    /**
     * 修改文章
     *
     * @param article 文章实体
     * @return 是否成功
     */
    public boolean editArticle(Article article) {
        if (article == null || article.getArticleId() == null) {
            return false;
        }

        // 检查文章是否存在
        Article existingArticle = this.getById(article.getArticleId());
        if (existingArticle == null || existingArticle.getDeleted() == 1) {
            return false;
        }

        // 更新修改时间
        article.setUpdateTime(LocalDateTime.now());

        // 重新计算字数
        if (StringUtils.isNotBlank(article.getMainBody())) {
            article.setWordCount(article.getMainBody().length());
        }

        return this.updateById(article);
    }

    /**
     * 删除文章（逻辑删除）
     *
     * @param articleId 文章ID
     * @return 是否成功
     */
    public boolean deleteArticle(Long articleId) {
        if (articleId == null || articleId <= 0) {
            return false;
        }

        //查询文章是否存在
        Article existingArticle = this.getById(articleId);
        if (existingArticle == null || existingArticle.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "文章不存在或已被删除");
        }

        Article article = new Article();
        article.setArticleId(articleId);
        article.setDeleted(1); // 逻辑删除
        article.setUpdateTime(LocalDateTime.now());

        return this.updateById(article);
    }

    /**
     * 更新阅读数
     *
     * @param articleId 文章ID
     * @return 是否成功
     */
    public boolean updatePageview(Long articleId) {
        if (articleId == null || articleId <= 0) {
            return false;
        }

        Article article = this.getById(articleId);
        if (article == null || article.getDeleted() == 1) {
            return false;
        }

        article.setPageview(article.getPageview() + 1);
        article.setUpdateTime(LocalDateTime.now());

        return this.updateById(article);
    }

    /**
     * 获取文章列表（分页查询）
     *
     * @param pageSize 每页大小
     * @param offset   偏移量
     * @return 文章列表
     */
    public List<ArticleVO> getArticleList(int pageSize, int offset) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("deleted", 0);
        queryWrapper.eq("status", ArticleStatus.PUBLISHED.getCode());
        queryWrapper.orderBy("create_time desc");
        queryWrapper.limit(offset, pageSize);

        List<Article> articles = this.list(queryWrapper);

        return articles.stream().map(this::getArticleVO).collect(Collectors.toList());
    }

    /**
     * 获取文章分页列表（包含总条数）
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页响应对象
     */
    public PageResponse<ArticleVO> getArticlePageList(int pageNum, int pageSize) {
        // 构建查询条件
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("deleted", 0);
        queryWrapper.eq("status", ArticleStatus.PUBLISHED.getCode());
        queryWrapper.orderBy("create_time desc");

        // 计算偏移量
        int offset = (pageNum - 1) * pageSize;

        // 查询总条数
        long total = this.count(queryWrapper);

        // 查询分页数据
        queryWrapper.limit(offset, pageSize);
        List<Article> articles = this.list(queryWrapper);

        // 转换为视图对象
        List<ArticleVO> articleVOs = articles.stream()
                .map(this::getArticleVO)
                .collect(Collectors.toList());

        // 返回分页响应对象
        return PageResponse.of(pageNum, pageSize, total, articleVOs);
    }
}