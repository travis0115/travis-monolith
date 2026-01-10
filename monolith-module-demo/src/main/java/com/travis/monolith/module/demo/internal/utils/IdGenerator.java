package com.travis.monolith.module.demo.internal.utils;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;

/**
 * @author travis
 * @date 2020-03-21 13:35
 */

public class IdGenerator {

    /**
     * 分布式唯一ID
     * Mybatis Plus的实现，基于雪花算法+UUID
     * 自动计算数据中心ID和机器ID
     */
    public static synchronized String nextId() {
        var identifierGenerator = new DefaultIdentifierGenerator();
        return String.valueOf(identifierGenerator.nextId(null));
    }


}
