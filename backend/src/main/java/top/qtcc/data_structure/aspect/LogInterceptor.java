package top.qtcc.data_structure.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 请求响应日志 AOP
 *
 * @author qiutuan
 * @date 2024/11/02
 **/
@Aspect
@Component
@Slf4j
public class LogInterceptor {

    private static final int MAX_PARAM_LENGTH = 1000;

    /**
     * 执行拦截
     */
    @Around("execution(* top.qtcc.data_structure.controller*.*(..))")
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {
        String requestId = UUID.randomUUID().toString();
        StopWatch stopWatch = new StopWatch();
        
        try {
            // 获取请求信息
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            
            String url = httpServletRequest.getRequestURI();
            String method = httpServletRequest.getMethod();
            String clientIp = getClientIpAddress(httpServletRequest);
            
            // 获取请求参数（限制长度防止日志过大）
            Object[] args = point.getArgs();
            String reqParam = truncateParams(args);
            
            // 输出请求日志
            log.info("请求开始 - ID: {}, 方法: {}, 路径: {}, IP: {}, 参数: {}", 
                    requestId, method, url, clientIp, reqParam);
            
            // 计时并执行原方法
            stopWatch.start();
            Object result = point.proceed();
            stopWatch.stop();
            
            long totalTimeMillis = stopWatch.getTotalTimeMillis();
            
            // 根据执行时间选择日志级别
            if (totalTimeMillis > 1000) {
                log.warn("请求结束 - ID: {}, 耗时: {}ms (性能警告)", requestId, totalTimeMillis);
            } else {
                log.info("请求结束 - ID: {}, 耗时: {}ms", requestId, totalTimeMillis);
            }
            
            return result;
            
        } catch (Exception e) {
            stopWatch.stop();
            long totalTimeMillis = stopWatch.getTotalTimeMillis();
            log.error("请求异常 - ID: {}, 耗时: {}ms, 异常: {}", 
                    requestId, totalTimeMillis, e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 获取客户端真实IP地址
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        
        return request.getRemoteAddr();
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

