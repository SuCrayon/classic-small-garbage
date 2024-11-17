package person.crayon.ratelimit.impl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import person.crayon.ratelimit.RateLimiter;
import person.crayon.ratelimit.domain.LimitFreq;
import person.crayon.ratelimit.domain.LimitResult;

/**
 * @author Crayon
 * @date 2022/6/7 12:17
 * 固定窗口限流器
 * 多租户
 */
public class FixedWindowMultiTenantRateLimiter implements RateLimiter {

    @Getter
    @Accessors(chain = true)
    @ToString
    public static class LimitRecord {
        /**
         * 上次请求的时间
         */
        private long lastRequestTime;
        /**
         * 计数器
         */
        private int count;

        public LimitRecord() {
            this.lastRequestTime = DateUtil.current();
            this.count = 0;
        }

        public void setLastRequestTime(long lastRequestTime) {
            this.lastRequestTime = lastRequestTime;
        }

        public int clearCount() {
            this.count = 0;
            return this.count;
        }

        public int incrCount() {
            return ++this.count;
        }
    }

    @Deprecated
    private static final String DEFAULT_KEY = "default-key";

    /**
     * 缓存
     */
    private final TimedCache<String, LimitRecord> cache;

    /**
     * 限流频率
     */
    private final LimitFreq limitFreq;

    public FixedWindowMultiTenantRateLimiter(@org.jetbrains.annotations.NotNull LimitFreq limitFreq) {
        this.limitFreq = limitFreq;
        this.cache = CacheUtil.newTimedCache(limitFreq.getMilliTimeSpan());
        this.cache.schedulePrune(limitFreq.getMilliTimeSpan() >> 1);
    }

    /**
     * 默认限流标识
     * @return 限流结果
     */
    @Deprecated
    public LimitResult tryAcquire() {
        return tryAcquire(DEFAULT_KEY);
    }

    /**
     * 指定限流标识
     * @param throttleId 限流标识
     * @return 限流结果
     */
    public LimitResult tryAcquire(String throttleId) {
        long currentTime = DateUtil.current();
        LimitRecord limitRecord = cache.get(throttleId);
        if (cache.containsKey(throttleId)) {
            if (currentTime - limitRecord.getLastRequestTime() > limitFreq.getMilliTimeSpan()) {
                // 计数器清空
                limitRecord.clearCount();
                // 设置新的时间窗口左边界
                limitRecord.setLastRequestTime(currentTime);
            }
        } else {
            limitRecord = new LimitRecord();
            cache.put(throttleId, limitRecord);
        }

        return new LimitResult()
                // 防止溢出
                .setAllow(limitRecord.incrCount() > 0 && limitRecord.getCount() <= limitFreq.getAmount())
                .setRemainCount(Math.max(0, limitFreq.getAmount() - limitRecord.getCount()))
                .setRemainResetTime(limitRecord.getLastRequestTime() + limitFreq.getMilliTimeSpan() - currentTime);

    }
}
