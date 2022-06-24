package person.crayon.core.channel;

import lombok.Getter;

/**
 * @author Crayon
 * @date 2021/12/29 14:13
 * @desc 爬虫通道
 */
@Getter
public abstract class Channel {
    private final String channelName;

    public Channel(String channelName) {
        this.channelName = channelName;
    }
}
