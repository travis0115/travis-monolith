package com.travis.monolith.module.demo.internal.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author travis
 * @date 2017-10-04 1:24
 */

public class StringUtils {
    /**
     * 是否为空字符串
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 是否非空
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

    /**
     * 正则表达式匹配
     *
     * @param str String
     * @return boolean
     */
    public static boolean isMatch(String str, String sRegExp) {
        Pattern pattern = Pattern.compile(sRegExp);
        Matcher isLegal = pattern.matcher(str);
        return isLegal.matches();
    }

    /**
     * 若为null，返回空字符串
     *
     * @param str
     * @return
     */
    public static String nullToBlank(Object str) {
        return str == null ? "" : String.valueOf(str).trim();
    }


    /**
     * 字符串打码
     *
     * @param str
     * @return
     */
    public static String mask(String str, int front, int end) {
        if (isBlank(str)) {
            return "";
        }
        return str.replaceAll("(?<=[\\w]{" + front + "})\\w(?=[\\w]{" + end + "})", "*");
    }

    /**
     * 驼峰 -> 蛇形
     *
     * @param str
     * @return
     */
    public static String toLineString(String str) {
        var letters = str.toCharArray();
        var sb = new StringBuilder();
        for (char c : letters) {
            if (Character.isUpperCase(c)) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }


}
