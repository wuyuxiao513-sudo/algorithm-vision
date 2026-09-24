package top.qtcc.data_structure.service;

import com.mybatisflex.core.service.IService;
import top.qtcc.data_structure.domain.entity.Category;
import top.qtcc.data_structure.domain.vo.category.CategoryVO;

import java.util.List;

/**
 * 分类服务接口
 *
 * @author qiutuan
 * @date 2024/11/16
 */
public interface CategoryService extends IService<Category> {

    /**
     * 通过分类ID获取分类信息
     *
     * @param categoryId 分类ID
     * @return 分类实体
     */
    Category getCategoryById(Long categoryId);

    /**
     * 获取所有分类列表
     *
     * @return 分类列表
     */
    List<CategoryVO> getAllCategories();

    /**
     * 获取树形分类列表
     *
     * @return 树形分类列表
     */
    List<CategoryVO> getCategoryTree();

    /**
     * 添加分类
     *
     * @param category 分类实体
     * @return 是否成功
     */
    boolean addCategory(Category category);

    /**
     * 修改分类
     *
     * @param category 分类实体
     * @return 是否成功
     */
    boolean editCategory(Category category);

    /**
     * 删除分类（逻辑删除）
     *
     * @param categoryId 分类ID
     * @return 是否成功
     */
    boolean deleteCategory(Long categoryId);

    /**
     * 获取分类视图对象
     *
     * @param category 分类实体
     * @return 分类视图对象
     */
    CategoryVO getCategoryVO(Category category);

    /**
     * 根据分类名称查询分类
     *
     * @param categoryName 分类名称
     * @return 分类列表
     */
    List<CategoryVO> getCategoriesByName(String categoryName);
}