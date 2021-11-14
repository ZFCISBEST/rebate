package com.help.rebate.web.response;

import lombok.Data;

/**
 * 响应包装类
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Data
public class SafeServiceResponse<T> {
    /**
     * 服务的起止时间，耗时
     */
    private long startTime;
    private long endTime;
    private long timeCost;

    /**
     * 响应码和响应消息
     */
    private String code;
    private String msg;
    private String ip;

    /**
     * 实际的业务数据
     */
    private T data;

    private static ThreadLocal<SafeServiceResponse> safeServiceResponseThreadLocal  = new ThreadLocal<SafeServiceResponse>(){
        @Override
        protected SafeServiceResponse initialValue() {
            return null;
        }
    };

    /**
     * 私有化构造函数
     */
    private SafeServiceResponse(){

    }

    /**
     * 业务开始
     */
    public static void startBiz() {
        SafeServiceResponse oldSafeServiceResponse = safeServiceResponseThreadLocal.get();
        if (oldSafeServiceResponse == null) {
            SafeServiceResponse safeServiceResponse = newResponse();
            safeServiceResponseThreadLocal.set(safeServiceResponse);
        }
        else{
            oldSafeServiceResponse.setStartTime(System.currentTimeMillis());
        }
    }

    /**
     * 包装为成功的响应
     */
    public static <T> SafeServiceResponse success(T data) {
        SafeServiceResponse safeServiceResponse = safeServiceResponseThreadLocal.get();
        if (safeServiceResponse == null) {
            safeServiceResponse = newResponse();
        }

        safeServiceResponse.setData(data);
        safeServiceResponse.finishResponse();
        return safeServiceResponse;
    }

    /**
     * 包装为客户端失败的响应
     */
    public static SafeServiceResponse client(String msg) {
        SafeServiceResponse safeServiceResponse = safeServiceResponseThreadLocal.get();
        if (safeServiceResponse == null) {
            safeServiceResponse = newResponse();
        }

        safeServiceResponse.setData(null);
        safeServiceResponse.clientErrorResponse(msg);
        return safeServiceResponse;
    }

    /**
     * 包装为成功的响应
     */
    public static SafeServiceResponse fail(String msg) {
        SafeServiceResponse safeServiceResponse = safeServiceResponseThreadLocal.get();
        if (safeServiceResponse == null) {
            safeServiceResponse = newResponse();
        }

        safeServiceResponse.setData(null);
        safeServiceResponse.serverErrorResponse(msg);
        return safeServiceResponse;
    }

    /**
     * 创建一个响应体
     * @return
     */
    public static <T> SafeServiceResponse newResponse() {
        SafeServiceResponse<T> serviceResponse = new SafeServiceResponse<>();

        //设置起始时间
        serviceResponse.setStartTime(System.currentTimeMillis());
        return serviceResponse;
    }

    /**
     * 完成响应体
     */
    public void finishResponse() {
        this.setEndTime(System.currentTimeMillis());

        //code
        this.setCode("200");
        this.setMsg("success");
        this.setIp(IpAddressUtil.getHostAddress());

        //time cost
        this.setTimeCost(getEndTime() - getStartTime());
    }

    /**
     * 完成响应体
     */
    public void clientErrorResponse(String msg) {
        this.setEndTime(System.currentTimeMillis());

        //code
        this.setCode("404");
        this.setMsg(msg);
        this.setIp(IpAddressUtil.getHostAddress());

        //time cost
        this.setTimeCost(getEndTime() - getStartTime());
    }

    /**
     * 完成响应体
     */
    public void serverErrorResponse(String msg) {
        this.setEndTime(System.currentTimeMillis());

        //code
        this.setCode("500");
        this.setMsg(msg);
        this.setIp(IpAddressUtil.getHostAddress());

        //time cost
        this.setTimeCost(getEndTime() - getStartTime());
    }
}
