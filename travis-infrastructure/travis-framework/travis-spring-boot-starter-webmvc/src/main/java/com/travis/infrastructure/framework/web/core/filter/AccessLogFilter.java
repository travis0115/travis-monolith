package com.travis.infrastructure.framework.web.core.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.travis.infrastructure.common.constant.CustomHttpHeaders;
import com.travis.infrastructure.common.constant.LogKeys;
import com.travis.infrastructure.common.constant.MdcKeys;
import com.travis.infrastructure.common.enums.ClientType;
import com.travis.infrastructure.common.enums.LogType;
import com.travis.infrastructure.framework.logging.core.enums.AccessLogger;
import com.travis.infrastructure.framework.web.core.utils.ServletUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArgument;
import org.jspecify.annotations.NonNull;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * 访问日志过滤器
 */
@Slf4j
public class AccessLogFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final boolean enabledAccessLog = Boolean.parseBoolean(SpringUtil.getProperty("logging.access.enabled",
            "true"));
    private final String accessLogOutput = SpringUtil.getProperty("logging.output", AccessLogger.STDOUT.name());


    public AccessLogFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        var beginTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        } finally {
            outputAccessLog(request, response, beginTime);
        }
    }

    private void outputAccessLog(HttpServletRequest request, HttpServletResponse response, long beginTime) throws IOException {
        if (!enabledAccessLog) {
            return;
        }
        try {
            var logType = LogType.ACCESS.name();
            var tenantId = request.getHeader(CustomHttpHeaders.TENANT_ID);
            var userId = request.getHeader(CustomHttpHeaders.USER_ID);
            var apiUrl = request.getRequestURI();
            var httpMethod = request.getMethod();
            var clientIp = ServletUtils.getClientIP(request);
            var userAgent = ServletUtils.getUserAgent();
            var clientType = ClientType.from(request.getHeader(CustomHttpHeaders.CLIENT_TYPE));
            var apiCost = (System.currentTimeMillis() - beginTime) + " ms";
            var requestParams = ServletUtils.getParamMap(request);
            var requestBody = ServletUtils.getJsonBody(request);
            requestBody = StrUtil.blankToDefault(requestBody, "{}");
            //TODO 日志数据脱敏

            var argumentsJson = new JSONObject();
            argumentsJson.set(MdcKeys.TENANT_ID, tenantId);
            argumentsJson.set(MdcKeys.USER_ID, userId);
            argumentsJson.set(LogKeys.LOG_TYPE, logType);
            argumentsJson.set(LogKeys.API_URL, apiUrl);
            argumentsJson.set(LogKeys.HTTP_METHOD, httpMethod);
            argumentsJson.set(LogKeys.CLIENT_IP, clientIp);
            argumentsJson.set(LogKeys.USER_AGENT, userAgent);
            argumentsJson.set(LogKeys.CLIENT_TYPE, clientType);
            argumentsJson.set(LogKeys.API_COST, apiCost);
            argumentsJson.set(LogKeys.REQUEST_PARAMS, requestParams);
            argumentsJson.set(LogKeys.REQUEST_BODY, requestBody);

            if ("dev".equalsIgnoreCase(SpringUtil.getActiveProfile())) {
                var logger = LoggerFactory.getLogger(this.getClass());
                logger.info(argumentsJson.toString());
            }
            if ("prod".equalsIgnoreCase(SpringUtil.getActiveProfile())) {
                var argumentsList = new ArrayList<StructuredArgument>();
                argumentsJson.forEach(argument -> {
                    argumentsList.add(kv(argument.getKey(), argument.getValue()));
                });
                var logger = LoggerFactory.getLogger(AccessLogger.from(accessLogOutput).getLoggerName());
                logger.info(LogType.ACCESS.name(), argumentsList.toArray());
            }
        } catch (Exception e) {
            log.error("Access log error", e);
        }
    }
}