package com.aio.portable.swiss.structure.database.identity;

import com.aio.portable.swiss.global.Constant;

import java.util.UUID;

public abstract class ID {
    public final static String uuid() {
        String uuid = UUID.randomUUID().toString().replace("-", Constant.EMPTY);
        return uuid;
    }
}
