package com.aio.portable.swiss.hamlet.bean;

import java.util.List;

public class ClassicResponseStatuses extends ResponseStatuses {
    public static List<ResponseStatus> values() {
        return ResponseStatuses.values();
    }

    public ClassicResponseStatuses() {
    }

    public ResponseStatus succeed() {
        return new ResponseStatus(0, "请求成功");
    }

    public ResponseStatus failed() {
        return new ResponseStatus(1, "请求失败");
    }

    public ResponseStatus exception() {
        return new ResponseStatus(2, "请求异常");
    }

    public ResponseStatus invalid() {
        return new ResponseStatus(3, "请求无效");
    }

    public ResponseStatus unauthorized() {
        return new ResponseStatus(4, "无效授权");
    }


}
