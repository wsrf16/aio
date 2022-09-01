package com.aio.portable.swiss.design.clone;

import com.aio.portable.swiss.suite.bean.BeanSugar;

public interface DeepCloneable {
    default <T> T deepClone() {
        return (T) BeanSugar.Cloneable.deepCloneByCglib(this);
    }
}
