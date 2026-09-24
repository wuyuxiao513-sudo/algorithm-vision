package top.qtcc.data_structure.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.qtcc.data_structure.utils.JwtUtil;
import top.qtcc.data_structure.service.impl.UserDetailsServiceImpl;
import top.qtcc.data_structure.service.TokenBlacklistService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证过滤器
 * 拦截HTTP请求，验证JWT Token并设置认证上下文
 * 
 * @author qiutuan
 * @date 2024/11/02
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ApplicationContext applicationContext;
    private final TokenBlacklistService tokenBlacklistService;

    /**
     * 过滤请求，验证JWT Token
     * 
     * @param request HTTP请求
     * @param response HTTP响应
     * @param filterChain 过滤器链
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        try {
            // 从请求头中获取JWT Token
            String jwt = getJwtFromRequest(request);
            
            if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {
                // 检查令牌是否在黑名单中
                if (tokenBlacklistService.isBlacklisted(jwt)) {
                    log.warn("令牌已被注销，拒绝访问");
                    // 清除认证上下文，确保用户被注销
                    SecurityContextHolder.clearContext();
                    throw new ServletException("令牌已被注销");
                } else {
                    // 从Token中提取用户ID
                    Long userId = jwtUtil.getUserIdFromToken(jwt);

                    // 加载用户详情（通过ApplicationContext获取UserDetailsService，避免循环依赖）
                    UserDetailsService userDetailsService = applicationContext.getBean(UserDetailsService.class);
                    UserDetails userDetails = ((UserDetailsServiceImpl) userDetailsService).loadUserById(userId);
                    
                    // 创建认证令牌
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // 设置认证上下文
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                    log.debug("设置认证上下文，用户ID: {}", userId);
                }
            }
        } catch (Exception ex) {
            log.error("JWT认证失败", ex);
        }
        
        // 继续过滤器链
        filterChain.doFilter(request, response);
    }

    /**
     * 从HTTP请求中提取JWT Token
     * 支持从Authorization头或Cookie中获取Token
     * 
     * @param request HTTP请求
     * @return JWT Token，如果不存在则返回null
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        // 从Authorization头获取Token
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        
        // 从Cookie中获取Token（可选）
        jakarta.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (jakarta.servlet.http.Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        
        return null;
    }
}