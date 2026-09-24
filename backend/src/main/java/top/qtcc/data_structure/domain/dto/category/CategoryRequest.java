package top.qtcc.data_structure.domain.dto.category;

import lombok.Data;
import java.io.Serializable;

/**
 * 分类请求对象
 *
 * @author qiutuan
 * @date 2024/11/16
 */
@Data
public class CategoryRequest implements Serializable {
    
    /**
     * 分类ID
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
     * 父级分类ID
     */
    private Long parentId;
    
    /**
     * 分类排序
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
    
    private static final long serialVersionUID = 1L;
}