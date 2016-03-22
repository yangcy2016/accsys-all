package com.sand.accsys.infrastructure.exchange;

import com.sand.accsys.infrastructure.exchange.event.DataEvent;
import com.sand.accsys.infrastructure.exchange.event.InnerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 上午 9:28
 * @since : ${VERSION}
 */
public abstract class ExchangeGroup<I extends InnerEvent,E extends DataEvent> {


    private final Map<TopicKey,Exchange<I,E>> exchangeMap = new HashMap<TopicKey, Exchange<I,E>>(10);

    protected static final Logger logger = LoggerFactory.getLogger(ExchangeGroup.class);
    public ExchangeGroup(){
        init();
    }
    protected abstract void init();

    public boolean startAll(){
        Iterator<TopicKey> it = exchangeMap.keySet().iterator();
        int i = 0;
        while (it.hasNext()){
            TopicKey key = it.next();
            Exchange<I,E> exchange = exchangeMap.get(key);
            if(!exchange.isActive()){
                if(exchange.start()){
                    i++;
                    logger.info("start exchange {},{} success",key.getTopicName(),key.getTopicName());
                }
            }else {
                i++;
                logger.warn("exchange is already started,need not start again");
            }
        }
        return i==exchangeMap.size();
    }

    public  void stopAll(){
        Iterator<TopicKey> it = exchangeMap.keySet().iterator();
        while (it.hasNext()){
            TopicKey key = it.next();
            Exchange<I,E> exchange = exchangeMap.get(key);
            if(exchange.isActive()){
               exchange.stop();
            }
            logger.info("stop exchange {},{} success", key.getTopicName(), key.getTopicName());
        }
    }

    public Exchange<I,E> get(TopicKey key){
        return exchangeMap.get(key);
    }

    public void put(TopicKey key,Exchange<I,E> exchange){
        exchangeMap.put(key,exchange);
    }

    public Exchange<I,E> remove(TopicKey key){
        return exchangeMap.remove(key);
    }

    public boolean start(TopicKey key){
        Exchange<I,E> exchange = this.get(key);
        boolean started = false;
        if(exchange!=null){
            if(!exchange.isActive()){
                started = exchange.start();
                logger.info("start exchange {},{} success",key.getTopicName(),key.getTopicName());
            }else {
                logger.warn("exchange is already started,need not start again");
                started = true;
            }
        }
        return started;
    }

    public void stop(TopicKey key){
        Exchange<I,E> exchange = this.get(key);
        if(exchange.isActive()){
            exchange.stop();
        }
        logger.info("stop exchange {},{} success",key.getTopicName(),key.getTopicName());
    }

    public int size(){
        return exchangeMap.size();
    }

}
