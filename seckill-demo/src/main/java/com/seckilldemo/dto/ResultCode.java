package com.seckilldemo.dto;

/**
 * @Author tortoise
 * @Date 2025/7/11 14:00
 * @PackageName:com.seckilldemo.dto
 * @ClassName: ResultCode
 * @Description: 统一结果状态码枚举
 * @Version 1.0
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 失败
     */
    FAILED(500, "操作失败"),

    /**
     * 参数验证失败
     */
    VALIDATE_FAILED(400, "参数验证失败"),

    /**
     * 未认证（未登录）
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),

    /**
     * 未授权（无权限）
     */
    FORBIDDEN(403, "没有相关权限"),

    /**
     * 用户不存在
     */
    USER_NOT_FOUND(1001, "用户不存在"),

    /**
     * 密码错误
     */
    PASSWORD_ERROR(1002, "密码错误"),

    /**
     * 用户已存在
     */
    USER_ALREADY_EXISTS(1003, "用户已存在"),

    /**
     * 手机号格式错误
     */
    MOBILE_FORMAT_ERROR(1004, "手机号格式错误"),

    /**
     * 验证码错误
     */
    VERIFY_CODE_ERROR(1005, "验证码错误"),

    /**
     * 商品不存在
     */
    GOODS_NOT_FOUND(2001, "商品不存在"),

    /**
     * 库存不足
     */
    STOCK_NOT_ENOUGH(2002, "库存不足"),

    /**
     * 秒杀未开始
     */
    SECKILL_NOT_START(2003, "秒杀未开始"),

    /**
     * 秒杀已结束
     */
    SECKILL_END(2004, "秒杀已结束"),

    /**
     * 重复秒杀
     */
    REPEAT_SECKILL(2005, "不能重复秒杀"),

    /**
     * 订单不存在
     */
    ORDER_NOT_FOUND(3001, "订单不存在"),

    /**
     * 订单状态错误
     */
    ORDER_STATUS_ERROR(3002, "订单状态错误"),

    /**
     * 系统繁忙
     */
    SYSTEM_BUSY(9001, "系统繁忙，请稍后重试"),

    /**
     * 请求过于频繁
     */
    REQUEST_TOO_FREQUENT(9002, "请求过于频繁，请稍后重试");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
} 