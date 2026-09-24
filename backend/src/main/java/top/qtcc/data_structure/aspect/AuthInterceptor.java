package top.qtcc.data_structure.aspect;

import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import top.qtcc.data_structure.annotation.AuthCheck;
import top.qtcc.data_structure.domain.entity.User;
import top.qtcc.data_structure.domain.enums.ErrorCode;
import top.qtcc.data_structure.domain.enums.UserRoleEnum;
import top.qtcc.data_structure.exception.BusinessException;
import top.qtcc.data_structure.service.UserService;

/**
 * 权限校验 AOP
 *
 * @author qiutuan
 * @date 2024/11/02
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    /**
     * 执行拦截
     *
     * @param joinPoint 切点
     * @param authCheck 权限校验注解
     * @return 执行结果
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        
        // 从Spring Security上下文中获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        
        // 获取当前登录用户
        User loginUser = userService.getCurrentUser();
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        
        // 不需要权限，放行
        if (mustRoleEnum == null) {
            return joinPoint.proceed();
        }
        
        // 必须有该权限才通过
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(loginUser.getUserRole());
        if (userRoleEnum == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 如果被封号，直接拒绝
        if (UserRoleEnum.BAN.equals(userRoleEnum)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        
        // 权限校验逻辑
        if (UserRoleEnum.ADMIN.equals(mustRoleEnum)) {
            // 必须有管理员权限
            if (!UserRoleEnum.ADMIN.equals(userRoleEnum)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "需要管理员权限");
            }
        } else if (UserRoleEnum.USER.equals(mustRoleEnum)) {
            // 必须有用户权限（管理员也具备用户权限）
            if (!UserRoleEnum.USER.equals(userRoleEnum) && !UserRoleEnum.ADMIN.equals(userRoleEnum)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "需要用户权限");
            }
        } else if (UserRoleEnum.BAN.equals(mustRoleEnum)) {
            // 被封禁用户不允许访问任何需要权限的接口
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "用户已被封禁");
        }
        
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}

