package top.qtcc.data_structure.domain.enums;

import lombok.Getter;

/**
 * 分类状态枚举
 */
@Getter
public enum CategoryStatus {
    
    /**
     * 禁用
     */
    DISABLED(0, "禁用"),
    
    /**
     * 启用
     */
    ENABLED(1, "启用");
    
    private final Integer code;
    private final String desc;
    
    CategoryStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static CategoryStatus getByCode(Integer code) {
        for (CategoryStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}