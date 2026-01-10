package com.travis.monolith.module.demo.internal.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author travis
 */
public class SecurityContextUtils {


    /**
     * 获取当前授权信息
     *
     * @return
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public static Object getUserDetails() {
        return getAuthentication().getPrincipal();
    }


}