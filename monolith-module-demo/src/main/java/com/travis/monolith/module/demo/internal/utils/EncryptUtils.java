package com.travis.monolith.module.demo.internal.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Tibbers
 * @date 2017-10-16 0:57
 */

public class EncryptUtils {
    /**
     * MD5加密
     *
     * @param data 待加密数据
     * @return byte[] 消息摘要
     */
    public static String md5Encode(String data) {
        return DigestUtils.md5DigestAsHex(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * BCrypt加密
     *
     * @param data
     * @return
     */
    public static String bCryptEncode(String data) {
        var bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(data);
    }

    /**
     * BCrypt验证密码
     *
     * @param rawPassword     待验证的密码
     * @param encodedPassword 正确密码（加密后）
     * @return
     */
    public static Boolean bCryptMatches(CharSequence rawPassword, String encodedPassword) {
        var bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }


}
