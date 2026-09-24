package top.qtcc.data_structure.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.qtcc.data_structure.domain.entity.Category;
import top.qtcc.data_structure.domain.enums.CategoryStatus;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.domain.vo.category.CategoryVO;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.mapper.CategoryMapper;
import top.qtcc.data_structure.service.CategoryService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static top.qtcc.data_structure.domain.entity.table.CategoryTableDef.CATEGORY;

/**
 * 分类服务实现
 *
 * @author qiutuan
 * @date 2024/11/16
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 通过分类ID获取分类信息
     *
     * @param categoryId 分类ID
     * @return 分类实体
     */
    @Override
    public Category getCategoryById(Long categoryId) {
        if (categoryId == null || categoryId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return this.getById(categoryId);
    }

    /**
     * 获取所有分类列表
     *
     * @return 分类列表
     */
    public List<CategoryVO> getAllCategories() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("deleted", 0); // 未删除的分类
        queryWrapper.orderBy("sort_order asc", "create_time desc"); // 按排序和创建时间排序
        
        List<Category> categories = this.list(queryWrapper);
        return categories.stream().map(this::getCategoryVO).collect(Collectors.toList());
    }

    /**
     * 获取树形分类列表
     *
     * @return 树形分类列表
     */
    public List<CategoryVO> getCategoryTree() {
        List<CategoryVO> allCategories = getAllCategories();
        
        // 构建分类树
        Map<Long, CategoryVO> categoryMap = allCategories.stream()
                .collect(Collectors.toMap(CategoryVO::getCategoryId, category -> category));
        
        List<CategoryVO> rootCategories = new ArrayList<>();
        
        for (CategoryVO category : allCategories) {
            if (category.getParentId() == null || category.getParentId() == 0) {
                rootCategories.add(category);
            } else {
                CategoryVO parentCategory = categoryMap.get(category.getParentId());
                if (parentCategory != null) {
                    if (parentCategory.getChildren() == null) {
                        parentCategory.setChildren(new ArrayList<>());
                    }
                    parentCategory.getChildren().add(category);
                }
            }
        }
        
        return rootCategories;
    }

    /**
     * 添加分类
     *
     * @param category 分类实体
     * @return 是否成功
     */
    public boolean addCategory(Category category) {
        if (category == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        
        // 设置默认值
        if (category.getStatus() == null) {
            category.setStatus(CategoryStatus.ENABLED.getCode()); // 默认启用
        }
        if (category.getCreateTime() == null) {
            category.setCreateTime(LocalDateTime.now());
        }
        if (category.getUpdateTime() == null) {
            category.setUpdateTime(LocalDateTime.now());
        }
        if (category.getArticleCount() == null) {
            category.setArticleCount(0);
        }
        if (category.getSortOrder() == null) {
            category.setSortOrder(0);
        }
        if (category.getDeleted() == null) {
            category.setDeleted(0);
        }
        if (category.getParentId() == null) {
            category.setParentId(0L); // 默认顶级分类
        }
        
        return this.save(category);
    }

    /**
     * 修改分类
     *
     * @param category 分类实体
     * @return 是否成功
     */
    public boolean editCategory(Category category) {
        if (category == null || category.getCategoryId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        
        // 检查分类是否存在
        Category existingCategory = this.getById(category.getCategoryId());
        if (existingCategory == null || existingCategory.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "分类不存在");
        }
        
        // 更新修改时间
        category.setUpdateTime(LocalDateTime.now());
        
        return this.updateById(category);
    }

    /**
     * 删除分类（逻辑删除）
     *
     * @param categoryId 分类ID
     * @return 是否成功
     */
    public boolean deleteCategory(Long categoryId) {
        if (categoryId == null || categoryId <= 0) {
            return false;
        }
        
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setDeleted(1); // 逻辑删除
        category.setUpdateTime(LocalDateTime.now());
        
        return this.updateById(category);
    }

    /**
     * 获取分类视图对象
     *
     * @param category 分类实体
     * @return 分类视图对象
     */
    public CategoryVO getCategoryVO(Category category) {
        if (category == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category, categoryVO);
        
        // 设置状态描述
        CategoryStatus status = CategoryStatus.getByCode(category.getStatus());
        if (status != null) {
            categoryVO.setStatusDesc(status.getDesc());
        }
        
        return categoryVO;
    }

    /**
     * 根据分类名称查询分类
     *
     * @param categoryName 分类名称
     * @return 分类列表
     */
    public List<CategoryVO> getCategoriesByName(String categoryName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("deleted", 0); // 未删除的分类
        
        if (StringUtils.isNotBlank(categoryName)) {
            queryWrapper.like("category_name", categoryName);
        }
        
        queryWrapper.orderBy("sort_order asc", "create_time desc");
        
        List<Category> categories = this.list(queryWrapper);
        return categories.stream().map(this::getCategoryVO).collect(Collectors.toList());
    }
}