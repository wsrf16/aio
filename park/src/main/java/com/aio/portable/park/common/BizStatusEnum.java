package com.aio.portable.park.common;

import com.aio.portable.swiss.hamlet.bean.BaseBizStatusEnum;
import com.aio.portable.swiss.hamlet.bean.BizStatus;
import org.springframework.stereotype.Component;

@Component
public class BizStatusEnum extends BaseBizStatusEnum {
    private final static BizStatusEnum _ = new BizStatusEnum();

//    protected static BizStatusEnum instance;
//
//    public static BizStatusNativeEnum singletonInstance() {
//        return instance;
//    }

    @Override
    public BizStatus succeed() {
        return new BizStatus(90, "请求成功");
    }

    @Override
    public BizStatus failed() {
        return new BizStatus(10, "请求失败");
    }

    @Override
    public BizStatus exception() {
        return new BizStatus(20, "请求异常");
    }

    @Override
    public BizStatus invalid() {
        return new BizStatus(30, "请求无效");
    }

    @Override
    public BizStatus unauthorized() {
        return new BizStatus(40, "无效授权");
    }
}
