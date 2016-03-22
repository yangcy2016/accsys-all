package com.sand.accsys.gateway.exchange.disruptor.event;

import com.lmax.disruptor.EventFactory;

/**
 * @author : huanghy
 * @create : 2016/3/15 0015 下午 3:13
 * @since : ${VERSION}
 */
public class GatewayEventFactory implements EventFactory<GatewayDataEvent>{

    private static final GatewayEventFactory factory = new GatewayEventFactory();

    public static GatewayEventFactory getFactory(){
        return factory;
    }

    public GatewayDataEvent newInstance() {
        return new GatewayDataEvent();
    }
}
