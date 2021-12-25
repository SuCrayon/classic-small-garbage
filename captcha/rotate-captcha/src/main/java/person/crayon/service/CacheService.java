package person.crayon.service;

import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Crayon
 * @date 2021/12/25 22:47
 * @desc 缓存
 */
@Service
public class CacheService {
    private static final Map<String, Object> CACHE = new HashMap<>();

    public void set(String key, Object obj) {
        CACHE.put(key, JSONUtil.toJsonStr(obj));
    }

    public Object get(String key) {
        return new Object();
    }

    public <T> T get(String key, Class<T> clazz) {
        return JSONUtil.toBean((String) Optional.ofNullable(CACHE.get(key)).orElseThrow(() -> new RuntimeException("key为空")), clazz);
    }
}
