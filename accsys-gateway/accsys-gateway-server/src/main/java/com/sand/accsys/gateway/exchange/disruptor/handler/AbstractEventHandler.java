package com.sand.accsys.gateway.exchange.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.sand.accsys.infrastructure.exchange.event.DataEvent;
import com.sand.accsys.infrastructure.exchange.event.InnerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 下午 1:16
 * @since : ${VERSION}
 */
public abstract class AbstractEventHandler<T extends InnerEvent> implements EventHandler<DataEvent> {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractEventHandler.class);

    public void onEvent(DataEvent event, long sequence, boolean endOfBatch) throws Exception {
        try {
            T innerEvent = (T)event.getEvent();
            doHandle(innerEvent);
        }catch (Exception e){
            logger.error("onEvent event {},sequence {} ,endOfBatch {} , process failed cause by:{}",event,sequence,endOfBatch,e);
            throw e;
        }
    }

    public abstract void doHandle(T innerEvent);
}
