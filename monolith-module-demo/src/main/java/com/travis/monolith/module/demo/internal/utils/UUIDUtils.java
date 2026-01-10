package com.travis.monolith.module.demo.internal.utils;

import java.util.UUID;

/**
 * @author travis
 * @date 2018-09-03 03:07
 */

public class UUIDUtils {
    public static String get() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
