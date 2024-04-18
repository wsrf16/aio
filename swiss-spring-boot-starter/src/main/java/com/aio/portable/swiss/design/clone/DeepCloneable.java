package com.aio.portable.swiss.design.clone;

import com.aio.portable.swiss.suite.bean.DeepCloneSugar;

public interface DeepCloneable {
    default <T> T deepClone() {
        return (T) DeepCloneSugar.Cglib.clone(this);
    }
}
