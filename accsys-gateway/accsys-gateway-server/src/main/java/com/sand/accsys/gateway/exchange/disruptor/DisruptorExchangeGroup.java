package com.sand.accsys.gateway.exchange.disruptor;

import com.google.common.base.Splitter;
import com.sand.accsys.common.config.DynamicPropertyHelper;
import com.sand.accsys.gateway.exchange.disruptor.event.GatewayDataEvent;
import com.sand.accsys.gateway.exchange.disruptor.event.GatewayEventFactory;
import com.sand.accsys.gateway.server.netty.NettyInnerEvent;
import com.sand.accsys.infrastructure.exchange.*;

import java.util.Iterator;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 上午 10:00
 * @since : ${VERSION}
 */
public class DisruptorExchangeGroup extends ExchangeGroup<NettyInnerEvent,GatewayDataEvent>{
    private static final DisruptorExchangeGroup instance = new DisruptorExchangeGroup();
    private static final String SUPPORT_PRODUCTS  = "accsys.gateway.support.products";
    private static final String CONFIG_PREFIX = "accsys.gateway.dispatcher.disruptor.product.";
    private DisruptorExchangeGroup(){
        super();
    }

    @Override
    protected void init() {
        String topics = DynamicPropertyHelper.getStringProperty(SUPPORT_PRODUCTS, "").get();
        Iterator<String> it = Splitter.on(',').trimResults().omitEmptyStrings().split(topics).iterator();
        while (it.hasNext()){
            String topic = it.next();
            int ringBufferSize  = DynamicPropertyHelper.getIntProperty(CONFIG_PREFIX+topic+".ringBufferSize",1024).get();
            String producerType = DynamicPropertyHelper.getStringProperty(CONFIG_PREFIX+topic+".producerType","MULTI").get();
            String waitStrategy = DynamicPropertyHelper.getStringProperty(CONFIG_PREFIX+topic+".waitStrategy","BLOCKING").get();
            String consumerType = DynamicPropertyHelper.getStringProperty(CONFIG_PREFIX+topic+".consumerType","WORKE_POOL").get();
            int    consumerSum  = DynamicPropertyHelper.getIntProperty(CONFIG_PREFIX+topic+".consumerSum",3).get();
            String handClass    = DynamicPropertyHelper.getStringProperty(CONFIG_PREFIX+topic+".handler","").get();
            int   parallelism   = DynamicPropertyHelper.getIntProperty(CONFIG_PREFIX+topic+".executor.handler.processors",30).get();
            DisruptorExchangeProperties properties = new DisruptorExchangeProperties(
                    parallelism,
                    producerType,
                    ringBufferSize,
                    waitStrategy,
                    consumerType,
                    consumerSum,
                    handClass);
            Exchange<NettyInnerEvent,GatewayDataEvent> exchange = new DisruptorExchange(GatewayEventFactory.getFactory(),properties);
            TopicKey topicKey = new TopicKey(topic, ChannelType.PRODUCT);
            put(topicKey, exchange);
        }
    }

    public static DisruptorExchangeGroup getInstance(){
        return instance;
    }
}
