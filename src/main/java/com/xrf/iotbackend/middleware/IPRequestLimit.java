package com.xrf.iotbackend.middleware;

import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * 按 IP 计数的固定窗口限流：每个 IP 的首次请求开启一个 60 秒窗口，窗口内最多放行 100 次。
 *     每个 IP 独立计数，首次请求开启 60 秒窗口。
 *     窗口内前 100 次放行，第 101 次起返回 429。
 *     满 60 秒后重新开始，新窗口的第一次请求计为 1。
 */



@Component
public class IPRequestLimit implements HandlerInterceptor {


    // 同一个 IP 100 次请求的额度
    private static final int REQUEST_LIMIT = 100;
    // 60秒计时
    private static final long WINDOW_MILLIS = 60000L;
    // String 是 IP，Window 是这个 IP 的窗口起点startedAtMillis和已放行次数requestCount；不同 IP 分开计数。
    private final ConcurrentHashMap<String, Window> requestCountPerIP = new ConcurrentHashMap<>();

    // record 定义不可变的 Window 类型；每次更新时创建新的 Window 对象
    private record Window(long startedAtMillis, int requestCount) {
    }

    long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取IP
        String ipAddress = request.getRemoteAddr();
        // 获取当前系统时间
        long now = currentTimeMillis();
        // 每次请求各有一个 allowed，默认 false
        AtomicBoolean allowed = new AtomicBoolean();

        requestCountPerIP.compute(ipAddress, (ip, currentWindow) -> {
            // 第一次访问或原窗口已满 60 秒：重新开窗，本次请求算第 1 次并放行。
            if (currentWindow == null || now - currentWindow.startedAtMillis >= WINDOW_MILLIS) {
                allowed.set(true);
                return new Window(now, 1);
            }
            // 仍在原窗口且未用完额度：次数加 1，但窗口起点不变，不能因访问而续期。
            if (currentWindow.requestCount < REQUEST_LIMIT) {
                allowed.set(true);
                return new Window(currentWindow.startedAtMillis, currentWindow.requestCount + 1);
            }
            // 原窗口已有 100 次：保持窗口不变，allowed 仍为 false，本次请求会被拒绝。
            return currentWindow;
        });

        if (allowed.get()) {
            return true;
        }

        throw new BusinessException(ErrorCode.TOO_MANY_REQUESTS);
    }
}
