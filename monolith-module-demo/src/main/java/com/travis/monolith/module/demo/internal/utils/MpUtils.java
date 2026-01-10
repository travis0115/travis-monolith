package com.travis.monolith.module.demo.internal.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travis.monolith.module.demo.internal.enums.ErrorCodeEnum;
import com.travis.monolith.module.demo.internal.exception.BusinessException;
import com.travis.monolith.module.demo.internal.exception.NotFoundException;
import com.travis.monolith.module.demo.internal.model.dto.PageRequest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatisPlus 工具类
 *
 * @author travis
 * @date 2020-02-20 18:20
 */

public class MpUtils {
    public static <T> Page<T> generatePage(Integer current, Integer size) {
        return new Page<>(current == null ? 1 : current, size == null ? 10 : size);
    }

    public static <T> Page<T> generatePage(PageRequest pageRequest) {
        if (pageRequest == null) {
            return generatePage(1, 10);
        }
        Page<T> pager = generatePage(pageRequest.getCurrent(), pageRequest.getSize());
        Boolean asc = pageRequest.getAsc();
        String by = pageRequest.getOrderItem();
        List<OrderItem> orderItems = new ArrayList<>(1);
        if (StringUtils.isNotBlank(by)) {
            OrderItem orderItem = new OrderItem();
            orderItem.setAsc(asc == null ? false : asc);
            orderItem.setColumn(StringUtils.toLineString(by));
            orderItems.add(orderItem);
        }
        pager.setOrders(orderItems);
        return pager;
    }

    public static <T> QueryWrapper<T> generateFuzzyQueryWrapper(QueryWrapper<T> queryWrapper, Object obj) {
        Field[] field = obj.getClass().getDeclaredFields();
        try {
            for (Field item : field) {
                String name = item.getName();
                String getMethodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                String type = item.getGenericType().toString();
                if ("class java.lang.String".equals(type)) {
                    Method m = obj.getClass().getMethod(getMethodName);
                    String value = (String) m.invoke(obj);
                    if (StringUtils.isNotBlank(value)) {
                        queryWrapper.like(StringUtils.toLineString(name), value);
                    }
                }
                if ("class java.lang.Long".equals(type)) {
                    Method m = obj.getClass().getMethod(getMethodName);
                    Long value = (Long) m.invoke(obj);
                    if (value != null) {
                        queryWrapper.like(StringUtils.toLineString(name), value);
                    }
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e, ErrorCodeEnum.GET_PROPERTY_FAILED);
        }
        return queryWrapper;
    }


    public static void checkResult(boolean success, String errorMsg) {
        if (!success) {
            var rc = ErrorCodeEnum.DATABASE_OPERATION_FAILED;
            rc.setMsg(errorMsg);
            throw new BusinessException(rc);
        }
    }

    public static void checkResult(boolean success) {
        if (!success) {
            throw new BusinessException(ErrorCodeEnum.DATABASE_OPERATION_FAILED);
        }
    }


    public static Object checkExist(Object o, String errorMsg) {
        if (o == null) {
            var rc = ErrorCodeEnum.DATABASE_SELECT_NOT_FOUND;
            rc.setMsg(errorMsg);
            throw new NotFoundException(rc);
        }
        return o;
    }
}
