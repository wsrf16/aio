package com.aio.portable.park.common;

import com.aio.portable.swiss.hamlet.bean.BaseBizStatusEnum;
import com.aio.portable.swiss.hamlet.bean.BizStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BizStatusEnum extends BaseBizStatusEnum {
    public static List<BizStatus> values() {
        return BaseBizStatusEnum.values();
    }

    public BizStatus succeed() {
        return new BizStatus(0, "请求成功");
    }

    public BizStatus failed() {
        return new BizStatus(1, "请求失败");
    }

    public BizStatus exception() {
        return new BizStatus(2, "请求异常");
    }

    public BizStatus invalid() {
        return new BizStatus(3, "请求无效");
    }

    public BizStatus unauthorized() {
        return new BizStatus(4, "无效授权");
    }
}
