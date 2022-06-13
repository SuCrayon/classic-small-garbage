package person.crayon.ratelimit.domain;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.concurrent.TimeUnit;

/**
 * @author Crayon
 * @date 2022/6/7 14:22
 * 限流结果
 */
@Data
@Accessors(chain = true)
@ToString
public class LimitResult {
    /**
     * 是否允许
     */
    private boolean allow;
    /**
     * 剩余请求次数
     */
    private int remainCount;
    /**
     * 剩余重置时间 ms
     */
    private long remainResetTime;

    public static LimitResult allow() {
        return new LimitResult().setAllow(true);
    }

    public static LimitResult forbid() {
        return new LimitResult().setAllow(false);
    }

    public long getRemainResetTimeSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds(remainResetTime);
    }
}
