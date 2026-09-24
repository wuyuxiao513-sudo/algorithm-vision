package top.qtcc.data_structure.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 令牌黑名单服务
 * 用于管理已注销的JWT令牌，实现真正的用户注销功能
 *
 * @author qiutuan
 * @date 2024/11/15
 */
//FIXME  jwt黑名单未生效
@Service
@Slf4j
public class TokenBlacklistService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final String BLACKLIST_KEY_PREFIX = "token:blacklist:";

    /**
     * 将令牌加入黑名单
     *
     * @param token JWT令牌
     * @param expirationTime 令牌过期时间（毫秒）
     */
    public void addToBlacklist(String token, long expirationTime) {
        try {
            // 计算令牌剩余的有效时间
            long currentTime = System.currentTimeMillis();
            long ttl = expirationTime - currentTime;
            
            // 如果令牌还有效，则加入黑名单
            if (ttl > 0) {
                String key = BLACKLIST_KEY_PREFIX + token;
                redisTemplate.opsForValue().set(key, "blacklisted", ttl, TimeUnit.MILLISECONDS);
                log.info("令牌已加入黑名单，剩余有效时间：{}毫秒", ttl);
            } else {
                // 如果令牌已过期，也加入黑名单，设置较短的过期时间（5分钟）
                // 这样可以防止短时间内重复使用已过期的令牌
                String key = BLACKLIST_KEY_PREFIX + token;
                redisTemplate.opsForValue().set(key, "blacklisted", 5, TimeUnit.MINUTES);
                log.info("令牌已过期，加入黑名单5分钟");
            }
        } catch (Exception e) {
            log.error("将令牌加入黑名单失败", e);
        }
    }

    /**
     * 检查令牌是否在黑名单中
     *
     * @param token JWT令牌
     * @return 是否在黑名单中
     */
    public boolean isBlacklisted(String token) {
        try {
            String key = BLACKLIST_KEY_PREFIX + token;
            Boolean exists = redisTemplate.hasKey(key);
            return exists != null && exists;
        } catch (Exception e) {
            log.error("检查令牌黑名单失败", e);
            return false;
        }
    }

    /**
     * 从黑名单中移除令牌（主要用于测试）
     *
     * @param token JWT令牌
     */
    public void removeFromBlacklist(String token) {
        try {
            String key = BLACKLIST_KEY_PREFIX + token;
            redisTemplate.delete(key);
            log.info("令牌已从黑名单中移除");
        } catch (Exception e) {
            log.error("从黑名单中移除令牌失败", e);
        }
    }
}