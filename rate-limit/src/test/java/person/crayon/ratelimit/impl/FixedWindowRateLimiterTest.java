package person.crayon.ratelimit.impl;

import org.junit.Test;
import person.crayon.ratelimit.domain.LimitFreq;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @author Crayon
 * @date 2022/6/7 14:34
 */
public class FixedWindowRateLimiterTest {

    @Test
    public void test() throws InterruptedException {
        LimitFreq limitFreq = new LimitFreq().setAmount(1).setTimeUnit(TimeUnit.SECONDS);
        FixedWindowRateLimiter limiter = new FixedWindowRateLimiter(limitFreq);
        for (int i = 0; i < 64; i++) {
            System.out.println(limiter.tryAcquire());
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}