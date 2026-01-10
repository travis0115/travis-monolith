package com.travis.monolith.module.demo.internal.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author travis
 * @email travis1215@vip.qq.com
 * @date 2020-09-12 01:05
 */
@Component
public class I18nUtils {

    private static MessageSource messageSource;

    @Autowired
    I18nUtils(MessageSource messageSource) {
        I18nUtils.messageSource = messageSource;
    }

    public static String getMessage(String msgCode) {
        var locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, null, locale);
    }
}
