package com.help.rebate.service.exception;

/**
 * 转链失败
 *
 * @author zfcisbest
 * @date 22/1/8
 */
public class ConvertException extends RuntimeException {
    public ConvertException() {
        super();
    }

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(String message, Throwable cause) {
        super(message, cause);
    }
}
