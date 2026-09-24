package top.qtcc.data_structure.domain.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 评论表定义
 */
public class CommentTableDef extends TableDef {

    public static final CommentTableDef COMMENT = new CommentTableDef();

    public final QueryColumn COMMENT_ID = new QueryColumn(this, "comment_id");
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");
    public final QueryColumn ARTICLE_ID = new QueryColumn(this, "article_id");
    public final QueryColumn CONTENT = new QueryColumn(this, "content");
    public final QueryColumn PARENT_ID = new QueryColumn(this, "parent_id");
    public final QueryColumn REPLY_USER_ID = new QueryColumn(this, "reply_user_id");
    public final QueryColumn LIKE_COUNT = new QueryColumn(this, "like_count");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
    public final QueryColumn DELETED = new QueryColumn(this, "deleted");

    public CommentTableDef() {
        super("", "comment");
    }
}