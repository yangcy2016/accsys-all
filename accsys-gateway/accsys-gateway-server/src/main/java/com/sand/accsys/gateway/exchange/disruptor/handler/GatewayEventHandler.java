package com.sand.accsys.gateway.exchange.disruptor.handler;

import com.sand.accsys.gateway.server.netty.NettyInnerEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 上午 10:00
 * @since : ${VERSION}
 */
public class GatewayEventHandler extends AbstractEventHandler<NettyInnerEvent> {

    private int processes = 30;
    private ExecutorService executor;

    public GatewayEventHandler() {

    }
    public GatewayEventHandler(int processes) {
        this.processes = processes;
    }

    private void initExecutors(){
        if(executor==null){
            executor = Executors.newFixedThreadPool(processes);
        }
    }

    @Override
    public void doHandle(NettyInnerEvent innerEvent) {
        initExecutors();
        executor.execute(new Task());
    }

    private class Task implements Runnable{

        public void run() {

        }
    }
}
