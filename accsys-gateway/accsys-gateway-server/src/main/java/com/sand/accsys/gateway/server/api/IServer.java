package com.sand.accsys.gateway.server.api;

/**
 * @author : huanghy
 * @create : 2016/3/15 0015 上午 11:06
 * @since : ${VERSION}
 */
public interface IServer {
    /**
     * 启动server
     */
    void start();

    /**
     * 停止server
     */
    void stop();

    /**
     * 是否启动
     * @return
     */
    boolean isStarted();

    /**
     * server 的实现类型
     * @return
     */
    ServerType serverType();


    int endPoint();
}
