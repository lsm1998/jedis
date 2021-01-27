package com.lsm1998.jedis.cmd.proxy;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.exception.ArgsException;
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
    private final Object target;

    public CglibInvocationHandler(Object target)
    {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable
    {
        if (target instanceof RedisCommand && method.getName().equals("handler") && !checkArgs(objects))
        {
            throw new ArgsException("参数校验不通过");
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

    private boolean checkArgs(Object[] objects)
    {
        // 第三项是args
        String[] args = (String[]) objects[2];
        RedisCommand command = (RedisCommand) this.target;
        String cond = command.argsCond();
        if (cond.startsWith("+"))
        {
            return args.length >= Integer.parseInt(cond.substring(1));
        }
        return args.length == Integer.parseInt(cond);
    }
}
