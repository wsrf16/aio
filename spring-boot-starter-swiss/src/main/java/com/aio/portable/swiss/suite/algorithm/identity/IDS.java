package com.aio.portable.swiss.suite.algorithm.identity;

import com.aio.portable.swiss.global.Constant;

import java.util.UUID;

public abstract class IDS {
    public static final String uuid() {
        String uuid = UUID.randomUUID().toString().replace("-", Constant.EMPTY);
        return uuid;
    }
}
