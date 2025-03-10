package com.aio.portable.swiss.suite.bean;

import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public abstract class DeepCloneSugar {
    public abstract static class Json {
        /**
         * deepCopy
         * @param source
         * @param targetClass
         * @param <T>
         * @return
         */
        public static final <S, T> T clone(S source, Class<T> targetClass) {
            T t = JacksonSugar.json2T(JacksonSugar.obj2Json(source), targetClass);
            return t;
        }

        /**
         * deepCopy
         * @param source
         * @param valueTypeRef
         * @param <T>
         * @return
         */
        public static final <S, T> T clone(S source, TypeReference<T> valueTypeRef) {
            T t = JacksonSugar.json2T(JacksonSugar.obj2Json(source), valueTypeRef);
//            T t = DeepCloneSugar.Json.clone(source, valueTypeRef);
            return t;
        }

        /**
         * deepCopy
         * @param source
         * @param <T>
         * @return
         */
        public static final <T> T clone(T source) {
            T t = (T) JacksonSugar.json2T(JacksonSugar.obj2Json(source), source.getClass());
//            T t = (T) DeepCloneSugar.Json.clone(source, source.getClass());
            return t;
        }

        public static final <S, T> ArrayList<T> cloneList(Collection<S> source) {
            ArrayList<T> list = JacksonSugar.deepCopy(source, new TypeReference<ArrayList<T>>(){});
            return list;
        }

        public static final HashMap<Object, Object> clone2ObjectMap(Object source) {
            HashMap<Object, Object> map = JacksonSugar.deepCopy(source, new TypeReference<HashMap<Object, Object>>(){});
            return map;
        }

        public static final HashMap<String, Object> clone2StringMap(Object source) {
            HashMap<String, Object> map = JacksonSugar.deepCopy(source, new TypeReference<HashMap<String, Object>>(){});
            return map;
        }

//        public static final HashMap<Object, Object> json2ObjectMap(String jsonStr) {
//            return JacksonSugar.json2ObjectMap(jsonStr);
//        }
//
//        public static final HashMap<String, Object> json2StringMap(String jsonStr) {
//            return JacksonSugar.json2StringMap(jsonStr);
//        }
    }

    public abstract static class Properties {
        public static final <S> S clone(S source) {
            try {
                S target = (S)source.getClass().getConstructor().newInstance();
                clone(source, target);
                return target;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static final <S, T> void clone(S source, T target) {
            BeanUtils.copyProperties(source, target);
        }

        public static final <S, T> T clone(S source, Class<T> targetClazz) {
            try {
                T target = targetClazz.getConstructor().newInstance();
                clone(source, target);
                return target;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static final <S, T> List<T> cloneList(Collection<S> source, Class<T> clazz) {
            List<T> list = source.stream().map(c -> DeepCloneSugar.Properties.clone(c, clazz)).collect(Collectors.toList());
            return list;
        }

        public static final <T> List<T> cloneList(Collection<T> source) {
            List<T> list = source.stream().map(c -> DeepCloneSugar.Properties.clone(c)).collect(Collectors.toList());
            return list;
        }

        public static final <K, V> HashMap<K, V> clone2Map(HashMap<K, V> source) {
            return new HashMap<>(source);
        }
    }

    public abstract static class Cglib {
        public static final <S> S clone(S source) {
            try {
                S target = (S)source.getClass().getConstructor().newInstance();
                clone(source, target);
                return target;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static final <S, T> void clone(S source, T target) {
            BeanCopier copier = createCopier(source.getClass(), target.getClass());
            copier.copy(source, target, new BeanConverter());
        }

        public static final <S, T> T clone(S source, Class<T> targetClazz) {
            try {
                T target = targetClazz.getConstructor().newInstance();
                BeanCopier copier = createCopier(source.getClass(), targetClazz);
                copier.copy(source, target, new BeanConverter());
                return target;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private static final ConcurrentMap<Class<?>, BeanCopier> beanCopierCache = new ConcurrentHashMap<Class<?>, BeanCopier>();

        private static BeanCopier createCopier(Class<?> clazz) {
            if (beanCopierCache.containsKey(clazz))
                return beanCopierCache.get(clazz);
            beanCopierCache.putIfAbsent(clazz, BeanCopier.create(clazz, clazz, true));
            return beanCopierCache.get(clazz);
        }

        private static BeanCopier createCopier(Class<?> source, Class<?> target) {
            return BeanCopier.create(source, target, true);
        }
    }



    private static class BeanConverter implements Converter {
        @Override
        public Object convert(Object bean, Class fieldType, Object fieldName) {
            return _clone(bean);
        }

        private static Object _clone(Object bean) {
            if (bean == null) {
                return null;
            } else {
                if (bean.getClass().isArray() && !bean.getClass().getComponentType().equals(byte.class)) {
                    int length = Array.getLength(bean);
                    Object clone = Array.newInstance(bean.getClass().getComponentType(), length);
                    for (int i = 0; i < length; i++) {
                        Array.set(clone, i, _clone(Array.get(bean, i)));
                    }
                    return clone;
                } else {
                    return bean;
                }
            }
        }
    }
}

