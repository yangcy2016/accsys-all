package com.sand.accsys.gateway.exchange.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.sand.accsys.infrastructure.exchange.Exchange;
import com.sand.accsys.infrastructure.exchange.event.DataEvent;
import com.sand.accsys.infrastructure.exchange.event.InnerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 上午 9:59
 * @since : ${VERSION}
 */
public class DisruptorExchange<I extends InnerEvent,E extends DataEvent> implements Exchange<I,E>{
    private EventFactory<E> eventFactory;
    private Disruptor<E> disruptor;
    private RingBuffer<E> ringBuffer;
    private volatile boolean isStarted;
    private EventHandlerGroup<E> group;

    private EventHandler<E>[] eventHandler;
    private WorkHandler<E>[] workHandlers;

    private DisruptorExchangeProperties properties;

    private static final Logger logger = LoggerFactory.getLogger(DisruptorExchange.class);

    public DisruptorExchange(EventFactory eventFactory,DisruptorExchangeProperties properties){
        this.eventFactory =eventFactory;
        this.properties = properties;
        this.init();
    }

    public void init(){

        disruptor = new Disruptor<E>(
                eventFactory,
                properties.ringBufferSize(),
                properties.executors(),
                properties.producerType(),
                properties.waitStrategy()
        );
        if(properties.consumerType()== DisruptorExchangeProperties.ConsumerType.WORKE){
            disruptor.handleEventsWith(properties.toEventHandler());
        }else {
            disruptor.handleEventsWithWorkerPool(properties.toWorkHandlers());
        }
        logger.info("init disruptor success");
    }

    public boolean start(){
        if(isStarted){
            return true;
        }
        if(disruptor==null){
            new IllegalStateException("disruptor need call init() before");
        }
        ringBuffer = disruptor.start();
        synchronized (this){
            isStarted = true;
        }
        return isStarted;
    }


    public void stop(){

        if(disruptor!=null&&isStarted){
            disruptor.shutdown();
        }
        ExecutorService executor = properties.executors();
        if(executor!=null){
            executor.shutdown();
        }
        synchronized (this){
            isStarted = false;
        }
        logger.info("disruptor shutdown");

    }


    public boolean isActive(){
        return this.isStarted;
    }

    public boolean publish(I innerEvent) {
        long sequence = ringBuffer.next();
        try {
            DataEvent event = ringBuffer.get(sequence);
            //filling data to dataEvent
            event.setEvent(innerEvent);
        }finally {
            ringBuffer.publish(sequence);
        }
        return true;
    }
}
