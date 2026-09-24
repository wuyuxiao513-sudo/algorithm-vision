package top.qtcc.data_structure.service;

import com.mybatisflex.core.service.IService;
import top.qtcc.data_structure.common.PageResponse;
import top.qtcc.data_structure.domain.entity.Article;
import top.qtcc.data_structure.domain.vo.article.ArticleVO;

import java.util.List;

/**
 * 文章服务接口
 *
 * @author qiutuan
 * @date 2024/11/16
 */
public interface ArticleService extends IService<Article> {

    /**
     * 通过文章ID获取文章信息
     *
     * @param articleId 文章ID
     * @return 文章实体
     */
    Article getArticleById(Long articleId);

    /**
     * 根据标题查询文章列表
     *
     * @param articleTitle 文章标题
     * @return 文章列表
     */
    List<ArticleVO> getArticlesByTitle(String articleTitle);

    /**
     * 根据用户ID查询文章列表
     *
     * @param userId 用户ID
     * @return 文章列表
     */
    List<ArticleVO> getArticlesByUserId(Long userId);

    /**
     * 发布文章
     *
     * @param article 文章实体
     * @return 是否成功
     */
    boolean addArticle(Article article);

    /**
     * 修改文章
     *
     * @param article 文章实体
     * @return 是否成功
     */
    boolean editArticle(Article article);

    /**
     * 删除文章（逻辑删除）
     *
     * @param articleId 文章ID
     * @return 是否成功
     */
    boolean deleteArticle(Long articleId);

    /**
     * 更新阅读数
     *
     * @param articleId 文章ID
     * @return 是否成功
     */
    boolean updatePageview(Long articleId);

    /**
     * 获取文章视图对象
     *
     * @param article 文章实体
     * @return 文章视图对象
     */
    ArticleVO getArticleVO(Article article);

    /**
     * 获取文章列表（分页查询）
     *
     * @param pageSize 每页大小
     * @param offset 偏移量
     * @return 文章列表
     */
    List<ArticleVO> getArticleList(int pageSize, int offset);

    /**
     * 获取文章分页列表（包含总条数）
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页响应对象
     */
    PageResponse<ArticleVO> getArticlePageList(int pageNum, int pageSize);
}
