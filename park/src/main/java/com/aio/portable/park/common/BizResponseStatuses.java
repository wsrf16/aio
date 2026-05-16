package com.aio.portable.park.common;

import com.aio.portable.swiss.hamlet.bean.ResponseStatus;
import com.aio.portable.swiss.hamlet.bean.ResponseStatuses;

public class BizResponseStatuses extends ResponseStatuses {
    public ResponseStatus succeed() {
        return new ResponseStatus(10, "请求成功");
    }

    public ResponseStatus failed() {
        return new ResponseStatus(11, "请求失败");
    }

    public ResponseStatus exception() {
        return new ResponseStatus(12, "请求异常");
    }

    public ResponseStatus invalid() {
        return new ResponseStatus(13, "请求无效");
    }

    public ResponseStatus unauthorized() {
        return new ResponseStatus(14, "无效授权");
    }
}
