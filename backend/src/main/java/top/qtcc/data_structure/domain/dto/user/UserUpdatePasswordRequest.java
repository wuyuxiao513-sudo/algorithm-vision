package top.qtcc.data_structure.domain.dto.user;

import com.mybatisflex.annotation.Id;
import lombok.Data;

/**
 * 用户更新密码请求
 *
 * @author qiutuan
 * @date 2025/11/21
 */
@Data
public class UserUpdatePasswordRequest {

    /**
     * 用户ID，主键，自增
     */
    @Id
    private Long id;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

}
