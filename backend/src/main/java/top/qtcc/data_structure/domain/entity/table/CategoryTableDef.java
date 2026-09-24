package top.qtcc.data_structure.domain.entity.table;

import com.mybatisflex.core.query.QueryTable;
import com.mybatisflex.core.query.QueryColumn;

/**
 * 分类表定义
 */
public class CategoryTableDef {

    /**
     * 分类表
     */
    public static final QueryTable CATEGORY = new QueryTable("category");

    /**
     * 分类ID
     */
    public static final QueryColumn CATEGORY_ID = new QueryColumn(CATEGORY, "category_id");

    /**
     * 分类名称
     */
    public static final QueryColumn CATEGORY_NAME = new QueryColumn(CATEGORY, "category_name");

    /**
     * 分类描述
     */
    public static final QueryColumn DESCRIPTION = new QueryColumn(CATEGORY, "description");

    /**
     * 父级分类ID
     */
    public static final QueryColumn PARENT_ID = new QueryColumn(CATEGORY, "parent_id");

    /**
     * 分类排序
     */
    public static final QueryColumn SORT_ORDER = new QueryColumn(CATEGORY, "sort_order");

    /**
     * 分类状态
     */
    public static final QueryColumn STATUS = new QueryColumn(CATEGORY, "status");

    /**
     * 分类图标URL
     */
    public static final QueryColumn ICON_URL = new QueryColumn(CATEGORY, "icon_url");

    /**
     * 分类颜色
     */
    public static final QueryColumn COLOR = new QueryColumn(CATEGORY, "color");

    /**
     * 文章数量
     */
    public static final QueryColumn ARTICLE_COUNT = new QueryColumn(CATEGORY, "article_count");

    /**
     * 创建时间
     */
    public static final QueryColumn CREATE_TIME = new QueryColumn(CATEGORY, "create_time");

    /**
     * 更新时间
     */
    public static final QueryColumn UPDATE_TIME = new QueryColumn(CATEGORY, "update_time");

    /**
     * 逻辑删除
     */
    public static final QueryColumn DELETED = new QueryColumn(CATEGORY, "deleted");
}