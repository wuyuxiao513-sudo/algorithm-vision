package top.qtcc.data_structure.domain.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章分类表实体类
 */
@Data
@Table("category") // 关联数据库表名
public class Category {

    /**
     * 主键，分类id
     */
    @Id(keyType = KeyType.Auto) // MyBatis-Flex 自增主键注解
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 父级分类id（0表示顶级分类）
     */
    private Long parentId;

    /**
     * 分类排序（数字越小越靠前）
     */
    private Integer sortOrder;

    /**
     * 分类状态：0-禁用，1-启用
     */
    private Integer status;

    /**
     * 分类图标URL
     */
    private String iconUrl;

    /**
     * 分类颜色
     */
    private String color;

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除：0-未删除，1-已删除
     */
    private Integer deleted;
}