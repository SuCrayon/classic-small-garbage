package person.crayon.ratelimit.impl;

import cn.hutool.core.util.RandomUtil;
import org.junit.Test;
import person.crayon.ratelimit.domain.LimitFreq;

import java.util.concurrent.TimeUnit;

/**
 * @author Crayon
 * @date 2022/6/7 16:29
 */
public class LeakBucketRateLimiterTest {
    @Test
    public void test() throws InterruptedException {
        LimitFreq limitFreq = new LimitFreq().setAmount(10).setTimeUnit(TimeUnit.MINUTES);
        LeakBucketRateLimiter limiter = new LeakBucketRateLimiter(limitFreq);
        for (int i = 0; i < 100; i++) {
            System.out.println(limiter.tryAcquire());
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}