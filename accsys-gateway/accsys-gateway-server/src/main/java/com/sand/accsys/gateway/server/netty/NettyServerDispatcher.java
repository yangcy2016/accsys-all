package com.sand.accsys.gateway.server.netty;

import com.google.common.base.Preconditions;
import com.sand.accsys.client.product.event.ProductEvent;
import com.sand.accsys.client.product.event.ProductEventFactory;
import com.sand.accsys.gateway.dispatcher.Dispatcher;
import com.sand.accsys.gateway.exchange.disruptor.DisruptorExchangeGroup;
import com.sand.accsys.infrastructure.exchange.ChannelType;
import com.sand.accsys.infrastructure.exchange.Exchange;
import com.sand.accsys.infrastructure.exchange.TopicKey;
import com.sand.accsys.infrastructure.exchange.event.InnerEvent;
import com.sand.accsys.protocol.protocolbuf.IProtocol;
import com.sand.accsys.protocol.protocolbuf.ProtocolbufPackUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : huanghy
 * @create : 2016/3/15 0015 下午 4:41
 * @since : ${VERSION}
 */
public class NettyServerDispatcher implements Dispatcher{

    private static final Logger logger = LoggerFactory.getLogger(NettyServerDispatcher.class);

    public void asyncDispatch(InnerEvent event) {
        try {
            Preconditions.checkNotNull(event,"innerEvent must not be null");
            if(event instanceof NettyInnerEvent){
                NettyInnerEvent innerEvent = (NettyInnerEvent)event;
                IProtocol.Request request = innerEvent.getRequest();
                String topic = ProtocolbufPackUtil.getDispatch(request);
                TopicKey key = new TopicKey(topic, ChannelType.PRODUCT);
                //根据一定的规则查找初始化好的disruptor
                Exchange exchange = DisruptorExchangeGroup.getInstance().get(key);
                if(exchange!=null){
                    ProductEvent productEvent = ProductEventFactory.getInstance().creatEvent(request);
                    innerEvent.setProductEvent(productEvent);//set a product  event
                    //异步的将请求数据写入ringBuffer
                    exchange.publish(innerEvent);
                }else{
                    logger.error("topic channel {} not fount please check dispatch in request or server config",topic);
                }
            }else {
                logger.warn("unknown innerEvent type {},dispatcher will discarded the event",event.getClass().getName());
            }
        }catch (IllegalArgumentException e1){

        }catch (Exception e){

        }

    }
}
