package com.travis.infrastructure.framework.web.config;

import com.travis.infrastructure.common.constant.CustomHttpHeaders;
import com.travis.infrastructure.common.constant.WebFilterOrders;
import com.travis.infrastructure.framework.web.core.exception.advice.CommonExceptionHandlerAdvice;
import com.travis.infrastructure.framework.web.core.filter.AccessLogFilter;
import com.travis.infrastructure.framework.web.core.filter.RequestContextFilter;
import com.travis.infrastructure.framework.web.core.filter.RequestIdFilter;
import com.travis.infrastructure.framework.web.core.service.I18nService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author travis
 */
@AutoConfiguration
public class TravisWebMvcAutoConfiguration implements WebMvcConfigurer {

    /**
     * 跨域处理
     * 若使用 Spring Security,开启 http.cors(withDefaults())，会自动启用该配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders(
                        HttpHeaders.AUTHORIZATION
                        , HttpHeaders.CONTENT_DISPOSITION
                        , CustomHttpHeaders.REQUEST_ID
                )
                .maxAge(3600);

    }

    /**
     * 配置全局异常处理
     */
    @Bean
    @ConditionalOnMissingBean
    public CommonExceptionHandlerAdvice commonExceptionHandler(I18nService i18nService) {
        return new CommonExceptionHandlerAdvice(i18nService);
    }


    /**
     * 配置请求上下文过滤器
     */
    @Bean("travisRequestContextFilter")
    public FilterRegistrationBean<RequestContextFilter> requestContextFilter(@Qualifier(
            "handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        return createFilterBean(new RequestContextFilter(handlerExceptionResolver),
                WebFilterOrders.REQUEST_CONTEXT_FILTER);
    }

    /**
     * 配置 Access Log 过滤器
     */
    @Bean
    public FilterRegistrationBean<AccessLogFilter> accessLogFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        return createFilterBean(new AccessLogFilter(handlerExceptionResolver),
                WebFilterOrders.ACCESS_LOG_FILTER);
    }

    /**
     * 配置请求ID过滤器
     */
    @Bean
    public FilterRegistrationBean<RequestIdFilter> requestIdFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        return createFilterBean(new RequestIdFilter(handlerExceptionResolver),
                WebFilterOrders.REQUEST_ID_FILTER);
    }

    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

}
