package top.qtcc.data_structure.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import top.qtcc.data_structure.domain.entity.Article;
import top.qtcc.data_structure.domain.entity.ArticleLike;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.mapper.ArticleLikeMapper;
import top.qtcc.data_structure.service.ArticleLikeService;
import top.qtcc.data_structure.service.ArticleService;

import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static top.qtcc.data_structure.domain.entity.table.ArticleLikeTableDef.ARTICLE_LIKE;

/**
 * 文章点赞服务实现
 *
 * @author qiutuan
 * @date 2024/11/16
 */
@Service
@Slf4j
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper, ArticleLike> implements ArticleLikeService {

    private final ArticleService articleService;

    public ArticleLikeServiceImpl(@Lazy ArticleService articleService) {
        this.articleService = articleService;
    }


    /**
     * 检查文章参是否存在，参数是否有效
     *
     * @param articleId 文章ID
     * @param userId    用户ID
     */
    private void validateParams(Long articleId, Long userId) {
        if (articleId == null || userId == null || articleId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 检查文章是否存在
        Article article = articleService.getArticleById(articleId);
        if (article == null || article.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "文章不存在");
        }
    }

    /**
     * 点赞文章
     *
     * @param articleId 文章ID
     * @param userId    用户ID
     * @return 是否成功
     */
    @Override
    public boolean likeArticle(Long articleId, Long userId) {

        validateParams(articleId, userId);

        // 检查是否已经点赞
        ArticleLike existingLike = getLikeRecord(articleId, userId);
        if (existingLike != null) {
            if (existingLike.getStatus() == 1) {
                // 已经点赞，无需重复操作
                return true;
            } else {
                // 更新为点赞状态
                existingLike.setStatus(1);
                existingLike.setUpdateTime(LocalDateTime.now());
                return this.updateById(existingLike);
            }
        }

        // 创建新的点赞记录
        ArticleLike articleLike = new ArticleLike();
        articleLike.setArticleId(articleId);
        articleLike.setUserId(userId);
        articleLike.setStatus(1); // 点赞
        articleLike.setCreateTime(LocalDateTime.now());
        articleLike.setUpdateTime(LocalDateTime.now());
        articleLike.setDeleted(0);

        boolean result = this.save(articleLike);

        // 更新文章的点赞数量
        if (result) {
            updateArticleLikeCount(articleId);
        }

        return result;
    }

    /**
     * 取消点赞文章
     *
     * @param articleId 文章ID
     * @param userId    用户ID
     * @return 是否成功
     */
    @Override
    public boolean unlikeArticle(Long articleId, Long userId) {

        validateParams(articleId, userId);

        // 检查点赞记录是否存在
        ArticleLike existingLike = getLikeRecord(articleId, userId);
        if (existingLike == null || existingLike.getStatus() == 0) {
            // 没有点赞记录或已经取消点赞
            return true;
        }

        // 更新为取消点赞状态
        existingLike.setStatus(0);
        existingLike.setUpdateTime(LocalDateTime.now());
        boolean result = this.updateById(existingLike);

        // 更新文章的点赞数量
        if (result) {
            updateArticleLikeCount(articleId);
        }

        return result;
    }

    /**
     * 获取用户对文章的点赞状态
     *
     * @param articleId 文章ID
     * @param userId    用户ID
     * @return 点赞状态（true-已点赞，false-未点赞）
     */
    @Override
    public boolean getLikeStatus(Long articleId, Long userId) {

        validateParams(articleId, userId);

        ArticleLike likeRecord = getLikeRecord(articleId, userId);
        return likeRecord != null && likeRecord.getStatus() == 1;
    }

    /**
     * 获取文章的点赞数量
     *
     * @param articleId 文章ID
     * @return 点赞数量
     */
    @Override
    public int getLikeCount(Long articleId) {
        if (articleId == null || articleId <= 0) {
            return 0;
        }

        //查询文章是否存在
        Article article = articleService.getArticleById(articleId);
        if (article == null || article.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "文章不存在");
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("article_id", articleId);
        queryWrapper.eq("status", 1); // 点赞状态
        queryWrapper.eq("deleted", 0); // 未删除

        return (int) this.count(queryWrapper);
    }

    /**
     * 切换点赞状态（点赞/取消点赞）
     *
     * @param articleId 文章ID
     * @param userId    用户ID
     * @return 切换后的状态（true-点赞，false-取消点赞）
     */
    @Override
    public boolean toggleLike(Long articleId, Long userId) {
        validateParams(articleId, userId);

        boolean currentStatus = getLikeStatus(articleId, userId);

        if (currentStatus) {
            // 当前已点赞，执行取消点赞
            return !unlikeArticle(articleId, userId);
        } else {
            // 当前未点赞，执行点赞
            return likeArticle(articleId, userId);
        }
    }

    /**
     * 获取用户的点赞记录
     *
     * @param articleId 文章ID
     * @param userId    用户ID
     * @return 点赞记录
     */
    private ArticleLike getLikeRecord(Long articleId, Long userId) {

        validateParams(articleId, userId);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("article_id", articleId);
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("deleted", 0); // 未删除

        return this.getOne(queryWrapper);
    }

    /**
     * 更新文章的点赞数量
     *
     * @param articleId 文章ID
     */
    private void updateArticleLikeCount(Long articleId) {
        try {
            int likeCount = getLikeCount(articleId);
            Article article = new Article();
            article.setArticleId(articleId);
            article.setLikeCount(likeCount);
            article.setUpdateTime(LocalDateTime.now());

            articleService.updateById(article);
        } catch (Exception e) {
            log.error("更新文章点赞数量失败，文章ID: {}", articleId, e);
        }
    }

    /**
     * 获取用户点赞的文章ID列表
     *
     * @param userId 用户ID
     * @return 点赞的文章ID列表
     */
    @Override
    public List<Long> getUserLikedArticles(Long userId) {
        if (userId == null || userId <= 0) {
            return List.of();
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("status", 1); // 点赞状态
        queryWrapper.eq("deleted", 0); // 未删除

        List<ArticleLike> likedArticles = this.list(queryWrapper);
        return likedArticles.stream()
                .map(ArticleLike::getArticleId)
                .collect(Collectors.toList());
    }
}