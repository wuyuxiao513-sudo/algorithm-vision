package top.qtcc.data_structure.domain.vo.category;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 分类视图对象
 */
@Data
public class CategoryVO {
    
    /**
     * 分类id
     */
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
     * 父级分类id
     */
    private Long parentId;
    
    /**
     * 父级分类名称
     */
    private String parentName;
    
    /**
     * 分类排序
     */
    private Integer sortOrder;
    
    /**
     * 分类状态：0-禁用，1-启用
     */
    private Integer status;
    
    /**
     * 分类状态描述
     */
    private String statusDesc;
    
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
     * 子分类列表
     */
    private java.util.List<CategoryVO> children;
}