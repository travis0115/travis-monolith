package com.travis.infrastructure.framework.web.core.filter;

import cn.hutool.core.util.StrUtil;
import com.travis.infrastructure.common.web.constant.CustomHttpHeaders;
import com.travis.infrastructure.common.web.constant.MdcKeys;
import com.travis.infrastructure.framework.web.core.http.MutableHttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RequestIdFilter
 * <p>
 * 职责：
 * 1. 如果请求头存在 X-Request-Id -> 直接使用（不覆盖）
 * 2. 如果不存在 -> 自动生成 UUID
 * 3. 写入 MDC，方便日志打印 trace
 * 4. 回写到响应头，前端和 Nginx 都能拿到
 * <p>
 * 放在网关 / 单体入口 都适用
 */
@Slf4j
public class RequestIdFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public RequestIdFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        var wrappedRequest = new MutableHttpServletRequest(request);
        try {
            var requestId = wrappedRequest.getHeader(CustomHttpHeaders.REQUEST_ID);
            if (StrUtil.isBlank(requestId)) {
                requestId = RequestIdGenerator.nextId();
                wrappedRequest.putHeader(CustomHttpHeaders.REQUEST_ID, requestId);
            }
            MDC.put(MdcKeys.REQUEST_ID, requestId);
            response.setHeader(CustomHttpHeaders.REQUEST_ID, requestId);
            filterChain.doFilter(wrappedRequest, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(wrappedRequest, response, null, e);
        } finally {
            MDC.remove(MdcKeys.REQUEST_ID);
        }
        // 禁止执行完毕后添加代码
    }

    /**
     * 是否允许在发生错误时过滤请求
     */
    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }

    /**
     * 是否允许在异步处理时过滤请求
     */
    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    /**
     * requestId 生成器
     * 采用Base36算法生成，短ID
     */
    private static final class RequestIdGenerator {
        private static final AtomicInteger SEQ = new AtomicInteger();
        private static final Object LOCK = new Object();
        private static long lastTime = -1L;

        public static String nextId() {
            long now = System.currentTimeMillis();
            int seq;
            synchronized (LOCK) {
                if (now == lastTime) {
                    seq = SEQ.incrementAndGet() & 0xFFF;
                } else {
                    lastTime = now;
                    SEQ.set(0);
                    seq = 0;
                }
            }
            int rand = ThreadLocalRandom.current().nextInt(1 << 20);
            long value =
                    (now << 42)
                            | ((long) seq << 20)
                            | rand;
            return Long.toUnsignedString(value, 36);
        }
    }
}