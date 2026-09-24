package top.qtcc.data_structure.domain.entity.table;

import com.mybatisflex.core.query.QueryTable;
import com.mybatisflex.core.query.QueryColumn;

/**
 * 文章表定义
 */
public class ArticleTableDef {

    /**
     * 文章表
     */
    public static final QueryTable ARTICLE = new QueryTable("article");

    /**
     * 文章ID
     */
    public static final QueryColumn ARTICLE_ID = new QueryColumn(ARTICLE, "article_id");

    /**
     * 用户ID
     */
    public static final QueryColumn USER_ID = new QueryColumn(ARTICLE, "user_id");

    /**
     * 文章标题
     */
    public static final QueryColumn ARTICLE_TITLE = new QueryColumn(ARTICLE, "article_title");

    /**
     * 文章摘要
     */
    public static final QueryColumn PARATEXT = new QueryColumn(ARTICLE, "paratext");

    /**
     * 文章封面图URL
     */
    public static final QueryColumn ARTICLE_COVER = new QueryColumn(ARTICLE, "article_cover");

    /**
     * 分类ID
     */
    public static final QueryColumn CATEGORY_ID = new QueryColumn(ARTICLE, "category_id");

    /**
     * 文章标签
     */
    public static final QueryColumn TAGS = new QueryColumn(ARTICLE, "tags");

    /**
     * 文章状态
     */
    public static final QueryColumn STATUS = new QueryColumn(ARTICLE, "status");

    /**
     * 是否置顶
     */
    public static final QueryColumn IS_TOP = new QueryColumn(ARTICLE, "is_top");

    /**
     * 是否原创
     */
    public static final QueryColumn IS_ORIGINAL = new QueryColumn(ARTICLE, "is_original");

    /**
     * 转载来源URL
     */
    public static final QueryColumn SOURCE_URL = new QueryColumn(ARTICLE, "source_url");

    /**
     * 转载来源作者
     */
    public static final QueryColumn SOURCE_AUTHOR = new QueryColumn(ARTICLE, "source_author");

    /**
     * 文章字数
     */
    public static final QueryColumn WORD_COUNT = new QueryColumn(ARTICLE, "word_count");

    /**
     * 创建时间
     */
    public static final QueryColumn CREATE_TIME = new QueryColumn(ARTICLE, "create_time");

    /**
     * 更新时间
     */
    public static final QueryColumn UPDATE_TIME = new QueryColumn(ARTICLE, "update_time");

    /**
     * 正文
     */
    public static final QueryColumn MAIN_BODY = new QueryColumn(ARTICLE, "main_body");

    /**
     * 阅读量
     */
    public static final QueryColumn PAGEVIEW = new QueryColumn(ARTICLE, "pageview");

    /**
     * 赞量
     */
    public static final QueryColumn LIKE_COUNT = new QueryColumn(ARTICLE, "like_count");

    /**
     * 踩量
     */
    public static final QueryColumn UNLIKE_COUNT = new QueryColumn(ARTICLE, "unlike_count");

    /**
     * 收藏量
     */
    public static final QueryColumn COLLECT_COUNT = new QueryColumn(ARTICLE, "collect_count");

    /**
     * 评论量
     */
    public static final QueryColumn COMMENT_COUNT = new QueryColumn(ARTICLE, "comment_count");

    /**
     * 逻辑删除
     */
    public static final QueryColumn DELETED = new QueryColumn(ARTICLE, "deleted");
}