package com.aio.portable.swiss.structure.bean;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.lang.reflect.Array;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class CloneableSugar
{
    private static ConcurrentMap<Class<?>, BeanCopier> beanCopiers = new ConcurrentHashMap<Class<?>, BeanCopier>();

    public static Object clone(Object obj)
    {
        try
        {
            Object clone = obj.getClass().newInstance();

            BeanCopier copier = _createCopier(obj.getClass());

            copier.copy(obj, clone, new Converter()
            {
                @Override
                public Object convert(Object pojo, Class fieldType, Object fieldName)
                {
                    return _clone(pojo);
                }
            });

            return clone;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static Object _clone(Object bean)
    {
        if (bean == null)
        {
            return null;
        }
        else if (bean instanceof Cloneable)
        {
            return clone(bean);
        }
        else
        {

            if (bean.getClass().isArray() && !bean.getClass().getComponentType().equals(byte.class))
            {
                int length = Array.getLength(bean);
                Object clone = Array.newInstance(bean.getClass().getComponentType(), length);
                for (int i = 0; i < length; i++)
                {
                    Array.set(clone, i, _clone(Array.get(bean, i)));
                }
                return clone;
            }
            else
            {
                return bean;
            }
        }
    }


    private static BeanCopier _createCopier(Class<?> clz)
    {
        if (beanCopiers.containsKey(clz))
            return beanCopiers.get(clz);
        beanCopiers.putIfAbsent(clz, BeanCopier.create(clz, clz, true));
        return beanCopiers.get(clz);

    }
}