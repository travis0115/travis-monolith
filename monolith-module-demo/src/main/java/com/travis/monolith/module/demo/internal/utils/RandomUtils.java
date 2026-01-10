package com.travis.monolith.module.demo.internal.utils;

import java.util.Random;

/**
 * @author travis
 * @date 2018-01-27 19:28
 */

public class RandomUtils {

    /**
     * 生成指定长度的数字
     *
     * @param length
     * @return
     */
    public static String getDigit(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(getPositiveOrZeroInteger(10));
        }
        return sb.toString();
    }

    /**
     * 生成指定范围内的随机正整数
     * [0,range)
     *
     * @param range
     * @return
     */
    public static int getPositiveOrZeroInteger(int range) {
        return (int) (range * Math.random());
    }

    /**
     * 生成指定范围内的随机整数（含负数）
     * [-range,range]
     *
     * @return
     */
    public static int getRandomInteger(int range) {
        var random = new Random();
        return random.nextInt(2 * range + 1) - range;
    }

    /**
     * 获取指定位数的随机小写字母组合
     *
     * @param length
     * @return
     */
    public static String getLowerLetters(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return result.toString();
    }

    /**
     * 获取指定位数的随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String chars = "abcdefghijkmnpqrstuvwxyz23456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return result.toString();
    }
}
