package person.crayon.server.entity.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Crayon
 * @date 2024/11/17 12:45
 */
@Data
public class Response {
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Response success() {
        return new Response(0, "success");
    }

    public static Response failed() {
        return new Response(-1, "failed");
    }
}
