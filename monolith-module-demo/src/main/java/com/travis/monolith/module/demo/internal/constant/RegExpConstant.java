package com.travis.monolith.module.demo.internal.constant;

/**
 * 正则表达式
 *
 * @author travis
 * @date 2019-04-04 00:46
 */

public class RegExpConstant {

    /**
     * 用户名
     * 英文字母开头的6-16位账号，支持字母、数字和下划线
     */
    public static final String USERNAME = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$";


    /**
     * 手机号
     */
    public static final String MOBILE = "^$|^[1][3,4,5,6,7,8,9][0-9]{9}$";


}
