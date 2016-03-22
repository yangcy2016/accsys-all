package com.sand.accsys.infrastructure.exchange;

import com.sand.accsys.infrastructure.exchange.event.DataEvent;
import com.sand.accsys.infrastructure.exchange.event.InnerEvent;

/**
 *服务端数据交换去区，负责发布请求事件进交换区，开启多个消费者消费交换区里的内容
 * 根据topicChannel将其发送到产品通道
 * @author : huanghy
 * @create : 2016/3/17 0017 上午 9:03
 * @since : ${VERSION}
 */
public interface Exchange<I extends InnerEvent,E extends DataEvent> {

    boolean isActive();

    boolean publish(I event);


    boolean start();


    void stop();

}
