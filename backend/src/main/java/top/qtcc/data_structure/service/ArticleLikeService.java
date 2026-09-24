package top.qtcc.data_structure.service;

import com.mybatisflex.core.service.IService;
import top.qtcc.data_structure.domain.entity.ArticleLike;
import java.util.List;

/**
 * 文章点赞服务接口
 *
 * @author qiutuan
 * @date 2024/11/16
 */
public interface ArticleLikeService extends IService<ArticleLike> {

    /**
     * 点赞文章
     *
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean likeArticle(Long articleId, Long userId);

    /**
     * 取消点赞文章
     *
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean unlikeArticle(Long articleId, Long userId);

    /**
     * 获取用户对文章的点赞状态
     *
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 点赞状态（true-已点赞，false-未点赞）
     */
    boolean getLikeStatus(Long articleId, Long userId);

    /**
     * 获取文章的点赞数量
     *
     * @param articleId 文章ID
     * @return 点赞数量
     */
    int getLikeCount(Long articleId);

    /**
     * 切换点赞状态（点赞/取消点赞）
     *
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 切换后的状态（true-点赞，false-取消点赞）
     */
    boolean toggleLike(Long articleId, Long userId);

    /**
     * 获取用户点赞的文章ID列表
     *
     * @param userId 用户ID
     * @return 点赞的文章ID列表
     */
    List<Long> getUserLikedArticles(Long userId);
}