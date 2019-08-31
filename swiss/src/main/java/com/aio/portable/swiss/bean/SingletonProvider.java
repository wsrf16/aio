package com.aio.portable.swiss.bean;

import com.aio.portable.swiss.sandbox.a中文.AA;
import com.aio.portable.swiss.sandbox.a中文.BB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by York on 2017/11/23.
 */
public abstract class SingletonProvider {
    private static List<Object> instances = new ArrayList<>();

//    public SingletonProvider()
//    {
//        try {
//            bean = (T)this.getMyClass().newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

//    public static v bean(){
//        v v = null;
//        return v.getClass().newInstance();
//    }

    public static <T> T instance(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T instance = get(clazz);
        if (instance == null)
            synchronized (instances) {
                if (instance == null) {
                    instance = clazz.newInstance();
                    instances.add(instance);
                    return instance;
                }
            }
        return instance;
    }

    private static <T> T get(Class<T> clazz) {
        Object obj = instances.stream().filter(c -> c.getClass().equals(clazz)).findFirst().orElse(null);
        return (T) obj;
    }


//    //得到泛型类T
//    private Class getMyClass() {
//        //class com.dfsj.generic.UserDaoImpl因为是该类调用的该法，所以this代表它
//
//        //返回表示此 Class 所表示的实体类的 直接父类 的 Type。注意，是直接父类
//        //这里type结果是 com.dfsj.generic.GetInstanceUtil<com.dfsj.generic.User>
//        Type type = getClass().getGenericSuperclass();
//
//        // 判断 是否泛型
//        if (type instanceof ParameterizedType) {
//            // 返回表示此类型实际类型参数的Type对象的数组.
//            // 当有多个泛型类时，数组的长度就不是1了
//            Type[] ptype = ((ParameterizedType) type).getActualTypeArguments();
//            return (Class) ptype[0];  //将第一个泛型T对应的类返回（这里只有一个）
//        } else {
//            return Object.class;//若没有给定泛型，则返回Object类
//        }
//    }


    private static class BlahUnit {
        private static void todo() throws InstantiationException, IllegalAccessException {
            AA aa = SingletonProvider.instance(AA.class);
            aa.aa = 77;
            BB bb = SingletonProvider.instance(BB.class);
            bb.aa = 44;
            AA aa1 = SingletonProvider.instance(AA.class);
            BB bb2 = SingletonProvider.instance(BB.class);
        }
    }
}
