package top.qtcc.data_structure.domain.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 文章点赞表定义
 *
 * @author qiutuan
 * @date 2024/11/16
 */
public class ArticleLikeTableDef extends TableDef {

    public static final ArticleLikeTableDef ARTICLE_LIKE = new ArticleLikeTableDef();

    /**
     * 点赞ID
     */
    public final QueryColumn LIKE_ID = new QueryColumn(this, "like_id");

    /**
     * 文章ID
     */
    public final QueryColumn ARTICLE_ID = new QueryColumn(this, "article_id");

    /**
     * 用户ID
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

    /**
     * 点赞状态（0-取消点赞，1-点赞）
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 删除标志（0-未删除，1-已删除）
     */
    public final QueryColumn DELETED = new QueryColumn(this, "deleted");

    public ArticleLikeTableDef() {
        super("", "article_like");
    }
}