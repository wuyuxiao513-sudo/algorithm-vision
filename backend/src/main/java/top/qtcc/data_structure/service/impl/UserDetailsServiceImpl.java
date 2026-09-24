package top.qtcc.data_structure.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.qtcc.data_structure.domain.entity.User;
import top.qtcc.data_structure.domain.enums.UserRoleEnum;
import top.qtcc.data_structure.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义UserDetailsService实现
 * 集成现有用户数据到Spring Security认证体系
 * 
 * @author qiutuan
 * @date 2024/11/02
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    /**
     * 根据用户名加载用户详情
     * 将数据库中的用户信息转换为Spring Security可识别的UserDetails对象
     * 
     * @param username 用户名（用户账户）
     * @return 用户详情对象
     * @throws UsernameNotFoundException 用户不存在时抛出异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        User user = userService.getUserByUserAccount(username);
        if (user == null) {
            log.warn("用户不存在: {}", username);
            throw new UsernameNotFoundException("用户不存在或密码错误");
        }

        // 检查用户状态
        if (UserRoleEnum.BAN.getValue().equals(user.getUserRole())) {
            log.warn("用户已被封禁: {}", username);
            throw new UsernameNotFoundException("用户已被封禁");
        }

        // 构建权限集合
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        
        // 根据用户角色添加权限
        String userRole = user.getUserRole();
        UserRoleEnum roleEnum = UserRoleEnum.getEnumByValue(userRole);
        
        if (roleEnum != null) {
            switch (roleEnum) {
                case ADMIN:
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                    break;
                case USER:
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                    break;
                case BAN:
                    // 被封禁用户不添加任何权限
                    break;
                default:
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                    break;
            }
        } else {
            // 默认给用户权限
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // 构建UserDetails对象
        return new org.springframework.security.core.userdetails.User(
            user.getUserAccount(),
            user.getUserPassword(), // 注意：这里需要存储加密后的密码
            true, // 账户是否启用
            true, // 账户是否未过期
            true, // 凭证是否未过期
            !UserRoleEnum.BAN.getValue().equals(userRole), // 账户是否未锁定
            authorities
        );
    }

    /**
     * 根据用户ID加载用户详情
     * 用于JWT认证时根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户详情对象
     * @throws UsernameNotFoundException 用户不存在时抛出异常
     */
    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        User user = userService.getById(userId);
        if (user == null) {
            log.warn("用户不存在，用户ID: {}", userId);
            throw new UsernameNotFoundException("用户不存在");
        }

        return loadUserByUsername(user.getUserAccount());
    }

    /**
     * 根据用户ID加载用户详情（通过用户账户名）
     * 用于JWT认证时根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户详情对象
     * @throws UsernameNotFoundException 用户不存在时抛出异常
     */
    public UserDetails loadUserByIdString(String userId) throws UsernameNotFoundException {
        try {
            Long id = Long.parseLong(userId);
            return loadUserById(id);
        } catch (NumberFormatException e) {
            log.warn("用户ID格式错误: {}", userId);
            throw new UsernameNotFoundException("用户ID格式错误");
        }
    }
}