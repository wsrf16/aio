package com.aio.portable.park.common;

import com.aio.portable.swiss.hamlet.bean.BizStatus;
import com.aio.portable.swiss.hamlet.bean.BizStatusNativeEnum;
import org.springframework.stereotype.Component;

@Component
public class BizStatusEnum extends BizStatusNativeEnum {
    private final static BizStatusEnum _ = new BizStatusEnum();

//    protected static BizStatusEnum instance;
//
//    public static BizStatusNativeEnum singletonInstance() {
//        return instance;
//    }

    @Override
    public BizStatus succeed() {
        return new BizStatus(0, "请求成功");
    }

    @Override
    public BizStatus failed() {
        return new BizStatus(1, "请求失败");
    }

    @Override
    public BizStatus exception() {
        return new BizStatus(2, "请求异常");
    }

    @Override
    public BizStatus invalid() {
        return new BizStatus(3, "请求无效");
    }

    @Override
    public BizStatus unauthorized() {
        return new BizStatus(4, "无效授权");
    }
}
