package com.sand.accsys.gateway.exchange.disruptor.event;

import com.sand.accsys.infrastructure.exchange.event.DataEvent;
import com.sand.accsys.infrastructure.exchange.event.InnerEvent;

/**
 * @author : huanghy
 * @create : 2016/3/16 0016 下午 2:14
 * @since : ${VERSION}
 */
public class GatewayDataEvent extends DataEvent{

    public GatewayDataEvent(){
    }

    public GatewayDataEvent(InnerEvent volume) {
        super(volume);
    }

}
