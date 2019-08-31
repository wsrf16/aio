package com.aio.portable.swiss.hamlet.model;

public enum BizStatusEnum {
    SUCCEED(0, "请求成功"),
    FAILED(1, "请求失败"),
    EXCEPTION(2, "请求异常"),
    VERIFICATION(3, "未授权"),
    VALID(4, "请求格式不正确"),
    PARAM_MISSING(103, "缺少参数_appid"),
    PARAM_INVALID(102, "请求参数格式错误"),
    APPID_NOT_EXIST(104, "该_appid不存在"),
    APPID_NOT_IN_USE(105, "该_appid已停用"),
    SIGNATURE_MISSING(106, "缺少签名"),
    SIGNATURE_ERROR(107, "签名错误"),
    TIMESTAMP_MISSING(108, "缺少参数_timestamp"),
    REQUEST_EXPIRED(109, "请求已过期"),
    REQUEST_REPEAT(110, "请求重发"),
    REQUEST_NOT_GET(111, "HTTP请求非GET方式"),
    REQUEST_NOT_POST(112, "HTTP请求非POST方式"),
    API_VERSION_NOT_EXIST(113, "接口版本不存在"),
    API_UNAUTHORIZED(114, "没有接口访问权限【根据业务分配的RETURNCODE前三位比如:社区=202】"),
    API_OUT_OF_SERVICE(115, "接口已停用"),
    API_OFFLINE(116, "接口不处于上线状态"),
    LIMIT_RATE(117, "已超限频阀值"),
    LIMIT_FLOW(118, "已超限流阀值"),
    LIMIT_IP(119, "IP限制不能访问资源"),
    NOT_FOUND(404, "页面找不到"),
    INTERNAL_SERVER_ERROR(500, "服务器错误"),
    ;


    BizStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private int code;
    private String description;
}
