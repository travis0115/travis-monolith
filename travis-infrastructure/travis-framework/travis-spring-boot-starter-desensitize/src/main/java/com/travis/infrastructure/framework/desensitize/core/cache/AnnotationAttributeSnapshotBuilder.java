package com.travis.infrastructure.framework.desensitize.core.cache;

import org.jspecify.annotations.NonNull;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注解属性快照构建器
 * 用于将注解的属性值提取到Map中，便于后续处理
 */
public final class AnnotationAttributeSnapshotBuilder {

    /**
     * 方法句柄缓存，按注解类型分类存储
     * 使用ClassValue确保每个类只计算一次，提高性能
     */
    private static final ClassValue<Map<String, MethodHandle>> HANDLE_CACHE =
            new ClassValue<>() {
                @Override
                protected Map<String, MethodHandle> computeValue(@NonNull Class<?> type) {
                    Map<String, MethodHandle> map = new ConcurrentHashMap<>();
                    try {
                        var lookup = MethodHandles.lookup();
                        //注解类型的所有方法
                        for (var method : type.getMethods()) {
                            //只处理无参方法（注解属性访问器）
                            if (method.getParameterCount() != 0) {
                                continue;
                            }
                            //将方法名和对应的方法句柄存入缓存
                            map.put(method.getName(), lookup.unreflect(method));
                        }
                        return map;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            };

    /**
     *私构造函数，防止实例化
     */
    private AnnotationAttributeSnapshotBuilder() {
    }

    /**
     *构建注解属性快照
     */
    public static Map<String, Object> buildSnapshot(Annotation annotation) {
        var snapshot = new HashMap<String, Object>();
        // 获取注解类型对应的方法句柄映射
        var handles = HANDLE_CACHE.get(annotation.annotationType());
        try {
            //遍历所有方法句柄，调用并收集结果
            for (var entry : handles.entrySet()) {
                var value = entry.getValue().invoke(annotation);
                snapshot.put(entry.getKey(), value);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return snapshot;
    }
}
