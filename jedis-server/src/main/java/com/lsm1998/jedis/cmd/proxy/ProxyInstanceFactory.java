package com.lsm1998.jedis.cmd.proxy;

import com.lsm1998.jedis.cmd.RedisCommand;
import net.sf.cglib.proxy.Enhancer;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-13 10:52
 **/
public class ProxyInstanceFactory
{
    public static <T> T getInstance(RedisCommand instance)
    {
        CglibInvocationHandler handler = new CglibInvocationHandler(instance);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(instance.getClass());
        enhancer.setCallback(handler);
        return (T) enhancer.create();
    }
}
