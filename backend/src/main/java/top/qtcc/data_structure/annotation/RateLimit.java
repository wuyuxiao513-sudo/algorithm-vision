package top.qtcc.data_structure.annotation;

import java.lang.annotation.*;

/**
 *  限流注解
 *
 * @author qiutuan
 * @date 2024/12/06
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {
    /**
     * 限流时间窗口（秒）
     */
    int time() default 60;

    /**
     * 限流次数
     */
    int count() default 100;
}