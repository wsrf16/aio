package com.aio.portable.swiss.sugar;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.util.List;

public abstract class UnsafeSugar {

    private volatile static Unsafe unsafe = getUnsafe();

    public static final Unsafe getUnsafe() {
        try {
            // Although the class and all methods are public, use of this class is limited because only trusted code can obtain instances of it.
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(Unsafe.class);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ObjectAddressHolder {
        public Object object;
    }

    public static final Long getDeclaredFieldOffset(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            long offset = unsafe.objectFieldOffset(field);
            return unsafe.getLong(object, offset);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static final Long getObjectOffset(Object object) {
        ObjectAddressHolder objectAddressHolder = new ObjectAddressHolder();
        objectAddressHolder.object = object;
        try {
            long objectFieldOffset = unsafe.objectFieldOffset(ObjectAddressHolder.class.getDeclaredField("object"));
            long objectAddress = unsafe.getLong(objectAddressHolder, objectFieldOffset);
            objectAddressHolder.object = null;
            objectAddressHolder = null;
            return objectAddress;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final Long getMarkWordAddress(Object o) {
        try {
            return unsafe.getLong(o, 0L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final Long getClassMetaDataAddress(Object o) {
        try {
            return unsafe.getLong(o, 8L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final void releaseClassLoader(URLClassLoader urlClassLoader) {
        sun.misc.ClassLoaderUtil.releaseLoader(urlClassLoader, (List)null);
        try {
            urlClassLoader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
