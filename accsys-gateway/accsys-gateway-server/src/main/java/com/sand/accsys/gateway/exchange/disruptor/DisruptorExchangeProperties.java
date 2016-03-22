package com.sand.accsys.gateway.exchange.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import com.sand.accsys.common.util.ReflectUtil;
import com.sand.accsys.infrastructure.exchange.ExchangeProperties;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 上午 10:41
 * @since : ${VERSION}
 */
public class DisruptorExchangeProperties implements ExchangeProperties{
    private int processors;
    private String producerType;
    private int ringBufferSize;
    private String wait;
    private String consumerType;
    private int consumerSum;
    private String handlerClass;

    private WorkHandler<?>[] workHandlers;
    private EventHandler<?> eventHandler;

    public DisruptorExchangeProperties(int processors,
                              String producerType, int ringBufferSize,
                              String wait, String consumerType,
                              int consumerSum, String handlerClass) {
        this.processors = processors;
        this.producerType = producerType;
        this.ringBufferSize = ringBufferSize;
        this.wait = wait;
        this.consumerType = consumerType;
        this.consumerSum = consumerSum;
        this.handlerClass = handlerClass;
    }

    public WaitStrategy waitStrategy(){
        return Wait.getWait(wait).getWaitStrategy();
    }

    public ProducerType producerType(){
        return Producer.valueOf(producerType).getProducerType();
    }

    public int ringBufferSize(){
        return this.ringBufferSize;
    }

    public ExecutorService executors(){
        return Executors.newFixedThreadPool(processors);
    }

    public WorkHandler[] toWorkHandlers(){

        if(consumerType()==ConsumerType.WORKE){
            new IllegalStateException("consumer is in work model can't to workHandlers");
        }
        workHandlers = new WorkHandler[consumerSum];
        for(int i = 0 ;i<consumerSum;i++){
            workHandlers[i] = (WorkHandler) ReflectUtil.callConstructor(ReflectUtil.loadClass(handlerClass));
        }
        return workHandlers;

    }

    public EventHandler toEventHandler(){
        if(consumerType()==ConsumerType.WORKE_POOL){
            new IllegalStateException("consumer is in work pool model can't to eventHandlers");
        }
        eventHandler = (EventHandler) ReflectUtil.callConstructor(ReflectUtil.loadClass(handlerClass));
        return eventHandler;
    }

    public ConsumerType consumerType(){
        return ConsumerType.valueOf(consumerType);
    }


    public static enum  Wait{
        /***
         * highest performing : BUSYSPIN > YIELDING > SLEEPING > BLOCKING
         *
         * low latency : BUSYSPIN > YIELDING > SLEEPING > BLOCKING
         *
         * event hand  smaller and physical cores : BUSYSPIN,YIELDING
         *
         * use case is for asynchronous logging: SLEEPING
         */
        BLOCKING,SLEEPING,YIELDING,BUSYSPIN;

        public WaitStrategy getWaitStrategy() {
            switch (this){
                case   BLOCKING: return new BlockingWaitStrategy();
                case   SLEEPING: return new SleepingWaitStrategy();
                case   YIELDING: return new YieldingWaitStrategy();
                case   BUSYSPIN: return new BusySpinWaitStrategy();
                default:         return new BlockingWaitStrategy();
            }
        }

        public static Wait getWait(String type){
            if("BLOCKING".equalsIgnoreCase(type)){
                return BLOCKING;
            }else if("SLEEPING".equalsIgnoreCase(type)){
                return SLEEPING;
            }else if("YIELDING".equalsIgnoreCase(type)){
                return YIELDING;
            }else if("BUSYSPIN".equalsIgnoreCase(type)){
                return BUSYSPIN;
            }else{
                return BLOCKING;
            }
        }
    }


    public static enum  Producer{
        /** single producer */
        SINGLE,

        /** multiple producer */
        MULTI;

        public ProducerType getProducerType() {
            switch (this){
                case   SINGLE: return ProducerType.SINGLE;
                case   MULTI:  return ProducerType.MULTI;
                default:       return ProducerType.SINGLE;
            }
        }

        public static Producer getProducer(String type){
            if(type.equalsIgnoreCase("SINGLE")){
                return SINGLE;
            }else if(type.equalsIgnoreCase("MULTI")){
                return MULTI;
            }else{
                return SINGLE;
            }
        }

    }

    public static enum  ConsumerType{
        WORKE(0,"single work"),WORKE_POOL(1,"work pool");
        private int value;
        private String desc;
        ConsumerType(int i, String s) {
            this.value = i;
            this.desc  = s;
        }

        public ConsumerType valueOf(int value){
            switch (value){
                case 0:
                    return WORKE;
                case 1:
                    return WORKE_POOL;
                default:
                    return null;
            }
        }
    }
}
