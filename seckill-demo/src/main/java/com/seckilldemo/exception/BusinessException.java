package com.seckilldemo.exception;

import com.seckilldemo.dto.ResultCode;

/**
 * @Author tortoise
 * @Date 2025/7/11 14:00
 * @PackageName:com.seckilldemo.exception
 * @ClassName: BusinessException
 * @Description: 自定义业务异常
 * @Version 1.0
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ResultCode resultCode;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        this.resultCode = ResultCode.FAILED;
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.resultCode = ResultCode.FAILED;
    }

    public BusinessException(ResultCode resultCode, Throwable cause) {
        super(resultCode.getMessage(), cause);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
} 