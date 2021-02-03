package com.lsm1998.jedis.cmd.proxy;

import com.lsm1998.jedis.cmd.BaseRedisCommand;
import com.lsm1998.jedis.cmd.RedisCommand;
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
            if (!checkArgs(objects))
            {
                throw new ArgsException("参数数量校验不通过");
            }
            if (!checkType(objects))
            {
                throw new TypeException("类型校验不通过");
            }
            removeExpireKey(objects);
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
     * 参数数量校验
     *
     * @param objects
     * @return
     */
    private boolean checkArgs(Object[] objects)
    {
        // 第三项是args
        String[] args = (String[]) objects[2];
        String cond = this.target.argsCond();
        if (cond.startsWith("+"))
        {
            return args.length >= Integer.parseInt(cond.substring(1));
        }
        return args.length == Integer.parseInt(cond);
    }

    private boolean checkType(Object[] objects)
    {
        RedisType redisType = this.target.typeCond();
        if (redisType == null)
        {
            return true;
        }
        String key = (String) objects[1];
        RedisClientConnect connect = (RedisClientConnect) objects[0];
        RedisObject object = connect.getRedisDB().dict.get(key);
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
     * @param objects
     */
    private void removeExpireKey(Object[] objects)
    {
        if (this.target instanceof BaseRedisCommand)
        {
            return;
        }
        String key = (String) objects[1];
        RedisClientConnect connect = (RedisClientConnect) objects[0];
        Long expireTime = connect.getRedisDB().expires.get(key);
        if (expireTime != null && expireTime < System.currentTimeMillis())
        {
            connect.getRedisDB().expires.remove(key);
            connect.getRedisDB().dict.remove(key);
        }
    }
}
