package com.sand.accsys.gateway.server;

import com.sand.accsys.gateway.server.api.IServer;

/**
 * @author : huanghy
 * @create : 2016/3/15 0015 上午 11:13
 * @since : ${VERSION}
 */
public abstract class AbstractServer implements IServer{
    private volatile boolean isStarted;
    protected int endPoint = 12345;
    public void start() {
        if(isStarted){
            throw new IllegalStateException("the server is already started");
        }
        startInner();
    }
    protected abstract void startInner();
    public void stop() {
        if(!isStarted){
            return;
        }
        stopInner();
        synchronized (this){
            isStarted = false;
        }
        stopInner();
    }

    protected abstract void stopInner();

    public int endPoint() {
        return this.endPoint;
    }

    public boolean isStarted() {
        return isStarted;
    }
}
