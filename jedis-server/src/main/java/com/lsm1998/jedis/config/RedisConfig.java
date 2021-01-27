/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis.config;

import com.lsm1998.jedis.common.utils.ArraysUtil;
import com.lsm1998.jedis.common.utils.ReflectObject;
import com.lsm1998.jedis.constant.SysProperties;
import lombok.Data;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RedisConfig
{
    public static final DefRedisConfig defRedisConfig = new DefRedisConfig();

    private static RedisConfig config = null;

    private static final Lock lock = new ReentrantLock();

    private final AtomicBoolean loadFlag;

    private RedisConfig()
    {
        this.loadFlag = new AtomicBoolean(true);
    }

    public static RedisConfig getInstance()
    {
        if (config == null)
        {
            try
            {
                lock.lock();
                if (config == null)
                {
                    config = new RedisConfig();
                }
            } finally
            {
                lock.unlock();
            }
        }
        return config;
    }

    public void load()
    {
        if (this.loadFlag.getAndSet(false))
        {
            Map<String, Class<?>> typeMap = new HashMap<>();
            ReflectObject reflect = new ReflectObject(defRedisConfig);
            for (Field field : reflect.getAllField())
            {
                typeMap.put(field.getName(), field.getType());
            }
            try (ConfigFetch fetch = new ConfigFetch(RedisConfig.class.getResourceAsStream(SysProperties.CONFIG_NAME)))
            {
                Map<String, String[]> arrayMap = new HashMap<>();
                String[] line;
                while ((line = fetch.next()) != null)
                {
                    Class<?> type = typeMap.get(line[0]);
                    if (type == null)
                    {
                        continue;
                    }
                    if (type == String.class)
                    {
                        reflect.setFieldVal(line[0], line[1]);
                    } else if (type == int.class || type == Integer.class)
                    {
                        reflect.setFieldVal(line[0], Integer.parseInt(line[1]));
                    } else if (type == boolean.class || type == Boolean.class)
                    {
                        reflect.setFieldVal(line[0], Boolean.parseBoolean(line[1]));
                    } else if (type == String[].class)
                    {
                        String[] array = arrayMap.get(line[0]);
                        if (array == null)
                        {
                            arrayMap.put(line[0], new String[]{line[1]});
                        } else
                        {
                            arrayMap.put(line[0], ArraysUtil.append(array.length, line[1], array, String.class));
                        }
                    }
                }
                arrayMap.forEach(reflect::setFieldVal);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Data
    public static class DefRedisConfig
    {
        private int port = 6379;

        private int timeout = 0;

        private int tcpKeepalive = 0;

        private int databases = 16;

        private String[] save = new String[]{"900 1", "300 10", "60 10000"};

        private String rdbCompression = "yes";

        private String rdbChecksum = "yes";

        private String dbFilename = "dump.rdb";

        private String appendOnly = "no";

        private String appendFilename = "appendonly.aof";
    }
}
