package com.xiaoruiit.project.demo.learn.header.callerinfo;

import lombok.Data;

/**
 * @author hanxiaorui
 * @date 2022/4/9
 */
@Data
public class CallerInfo {

    /**
     * 线程安全
     */
    private final static ThreadLocal<CallerInfo> REQUEST_HEADER_THREAD_LOCAL_CALLERINFO = new ThreadLocal<>();

    private String userCode;

    private String userName;

    public static void setCallerInfo(CallerInfo callerInfo){
        REQUEST_HEADER_THREAD_LOCAL_CALLERINFO.set(callerInfo);
    }

    public static CallerInfo getCallerInfo(){
        return REQUEST_HEADER_THREAD_LOCAL_CALLERINFO.get();
    }

    public static void removeCallerInfo(){
        REQUEST_HEADER_THREAD_LOCAL_CALLERINFO.remove();
    }
}
