package top.qtcc.data_structure.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import top.qtcc.data_structure.annotation.AuthCheck;
import top.qtcc.data_structure.annotation.RateLimit;
import top.qtcc.data_structure.annotation.RepeatSubmit;
import top.qtcc.data_structure.common.BaseResponse;
import top.qtcc.data_structure.common.ResultUtils;
import top.qtcc.data_structure.constant.UserConstant;
import top.qtcc.data_structure.domain.dto.category.CategoryRequest;
import top.qtcc.data_structure.domain.entity.Category;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.domain.vo.category.CategoryVO;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.service.CategoryService;

import java.util.List;

/**
 * 分类控制器
 *
 * @author qiutuan
 * @date 2024/11/16
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 获取分类详情
     *
     * @param id 分类ID
     * @return 分类详情
     */
    @RateLimit(time = 60, count = 100)
    @GetMapping("/getById")
    public BaseResponse<CategoryVO> getCategoryById(@RequestParam("id") Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }

        Category category = categoryService.getCategoryById(id);
        if (category == null || category.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "分类不存在");
        }
        CategoryVO categoryVO = categoryService.getCategoryVO(category);
        return ResultUtils.success(categoryVO);

    }

    /**
     * 获取所有分类列表
     *
     * @return 分类列表
     */
    @RateLimit()
    @GetMapping("/list")
    public BaseResponse<List<CategoryVO>> getAllCategories() {

        List<CategoryVO> categories = categoryService.getAllCategories();
        return ResultUtils.success(categories);

    }

    /**
     * 获取树形分类列表
     *
     * @return 树形分类列表
     */
    @RateLimit()
    @GetMapping("/tree")
    public BaseResponse<List<CategoryVO>> getCategoryTree() {

        List<CategoryVO> categoryTree = categoryService.getCategoryTree();
        return ResultUtils.success(categoryTree);
    }

    /**
     * 添加分类
     *
     * @param categoryRequest 分类请求信息
     * @return 操作结果
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @RateLimit(time = 60, count = 10)
    @RepeatSubmit(interval = 3000)
    @PostMapping("/add")
    public BaseResponse<Boolean> addCategory(@RequestBody CategoryRequest categoryRequest) {
        if (categoryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }

        Category category = convertToCategory(categoryRequest);
        boolean result = categoryService.addCategory(category);
        if (result) {
            return ResultUtils.success(true);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 修改分类
     *
     * @param categoryRequest 分类请求信息
     * @return 操作结果
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @RateLimit(time = 60, count = 10)
    @RepeatSubmit(interval = 3000)
    @PostMapping("/edit")
    public BaseResponse<Boolean> editCategory(@RequestBody CategoryRequest categoryRequest) {
        if (categoryRequest == null || categoryRequest.getCategoryId() == null) {
            return ResultUtils.error(400, "分类ID不能为空");
        }

        Category category = convertToCategory(categoryRequest);
        boolean result = categoryService.editCategory(category);
        if (result) {
            return ResultUtils.success(true);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 删除分类（逻辑删除）
     *
     * @param id 分类ID
     * @return 操作结果
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @RateLimit(time = 60, count = 5)
    @RepeatSubmit(interval = 5000)
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteCategory(@RequestParam("id") Long id) {
        if (id == null || id <= 0) {
            return ResultUtils.error(400, "分类ID不能为空");
        }

        boolean result = categoryService.deleteCategory(id);
        if (result) {
            return ResultUtils.success(true);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

    }

    /**
     * 根据分类名称查询分类
     *
     * @param categoryName 分类名称
     * @return 分类列表
     */
    @RateLimit(time = 60, count = 50)
    @GetMapping("/name")
    public BaseResponse<List<CategoryVO>> getCategoriesByName(@RequestParam(value = "categoryName", required = false) String categoryName) {
        List<CategoryVO> categories = categoryService.getCategoriesByName(categoryName);
        return ResultUtils.success(categories);
    }

    /**
     * 将CategoryRequest转换为Category实体
     *
     * @param categoryRequest 分类请求信息
     * @return Category实体
     */
    private Category convertToCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryRequest, category);
        return category;
    }
}