package person.crayon.ratelimit.impl;

import cn.hutool.core.date.DateUtil;
import person.crayon.ratelimit.RateLimiter;
import person.crayon.ratelimit.domain.LimitFreq;
import person.crayon.ratelimit.domain.LimitResult;

/**
 * @author Crayon
 * @date 2022/6/7 12:17
 * 固定窗口限流器
 */
public class FixedWindowRateLimiter implements RateLimiter {

    /**
     * 计数器
     */
    private int count;

    /**
     * 上次请求的时间
     */
    private long lastRequestTime;

    /**
     * 限流频率
     */
    private final LimitFreq limitFreq;

    public FixedWindowRateLimiter(@org.jetbrains.annotations.NotNull LimitFreq limitFreq) {
        this.count = 0;
        this.lastRequestTime = DateUtil.current();
        this.limitFreq = limitFreq;
    }

    /**
     *
     * @return 限流结果
     */
    public LimitResult tryAcquire() {
        long currentTime = DateUtil.current();
        if (currentTime - lastRequestTime > limitFreq.getMilliTimeSpan()) {
            count = 0;
            lastRequestTime = currentTime;
        }

        return new LimitResult()
                // 防止溢出
                .setAllow(++count > 0 && count <= limitFreq.getAmount())
                .setRemainCount(Math.max(0, limitFreq.getAmount() - count))
                .setRemainResetTime(lastRequestTime + limitFreq.getMilliTimeSpan() - currentTime);
    }

}
