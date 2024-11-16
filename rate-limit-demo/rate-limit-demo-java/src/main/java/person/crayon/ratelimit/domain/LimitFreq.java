package person.crayon.ratelimit.domain;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.concurrent.TimeUnit;

/**
 * @author Crayon
 * @date 2022/6/7 14:07
 * 限流速率：amount/timeUnit
 * eg. 2/s => 2次每秒
 */
@Data
@Accessors(chain = true)
@ToString
public class LimitFreq {
    /**
     * 数值
     */
    private int amount;
    /**
     * 时间单位
     */
    private TimeUnit timeUnit;

    public long getMilliTimeSpan() {
        return timeUnit.toMillis(1);
    }
}
