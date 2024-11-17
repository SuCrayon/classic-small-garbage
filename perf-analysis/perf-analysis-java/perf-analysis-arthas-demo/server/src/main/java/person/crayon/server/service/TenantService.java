package person.crayon.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import person.crayon.server.entity.dto.Tenant;

import java.util.Objects;

/**
 * @author Crayon
 * @date 2024/11/17 16:13
 */
@Service
@Slf4j
public class TenantService {
    private final ThreadLocal<Tenant> tl = new ThreadLocal<>();

    public void setTenantInfo(Tenant tenant) {
        tl.set(tenant);
    }

    public Tenant getTenantInfo() {
        Tenant tenant = tl.get();
        if (Objects.isNull(tenant)) {
            // 频繁的日志输出会导致响应缓慢
            log.error("tenant is null");
        }
        return tenant;
    }
}
