package person.crayon.domain.DTO;

import java.util.Date;

/**
 * @author Crayon
 * @date 2021/12/29 14:15
 * @desc 代理DTO
 */
public class ProxyDTO {
    private String ip;
    private String hostname;
    private Integer port;
    /*private AnonymityEnum anonymity;
    private ProtocolEnum protocol;*/
    private String address;
    private String country;
    private String isp;
    private String channelName;
    // 秒为单位
    private Double speed;
    // 上次验证时间
    private Date lastValidTime;
}
