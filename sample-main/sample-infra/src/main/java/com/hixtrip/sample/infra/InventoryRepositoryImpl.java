package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
@AllArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {

    private final RedisTemplate<String, Integer> redisTemplate;

    private static final String INVENTORY_KEY_TEMPLATE = "inventory:%s";

    public Integer getInventory(String skuId) {
        String key = String.format(INVENTORY_KEY_TEMPLATE, null != skuId ? skuId : "");
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        String key = String.format(INVENTORY_KEY_TEMPLATE, skuId);

        // Lua脚本，实现库存的原子性修改
        String script = "local current = tonumber(redis.call('GET', KEYS[1]))\n"
                + "if current then\n"
                + "    local new = current + tonumber(ARGV[1]) + tonumber(ARGV[2]) + tonumber(ARGV[3])\n"
                + "    if new >= 0 then\n"
                + "        redis.call('SET', KEYS[1], new)\n"
                + "        return true\n"
                + "    else\n"
                + "        return false\n"
                + "    end\n"
                + "else\n"
                + "    return false\n"
                + "end";

        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>(script, Boolean.class);
        List<String> keys = Collections.singletonList(key);
        List<Long> args = Arrays.asList(sellableQuantity, withholdingQuantity, occupiedQuantity);

        // 执行Lua脚本，确保操作的原子性
        return redisTemplate.execute(redisScript, keys, args);
    }


}
