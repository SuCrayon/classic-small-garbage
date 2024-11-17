package person.crayon.server.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Crayon
 * @date 2024/11/17 16:16
 */
@Data
@AllArgsConstructor
public class Tenant {
    private String id;
    private String name;
}
