package com.sand.accsys.gateway.dispatcher;

import com.sand.accsys.infrastructure.exchange.event.InnerEvent;

/**
 * @author : huanghy
 * @create : 2016/3/15 0015 下午 2:25
 * @since : ${VERSION}
 */
public interface Dispatcher {

    void asyncDispatch(InnerEvent event);
}
