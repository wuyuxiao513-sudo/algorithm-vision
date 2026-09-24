package top.qtcc.data_structure.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.qtcc.data_structure.constant.CommonConstant;
import top.qtcc.data_structure.domain.dto.user.UserQueryRequest;
import top.qtcc.data_structure.domain.entity.User;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.domain.enums.UserRoleEnum;
import top.qtcc.data_structure.domain.vo.user.LoginUserVO;
import top.qtcc.data_structure.domain.vo.user.UserVO;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.mapper.UserMapper;
import top.qtcc.data_structure.service.TokenBlacklistService;
import top.qtcc.data_structure.service.UserService;
import top.qtcc.data_structure.utils.SqlUtils;

import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import top.qtcc.data_structure.utils.JwtUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private TokenBlacklistService tokenBlacklistService;

    /**
     * 盐值，混淆密码
     */
    public static final String SALT = "qiutuan";

    /**
     * 用户注册
     * 实现用户注册功能，包括参数校验、密码加密、账户唯一性检查等
     *
     * @param userAccount   用户账户（长度至少4位）
     * @param userPassword  用户密码（长度至少8位）
     * @param checkPassword 校验密码（必须与userPassword一致）
     * @return 新用户 id
     * @throws BusinessException 参数错误、账户重复、数据库错误等异常
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_account", userAccount);
            long count = this.getMapper().selectCountByQuery(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密（使用BCrypt密码编码器，与Spring Security保持一致）
            String encryptPassword = passwordEncoder.encode(userPassword);
            // 3. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            //默认用户名
            user.setUserName(userAccount);
            //默认头像
            user.setUserAvatar("http://algorithm.qtcc.top/files/user_avatar/1857700903633555460/yAfRNkQ8-00016-2372822254.png");
            boolean saveResult = this.save(user);

            user = this.getUserByUserAccount(userAccount);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    /**
     * 用户登录
     * 实现用户登录功能，包括参数校验、用户验证和密码验证
     *
     * @param userAccount  用户账户（长度至少4位）
     * @param userPassword 用户密码（长度至少8位）
     * @return 脱敏后的用户信息
     * @throws BusinessException 参数错误、用户不存在或密码错误等异常
     */
    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }

        // 查询用户是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_account", userAccount);
        User user = this.getMapper().selectOneByQuery(queryWrapper);

        // 用户不存在
        if (user == null) {
            log.info("用户登录失败，用户不存在，userAccount={}", userAccount);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }

        // 验证密码（使用BCrypt密码编码器验证）
        if (!passwordEncoder.matches(userPassword, user.getUserPassword())) {
            log.info("用户登录失败，密码错误，userAccount={}", userAccount);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }

        // 登录成功，生成JWT令牌
        String token = jwtUtil.generateToken(user.getId());
        LoginUserVO loginUserVO = this.getLoginUserVO(user);
        loginUserVO.setToken(token);

        log.info("用户登录成功，userAccount={}, userId={}, token={}", userAccount, user.getId(), token);
        return loginUserVO;
    }

    /**
     * 根据用户账户获取用户信息
     *
     * @param userAccount 用户账户
     * @return 用户实体
     */
    @Override
    public User getUserByUserAccount(String userAccount) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_account", userAccount);
        return this.getMapper().selectOneByQuery(queryWrapper);
    }

    /**
     * 获取当前登录用户
     * 从Spring Security上下文中获取已登录用户信息
     *
     * @return 当前登录用户实体
     * @throws BusinessException 用户未登录或用户信息无效时抛出异常
     */
    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return getUserByUserAccount(userDetails.getUsername());
    }

    /**
     *  更新用户密码
     *
     * @param loginUser 当前登录用户
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return {@link String }
     */
    @Override
    public String updatePassword(User loginUser, String oldPassword, String newPassword) {

        if (loginUser == null || loginUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (StringUtils.isAnyBlank(oldPassword, newPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (newPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "新密码过短");
        }

        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, loginUser.getUserPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "旧密码错误");
        }

        // 加密新密码
        String encryptPassword = passwordEncoder.encode(newPassword);

        // 更新密码
        User updateUser = new User();
        updateUser.setId(loginUser.getId());
        updateUser.setUserPassword(encryptPassword);
        boolean updateResult = this.updateById(updateUser);
        if (!updateResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "密码更新失败，数据库错误");
        }
        return "ok";
    }

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @return 当前登录用户
     */
    @Override
    public User getCurrentUserPermitNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            return null;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return getUserByUserAccount(userDetails.getUsername());
    }

    /**
     * 是否为管理员
     *
     * @return 是否为管理员
     */
    @Override
    public boolean isAdmin() {
        User currentUser = getCurrentUserPermitNull();
        return isAdmin(currentUser);
    }

    @Override
    public boolean isAdmin(User user) {
        if (user == null || user.getUserRole() == null) {
            return false;
        }
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(user.getUserRole());
        return userRoleEnum != null && UserRoleEnum.ADMIN.equals(userRoleEnum);
    }

    /**
     * 用户注销
     * 清除Spring Security认证上下文，并将当前令牌加入黑名单
     *
     * @return 注销结果
     */
    @Override
    public boolean userLogout() {
        try {
            // 获取当前请求的JWT令牌
            String token = getCurrentToken();

            if (token != null) {
                // 将令牌加入黑名单
                long expirationTime = jwtUtil.getExpirationDateFromToken(token).getTime();
                tokenBlacklistService.addToBlacklist(token, expirationTime);
                log.info("用户注销成功，令牌已加入黑名单");
            }

            // 清除Spring Security认证上下文
            SecurityContextHolder.clearContext();
            return true;
        } catch (Exception e) {
            log.error("用户注销失败", e);
            return false;
        }
    }

    /**
     * 获取当前请求的JWT令牌
     * 从HTTP请求头中提取Authorization头信息
     *
     * @return JWT令牌，如果不存在则返回null
     */
    private String getCurrentToken() {
        try {
            // 获取当前HTTP请求
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return null;
            }

            HttpServletRequest request = attributes.getRequest();
            String bearerToken = request.getHeader("Authorization");

            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }

            return null;
        } catch (Exception e) {
            log.error("获取当前令牌失败", e);
            return null;
        }
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        loginUserVO.setUserId(user.getId());
        return loginUserVO;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVO(List<User> userList) {
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    /**
     *  获取用户信息
     *
     * @param userId 用户ID
     * @return {@link User }
     */
    @Override
    public User getUser(Long userId) {

        if (userId == null || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "用户不存在");
        }
        return user;
    }

    /**
     * 获取查询条件
     * 根据用户查询请求构建MyBatis-Flex QueryWrapper查询条件
     * 支持多字段条件查询和排序功能
     *
     * @param userQueryRequest 用户查询请求，包含查询条件和排序参数
     * @return QueryWrapper查询条件对象
     * @throws BusinessException 请求参数为空时抛出异常
     */
    @Override
    public QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String unionId = userQueryRequest.getUnionId();
        String mpOpenId = userQueryRequest.getMpOpenId();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper queryWrapper = new QueryWrapper();
        if (id != null) {
            queryWrapper.eq("id", id);
        }
        if (StringUtils.isNotBlank(unionId)) {
            queryWrapper.eq("unionId", unionId);
        }
        if (StringUtils.isNotBlank(mpOpenId)) {
            queryWrapper.eq("mpOpenId", mpOpenId);
        }
        if (StringUtils.isNotBlank(userRole)) {
            queryWrapper.eq("userRole", userRole);
        }
        if (StringUtils.isNotBlank(userProfile)) {
            queryWrapper.like("userProfile", userProfile);
        }
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("userName", userName);
        }
        if (SqlUtils.validSortField(sortField)) {
            queryWrapper.orderBy(sortField, sortOrder.equals(CommonConstant.SORT_ORDER_ASC));
        }
        return queryWrapper;
    }



}
