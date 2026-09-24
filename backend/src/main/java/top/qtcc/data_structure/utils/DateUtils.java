package top.qtcc.data_structure.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author qiutuan
 * @date 2024/12/07
 */
public final class DateUtils {
    
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);
    
    /**
     * 私有构造方法，防止实例化
     */
    private DateUtils() {
        throw new UnsupportedOperationException("工具类不允许实例化");
    }
    
    /**
     * 格式化LocalDateTime为字符串
     *
     * @param dateTime 日期时间对象
     * @return 格式化后的字符串，如果输入为null则返回null
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DEFAULT_FORMATTER) : null;
    }

    /**
     * 解析字符串为LocalDateTime
     *
     * @param dateStr 日期字符串
     * @return LocalDateTime对象，如果解析失败则返回null
     */
    public static LocalDateTime parseDateTime(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateStr.trim(), DEFAULT_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date Date对象
     * @return LocalDateTime对象，如果输入为null则返回null
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime LocalDateTime对象
     * @return Date对象，如果输入为null则返回null
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return localDateTime != null ? Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    /**
     * 获取当前时间字符串
     *
     * @return 当前时间的格式化字符串
     */
    public static String getCurrentDateTimeStr() {
        return formatDateTime(LocalDateTime.now());
    }
    
    /**
     * 获取当前时间戳（毫秒）
     *
     * @return 当前时间戳
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
    
    /**
     * 判断两个LocalDateTime是否在同一天
     *
     * @param date1 第一个日期时间
     * @param date2 第二个日期时间
     * @return 如果在同一天则返回true
     */
    public static boolean isSameDay(LocalDateTime date1, LocalDateTime date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.toLocalDate().equals(date2.toLocalDate());
    }
    
    /**
     * 计算两个LocalDateTime之间的天数差
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return 天数差，如果任一参数为null则返回0
     */
    public static long daysBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }
        return java.time.Duration.between(start, end).toDays();
    }
} 