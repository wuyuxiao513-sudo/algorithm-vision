package top.qtcc.data_structure.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.qtcc.data_structure.domain.entity.RequestLog;
import top.qtcc.data_structure.domain.entity.User;
import top.qtcc.data_structure.service.RequestLogService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import top.qtcc.data_structure.service.UserService;

import java.util.UUID;

import static top.qtcc.data_structure.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 请求日志切面
 *
 * @author qiutuan
 * @date 2024/12/07
 */
@Aspect
@Component
@Slf4j
public class RequestLogAspect {

    @Resource
    private RequestLogService requestLogService;

    @Resource
    private UserService userService;

    private static final int MAX_PARAM_LENGTH = 1000;

    @Around("execution(* top.qtcc.data_structure.controller.*.*(..))")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        RequestLog requestLog = new RequestLog();
        requestLog.setRequestId(UUID.randomUUID().toString());

        // 获取请求信息
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        requestLog.setUrl(request.getRequestURI());
        requestLog.setMethod(request.getMethod());
        requestLog.setIp(request.getRemoteAddr());

        // 获取并保存请求参数
        Object[] args = joinPoint.getArgs();
        String params = truncateParams(args);
        requestLog.setParams(params);

        //TODO
        //保存用户信息如果用户登录


        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (attribute != null) {
            requestLog.setUserId(((User) attribute).getId());
        }

        Object result;
        try {
            result = joinPoint.proceed();
            requestLog.setStatus(200);
        } catch (Exception e) {
            requestLog.setStatus(500);
            requestLog.setErrorMsg(e.getMessage());
            throw e;
        } finally {
            requestLog.setCostTime(System.currentTimeMillis() - startTime);
            requestLogService.save(requestLog);
        }

        return result;
    }

    /**
     * 截断参数，防止日志过大
     */
    private String truncateParams(Object[] args) {
        if (args == null || args.length == 0) {
            return "[]";
        }

        String paramStr = "[" + StringUtils.join(args, ", ") + "]";

        if (paramStr.length() > MAX_PARAM_LENGTH) {
            return paramStr.substring(0, MAX_PARAM_LENGTH) + "... (truncated)";
        }

        return paramStr;
    }
}