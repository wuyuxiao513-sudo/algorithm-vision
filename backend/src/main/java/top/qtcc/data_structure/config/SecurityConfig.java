package top.qtcc.data_structure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.qtcc.data_structure.filter.JwtAuthenticationFilter;
import top.qtcc.data_structure.service.impl.UserDetailsServiceImpl;

/**
 * Spring Security 配置类
 * 配置认证授权规则、密码编码器、JWT过滤器等
 * 
 * @author qiutuan
 * @date 2024/11/02
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApplicationContext applicationContext;

    /**
     * 配置安全过滤器链
     * 定义HTTP安全规则、认证策略、过滤器顺序等
     * 
     * @param http HTTP安全配置对象
     * @return 安全过滤器链
     * @throws Exception 配置异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 禁用CSRF保护，因为使用JWT认证
            .csrf(csrf -> csrf.disable())
            // 配置会话管理为无状态
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 配置授权规则
            .authorizeHttpRequests(authz -> authz
                // 公开访问的接口
                .requestMatchers("/user/login", "/user/register", "/doc.html", "/webjars/**", "/v3/api-docs/**","/files/**","/article/list","/article/page").permitAll()
                // 需要管理员权限的接口
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                // 需要用户权限的接口
                .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                // 其他接口需要认证
                .anyRequest().authenticated()
            )
            // 添加JWT认证过滤器（通过ApplicationContext获取，避免循环依赖）
            .addFilterBefore(applicationContext.getBean(JwtAuthenticationFilter.class), UsernamePasswordAuthenticationFilter.class)
            // 配置异常处理
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(401);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"code\":40100,\"message\":\"未登录或Token无效\"}");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(403);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"code\":40101,\"message\":\"权限不足\"}");
                })
            );

        return http.build();
    }

    /**
     * 配置密码编码器
     * 使用BCrypt算法进行密码加密和验证
     * 
     * @return BCrypt密码编码器实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置认证管理器
     * 用于处理认证请求
     * 
     * @param authenticationConfiguration 认证配置
     * @return 认证管理器
     * @throws Exception 配置异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}