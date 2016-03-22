package com.sand.accsys.gateway.exchange.disruptor.handler;

import com.lmax.disruptor.WorkHandler;
import com.sand.accsys.infrastructure.exchange.event.DataEvent;
import com.sand.accsys.infrastructure.exchange.event.InnerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 下午 1:26
 * @since : ${VERSION}
 */
public abstract class AbstractWorkHandler<T extends InnerEvent> implements WorkHandler<DataEvent>{
    private static final Logger logger = LoggerFactory.getLogger(AbstractWorkHandler.class);

    public void onEvent(DataEvent event) throws Exception {
        try {
            T innerEvent = (T) event.getEvent();
            doHandle(innerEvent);
        }catch (Exception e){
            logger.error("onEvent event {},process failed cause by:{}",event,e);
            throw e;
        }

    }
    public abstract void doHandle(T innerEvent);
}
