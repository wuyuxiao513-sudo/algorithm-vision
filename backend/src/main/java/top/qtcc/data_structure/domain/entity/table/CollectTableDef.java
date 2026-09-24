package top.qtcc.data_structure.domain.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 收藏表定义
 */
public class CollectTableDef extends TableDef {

    public static final CollectTableDef COLLECT = new CollectTableDef();

    public final QueryColumn COLLECT_ID = new QueryColumn(this, "collect_id");
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");
    public final QueryColumn ARTICLE_ID = new QueryColumn(this, "article_id");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
    public final QueryColumn DELETED = new QueryColumn(this, "deleted");

    public CollectTableDef() {
        super("", "collect");
    }
}