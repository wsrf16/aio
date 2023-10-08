package com.aio.portable.park.common;

import com.aio.portable.swiss.hamlet.bean.BaseBizStatusEnum;
import com.aio.portable.swiss.hamlet.bean.BizStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BizStatusEnum extends BaseBizStatusEnum {
    public final static BizStatusEnum bizStatusEnum = new BizStatusEnum();

    public static List<BizStatus> values() {
        return BaseBizStatusEnum.values();
    }

    @Override
    public BizStatus succeed() {
        return super.succeed();
    }

    @Override
    public BizStatus failed() {
        return super.failed();
    }

    @Override
    public BizStatus exception() {
        return super.exception();
    }

    @Override
    public BizStatus invalid() {
        return super.invalid();
    }

    @Override
    public BizStatus unauthorized() {
        return super.unauthorized();
    }
}
