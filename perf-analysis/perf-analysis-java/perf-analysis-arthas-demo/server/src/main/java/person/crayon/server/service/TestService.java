package person.crayon.server.service;

import org.springframework.stereotype.Service;
import person.crayon.server.entity.vo.Response;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Crayon
 * @date 2024/11/17 14:17
 */
@Service
public class TestService {
    private static final int TEST_STATIC = 0;

    @Resource
    private TenantService tenantService;

    public void demo1() {
        for (int i = 0; i < 100_000; i++) {
            tenantService.getTenantInfo();
        }
    }
}
