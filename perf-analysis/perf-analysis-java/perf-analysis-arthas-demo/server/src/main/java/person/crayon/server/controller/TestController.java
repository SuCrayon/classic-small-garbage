package person.crayon.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import person.crayon.server.entity.vo.Response;
import person.crayon.server.service.TestService;

import javax.annotation.Resource;

/**
 * @author Crayon
 * @date 2024/11/16 12:49
 */
@RestController
@RequestMapping("/test")
@CrossOrigin
@Slf4j
public class TestController {
    @Resource
    private TestService testService;

    @GetMapping("/demo1")
    public Response demo1() {
        log.info("demo1 start");
        testService.demo1();
        log.info("demo1 end");
        return Response.success();
    }
}
