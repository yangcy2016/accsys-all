package com.sand.accsys.infrastructure.exchange.event;

/**
 *
 * InnerEvent 的实例由服务端读取请求数据是产生 是包含了待交换数据的容器，
 *eg.服务端接入的请求的上下文数据
 * 请求的报文数据，
 * 由于disruptor的处理机制，在通过ringBuffer发布数据的时候，InnerEvent
 * DataEvent中，由consumer的方法读取到InnerEvent数据并进行后续的处理
 * @author : huanghy
 * @create : 2016/3/16 0016 下午 2:52
 * @since : ${VERSION}
 */
public interface InnerEvent {
    /**
     * id初始化后不可更改
     * @return
     */
    long id();

    /**
     * InnerEvent's name
     * @return
     */
    String name();
}
