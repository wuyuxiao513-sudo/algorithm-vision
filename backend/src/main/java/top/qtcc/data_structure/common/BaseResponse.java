package top.qtcc.data_structure.common;

import lombok.Data;
import top.qtcc.data_structure.domain.enums.ErrorCode;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
    
    /**
     * 创建成功响应
     * 
     * @param data 响应数据
     * @return 成功响应对象
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "success");
    }
    
    /**
     * 创建成功响应（无数据）
     * 
     * @return 成功响应对象
     */
    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(0, null, "success");
    }
    
    /**
     * 创建错误响应
     * 
     * @param errorCode 错误码
     * @return 错误响应对象
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }
    
    /**
     * 创建错误响应
     * 
     * @param code 错误码
     * @param message 错误信息
     * @return 错误响应对象
     */
    public static <T> BaseResponse<T> error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }
}
