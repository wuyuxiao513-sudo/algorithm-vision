package top.qtcc.data_structure.domain.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 评论点赞表定义
 */
public class CommentLikeTableDef extends TableDef {

    public static final CommentLikeTableDef COMMENT_LIKE = new CommentLikeTableDef();

    public final QueryColumn LIKE_ID = new QueryColumn(this, "like_id");
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");
    public final QueryColumn TARGET_TYPE = new QueryColumn(this, "target_type");
    public final QueryColumn TARGET_ID = new QueryColumn(this, "target_id");
    public final QueryColumn LIKED_AT = new QueryColumn(this, "liked_at");
    public final QueryColumn DELETED = new QueryColumn(this, "deleted");

    public CommentLikeTableDef() {
        super("", "comment_like");
    }
}