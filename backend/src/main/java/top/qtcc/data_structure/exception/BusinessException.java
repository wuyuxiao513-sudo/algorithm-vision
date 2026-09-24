package top.qtcc.data_structure.exception;

import lombok.Getter;
import top.qtcc.data_structure.domain.enums.ErrorCode;


/**
 * 自定义异常类
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
    
    /**
     * 创建业务异常
     * 
     * @param errorCode 错误码枚举
     * @return 业务异常对象
     */
    public static BusinessException of(ErrorCode errorCode) {
        return new BusinessException(errorCode);
    }
    
    /**
     * 创建业务异常
     * 
     * @param code 错误码
     * @param message 错误信息
     * @return 业务异常对象
     */
    public static BusinessException of(int code, String message) {
        return new BusinessException(code, message);
    }

}
