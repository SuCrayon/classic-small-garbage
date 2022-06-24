package person.crayon.service.impl;

import org.springframework.stereotype.Component;
import person.crayon.domain.DTO.RequestDTO;
import person.crayon.service.CrawlService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Crayon
 * @date 2021/12/29 14:10
 * @desc 描述功能
 */
@Component
public class CrawlServiceImpl implements CrawlService {
    /**
     * 请求队列
     */
    private final BlockingQueue<RequestDTO> requestQueue = new LinkedBlockingQueue<>();

    @Override
    public void crawl() {

    }
}
