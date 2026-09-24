package top.qtcc.data_structure.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import top.qtcc.data_structure.domain.dto.user.UserQueryRequest;
import top.qtcc.data_structure.domain.entity.User;
import top.qtcc.data_structure.domain.vo.user.LoginUserVO;
import top.qtcc.data_structure.domain.vo.user.UserVO;

import java.util.List;

/**
 * 用户服务
 *
 * @author qiutuan
 * @date 2024/11/02
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword);

    /**
     * 根据用户账户获取用户信息
     *
     * @param userAccount 用户账户
     * @return 用户实体
     */
    User getUserByUserAccount(String userAccount);

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    User getCurrentUser();

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @return 当前登录用户
     */
    User getCurrentUserPermitNull();

    /**
     * 是否为管理员
     *
     * @return 是否为管理员
     */
    boolean isAdmin();

    /**
     * 是否为管理员
     *
     * @param user 用户
     * @return 是否为管理员
     */
    boolean isAdmin(User user);

    /**
     * 用户注销
     *
     * @return 是否注销成功
     */
    boolean userLogout();

    /**
     * 获取脱敏的已登录用户信息
     *
     * @param user 用户
     * @return 脱敏后的用户信息
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param user 用户
     * @return 脱敏后的用户信息
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param userList 用户列表
     * @return 脱敏后的用户信息
     */
    List<UserVO> getUserVO(List<User> userList);


    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    User getUser(Long userId);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest 用户查询请求
     * @return 查询条件
     */
    QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest);

    String updatePassword(User loginUser, String oldPassword, String newPassword);
}
