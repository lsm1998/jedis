package com.lsm1998.jedis.common.utils;

import java.lang.reflect.Field;

public class ReflectObject
{
    private final Object object;

    private final Class<?> clazz;

    public ReflectObject(Object object)
    {
        if (object == null)
        {
            throw new RuntimeException("object is null!");
        }
        this.object = object;
        this.clazz = this.object.getClass();
    }

    public void setFieldVal(String name, Object value)
    {
        try
        {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    public Object getFieldVal(String name)
    {
        try
        {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field.get(this.object);
        } catch (NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Class<?> fieldType(String name)
    {
        try
        {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field.getType();
        } catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Field[] getAllField()
    {
        return clazz.getDeclaredFields();
    }
}
