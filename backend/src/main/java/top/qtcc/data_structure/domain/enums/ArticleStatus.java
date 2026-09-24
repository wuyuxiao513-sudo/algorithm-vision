package top.qtcc.data_structure.domain.enums;

import lombok.Getter;

/**
 * 文章状态枚举
 */
@Getter
public enum ArticleStatus {
    
    /**
     * 草稿
     */
    DRAFT(0, "草稿"),
    
    /**
     * 已发布
     */
    PUBLISHED(1, "已发布"),
    
    /**
     * 下架
     */
    OFFLINE(2, "下架"),
    
    /**
     * 审核中
     */
    UNDER_REVIEW(3, "审核中");
    
    private final Integer code;
    private final String desc;
    
    ArticleStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static ArticleStatus getByCode(Integer code) {
        for (ArticleStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
