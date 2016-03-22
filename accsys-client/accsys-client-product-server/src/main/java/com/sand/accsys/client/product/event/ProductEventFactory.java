package com.sand.accsys.client.product.event;

import com.sand.accsys.protocol.protocolbuf.IProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : huanghy
 * @create : 2016/3/22 0022 下午 3:33
 * @since : ${VERSION}
 */
public class ProductEventFactory {
    private static final ProductEventFactory instance = new ProductEventFactory();
    private static final Logger logger = LoggerFactory.getLogger(ProductEventFactory.class);
    private final Map<ProductEvent.EventType,EventCreator<?>> creatorMap = new HashMap<ProductEvent.EventType, EventCreator<?>>();
    private ProductEventFactory(){
        init();
    }
    public static ProductEventFactory getInstance(){
        return instance;
    }

    private void init(){
        creatorMap.put(ProductEvent.EventType.SERVICE,new ProductServiceEventCreator());
        creatorMap.put(ProductEvent.EventType.MESSAGE,new ProductMessageBatchEventCreator());
        creatorMap.put(ProductEvent.EventType.FILEMETA,new ProductFileMetaEventCreator());
    }
    public ProductEvent creatEvent(IProtocol.Request request){
        ProductEvent.EventType eventType = translator(request.getHeader().getBimpl());
        EventCreator<?> creator = creatorMap.get(eventType);
        return creator.create(request);
    }

    public ProductEvent.EventType translator(IProtocol.Header.Bimpl bimpl){
        switch (bimpl){
            case RPC:
                return ProductEvent.EventType.SERVICE;
            case MessageBatch:
                return ProductEvent.EventType.MESSAGE;
            case FileMeta:
                return ProductEvent.EventType.FILEMETA;
            default:
               return null;
        }
    }

    interface EventCreator<T extends ProductEvent>{
        T create(IProtocol.Request request);
    }
    class ProductServiceEventCreator implements EventCreator<ProductServiceEvent>{

        public ProductServiceEvent create(IProtocol.Request request) {
            String productCode = request.getRBody().getPayhead().getDispath();
            ProductServiceEvent event = new ProductServiceEvent(productCode, ProductEvent.EventType.SERVICE);
            event.setFrom(request.getRBody().getPayhead().getInvoker());
            String routeInfo = request.getHeader().getVersion()+"-"+request.getRBody().getPayhead().getTcode();
            event.setRouteInfo(routeInfo);
            event.setData(request.getRBody().getPayload().getData());
            return event;
        }
    }
    class ProductMessageBatchEventCreator implements EventCreator<ProductMessageBatchEvent>{

        public ProductMessageBatchEvent create(IProtocol.Request request) {
            return null;
        }
    }

    class ProductFileMetaEventCreator implements EventCreator<ProductFileMetaEvent>{

        public ProductFileMetaEvent create(IProtocol.Request request) {
            return null;
        }
    }

}
