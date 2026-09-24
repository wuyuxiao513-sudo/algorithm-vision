package top.qtcc.data_structure.utils;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author qiutuan
 * @date 2024/12/07
 */
public final class StringUtils {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    
    /**
     * 私有构造方法，防止实例化
     */
    private StringUtils() {
        throw new UnsupportedOperationException("工具类不允许实例化");
    }
    
    /**
     * 判断字符串是否为空或空白
     * 
     * @param str 待检查的字符串
     * @return 如果字符串为null、空字符串或仅包含空白字符，则返回true
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * 判断字符串是否为空
     * 
     * @param str 待检查的字符串
     * @return 如果字符串为null或空字符串，则返回true
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
    
    /**
     * 生成不带连字符的UUID
     * 
     * @return 32位UUID字符串
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 验证邮箱格式
     * 
     * @param email 待验证的邮箱地址
     * @return 如果邮箱格式正确则返回true
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * 验证手机号格式
     * 
     * @param phone 待验证的手机号
     * @return 如果手机号格式正确则返回true
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * 隐藏手机号中间四位
     * 
     * @param phone 手机号
     * @return 隐藏后的手机号，如果输入无效则返回原值
     */
    public static String maskPhone(String phone) {
        if (!isValidPhone(phone)) {
            return phone;
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }
    
    /**
     * 隐藏邮箱用户名部分
     * 
     * @param email 邮箱地址
     * @return 隐藏后的邮箱地址，如果输入无效则返回原值
     */
    public static String maskEmail(String email) {
        if (!isValidEmail(email)) {
            return email;
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return email;
        }
        return email.charAt(0) + "***" + email.substring(atIndex);
    }
    
    /**
     * 截断字符串，如果超过指定长度则添加省略号
     * 
     * @param str 原始字符串
     * @param maxLength 最大长度
     * @return 截断后的字符串
     */
    public static String truncate(String str, int maxLength) {
        if (str == null || str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + "...";
    }
} 