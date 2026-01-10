package com.travis.monolith.module.demo.internal.utils;

import com.travis.monolith.module.demo.internal.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * @author travis
 * @date 2018-10-30 20:50
 */
@Component
public class DateTimeUtils {
    public static ZoneId ZONE_ID;

    @Value("${application.zone-id}")
    public void setZoneId(String zoneId) {
        DateTimeUtils.ZONE_ID = ZoneId.of(zoneId);

    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static LocalDateTime getTime() {
        return LocalDateTime.ofInstant(Instant.now(), ZONE_ID);
    }

    /**
     * 获取当前默认格式化时间
     *
     * @return
     */
    public static String getFormatTime() {
        return getTime().format(DateTimeFormatter.ofPattern(CommonConstant.DEFAULT_DATE_TIME_FORMAT));
    }

    /**
     * 获取当前格式化时间
     *
     * @param pattern
     * @return
     */
    public static String getFormatTime(String pattern) {
        return getTime().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 时间 -> 字符串
     *
     * @param time 时间
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(CommonConstant.DEFAULT_DATE_TIME_FORMAT));
    }


    /**
     * 时间 -> 字符串
     *
     * @param time    时间
     * @param pattern 模版，例如"yyyy-MM-dd HH:mm:ss"
     * @return 转换后的字符串 2018-05-08 16:13:48
     */
    public static String formatTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 时间格式转换
     */
    public static String parse(String oldDateTime, String oldPattern, String newPattern) {
        LocalDateTime localDateTime = LocalDateTime.parse(oldDateTime, DateTimeFormatter.ofPattern(oldPattern));
        return localDateTime.format(DateTimeFormatter.ofPattern(newPattern));
    }

    /**
     * 获取n分钟后的时间
     *
     * @param minutes
     * @return
     */
    public static LocalDateTime getMinutesAfterTime(Long minutes) {
        return getTime().plusMinutes(minutes);
    }

    /**
     * 获取n小时后的时间
     *
     * @param hours
     * @return
     */
    public static LocalDateTime getHoursAfterTime(Long hours) {
        return getTime().plusHours(hours);
    }

    /**
     * 获取n日后时间
     *
     * @param days
     * @return
     */
    public static LocalDateTime getDaysAfterTime(Long days) {
        return getTime().plusDays(days);
    }


    /**
     * 获取n日前开始时间
     *
     * @return
     */
    public static LocalDateTime getDaysBeforeStartTime(Long n) {
        return LocalDateTime.of(getTime().toLocalDate(), LocalTime.MIN).minusDays(n);
    }

    /**
     * 获取n日前开始时间
     * 默认格式
     *
     * @return
     */
    public static String getDaysBeforeStartFormatTime(Long n) {
        LocalDateTime localDateTime = LocalDateTime.of(getTime().toLocalDate(), LocalTime.MIN);
        localDateTime = localDateTime.minusDays(n);
        return localDateTime.format(DateTimeFormatter.ofPattern(CommonConstant.DEFAULT_DATE_TIME_FORMAT));
    }

    /**
     * 获取n日前开始时间
     * 指定格式
     *
     * @return
     */
    public static String getDaysBeforeStartFormatTime(Long n, String pattern) {
        LocalDateTime localDateTime = LocalDateTime.of(getTime().toLocalDate(), LocalTime.MIN);
        localDateTime = localDateTime.minusDays(n);
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取n月前开始时间
     *
     * @return
     */
    public static LocalDateTime getMonthsBeforeStartTime(Long n) {
        return LocalDateTime.of(getTime().toLocalDate(), LocalTime.MIN).minusMonths(n).with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取时间差
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Duration getDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime);
    }
}
