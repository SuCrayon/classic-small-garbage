package person.crayon.ratelimit.impl;

import cn.hutool.core.date.DateUtil;
import person.crayon.ratelimit.RateLimiter;
import person.crayon.ratelimit.domain.LimitFreq;
import person.crayon.ratelimit.domain.LimitResult;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Crayon
 * @date 2022/6/7 12:17
 * 漏桶限流器
 */
public class LeakBucketRateLimiter implements RateLimiter {

    private final LinkedList<Long> requestTimeQueue;

    private final LimitFreq limitFreq;

    public LeakBucketRateLimiter(LimitFreq limitFreq) {
        this.limitFreq = limitFreq;
        this.requestTimeQueue = new LinkedList<>();
    }

    private void leakWater(long currentTime) {
        if (requestTimeQueue.isEmpty()) {
            return;
        }

        requestTimeQueue.removeIf(requestTime -> (int) ((double) ((currentTime - requestTime)) / limitFreq.getMilliTimeSpan() * limitFreq.getAmount()) >= 1);
    }

    public LimitResult tryAcquire() {
        long currentTime = DateUtil.current();
        leakWater(currentTime);

        LimitResult limitResult = LimitResult.forbid();
        if (requestTimeQueue.size() < (limitFreq.getAmount() >> 1)) {
            requestTimeQueue.addLast(currentTime);
            limitResult.setAllow(true);
        }

        return limitResult
                .setRemainCount((limitFreq.getAmount() >> 1) - requestTimeQueue.size())
                .setRemainResetTime(
                        requestTimeQueue.isEmpty() ? 0 : limitFreq.getMilliTimeSpan() / limitFreq.getAmount() + requestTimeQueue.peek() - currentTime
                );
    }
}
