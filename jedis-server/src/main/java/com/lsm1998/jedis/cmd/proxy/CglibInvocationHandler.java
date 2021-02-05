package com.lsm1998.jedis.cmd.proxy;

import com.lsm1998.jedis.cmd.BaseRedisCommand;
import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.cmd.handler.NullValHandler;
import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.RedisType;
import com.lsm1998.jedis.common.exception.ArgsException;
import com.lsm1998.jedis.common.exception.TypeException;
import com.lsm1998.jedis.common.utils.ArraysUtil;
import com.lsm1998.jedis.connect.RedisClientConnect;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-13 10:49
 **/
public class CglibInvocationHandler implements MethodInterceptor
{
    private final RedisCommand target;

    public CglibInvocationHandler(RedisCommand target)
    {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable
    {
        if (method.getName().equals("handler"))
        {
            if (!setKey(objects))
            {
                throw new ArgsException("参数错误");
            }
            RedisClientConnect connect = (RedisClientConnect) objects[0];
            String key = (String) objects[1];
            String[] args = (String[]) objects[2];
            if (!checkArgs(args))
            {
                throw new ArgsException("参数数量校验不通过");
            }
            if (!checkType(connect, key))
            {
                throw new TypeException("类型校验不通过");
            }
            // 删除过期Key
            removeExpireKey(connect, key);
            // 尝试空值处理
            if (nullValHandler(connect, key))
            {
                return ((NullValHandler) this.target).nullVal();
            }
        }
        try
        {
            return methodProxy.invokeSuper(o, objects);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 空值处理
     *
     * @param connect
     * @param key
     * @return
     */
    private boolean nullValHandler(RedisClientConnect connect, String key)
    {
        return this.target instanceof NullValHandler && connect.getObject(key) == null;
    }

    /**
     * 参数数量校验
     *
     * @param args
     * @return
     */
    private boolean checkArgs(String[] args)
    {
        // 第三项是args
        String cond = this.target.argsCond();
        if (cond.startsWith("+"))
        {
            return args.length >= Integer.parseInt(cond.substring(1));
        }
        return args.length == Integer.parseInt(cond);
    }

    private boolean checkType(RedisClientConnect connect, String key)
    {
        RedisType redisType = this.target.typeCond();
        if (redisType == null)
        {
            return true;
        }
        RedisObject object = connect.getObject(key);
        if (object == null)
        {
            return true;
        }
        return object.getType() == redisType;
    }

    /**
     * 设置key
     *
     * @param objects
     * @return
     */
    private boolean setKey(Object[] objects)
    {
        String[] args = (String[]) objects[2];
        // 设置key
        objects[1] = args[0];
        objects[2] = args.length == 1 ? new String[]{} : ArraysUtil.remove(0, args, String.class);
        return true;
    }

    /**
     * 删除过期key
     *
     * @param key
     * @param connect
     */
    private void removeExpireKey(RedisClientConnect connect, String key)
    {
        if (this.target instanceof BaseRedisCommand)
        {
            return;
        }
        Long expireTime = connect.getRedisDB().expires.get(key);
        if (expireTime != null && expireTime < System.currentTimeMillis())
        {
            connect.getRedisDB().expires.remove(key);
            connect.getRedisDB().dict.remove(key);
        }
    }
}
