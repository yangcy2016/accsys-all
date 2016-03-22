package com.sand.accsys.client.product.thrift;

import com.sand.accsys.client.product.Client;
import com.sand.accsys.client.product.event.ProductEvent;
import com.sand.accsys.client.product.event.ProductServiceEvent;
import com.sand.accsys.infrastructure.idl.thrift.service.ProductService;
import com.sand.accsys.infrastructure.idl.thrift.struct.ProductInfo;
import com.sand.accsys.infrastructure.idl.thrift.struct.ServiceRequest;
import com.sand.accsys.infrastructure.idl.thrift.struct.ServiceResponse;
import com.sand.accsys.thrift.connect.ConnectionManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : huanghy
 * @create : 2016/3/21 0021 下午 3:54
 * @since : ${VERSION}
 */
public class ThriftClient implements Client{
    private static final Logger logger = LoggerFactory.getLogger(ThriftClient.class);
    private TTransport transport;
    private ProductService.Client client;
    private ConnectionManager connectionManager;

    private boolean connectOK = false;

    public ThriftClient(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void onConnected() {
        transport = new TFramedTransport(connectionManager.getConnection());
        TProtocol protocol = new TCompactProtocol(transport);
        client = new ProductService.Client(protocol);
    }



    public ProductEvent invoke(ProductEvent event) {
        ProductEvent responseEvent = null;
            if(!transport.isOpen()){
                //do some error handle
            }else{
               if(event.getEventType()== ProductEvent.EventType.SERVICE){
                   try{
                       ServiceRequest request = convertToServiceRequest(event);
                       ServiceResponse response = client.doInService(request);
                       responseEvent = converToServiceProductEvent(response);
                   }catch (Exception e){
                        e.printStackTrace();
                   }
               }else if(event.getEventType()== ProductEvent.EventType.MESSAGE){

               }else if(event.getEventType()== ProductEvent.EventType.FILEMETA){

               }
            }
        return responseEvent;
    }

    private ServiceRequest convertToServiceRequest(ProductEvent event){
        ServiceRequest request = new ServiceRequest();
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductCode(event.getProductCode());
        productInfo.setFromInfo(event.getFrom());
        productInfo.setRouteInfo(event.getRouteInfo());
        request.setProductInfo(productInfo);
        request.setData(event.toStringData());
        return request;
    }

    private ProductEvent converToServiceProductEvent(ServiceResponse response){
        ProductServiceEvent event = new ProductServiceEvent(response.getProductInfo().getProductCode(), ProductEvent.EventType.SERVICE);
        event.setRespCode(response.getResponseInfo().getRespCode());
        event.setRespMsg(response.getResponseInfo().getRespMsg());
        event.setData(response.getData());
        return event;
    }

    private ServiceRequest convertToMessageBatchRequest(ProductEvent event){
        return null;
    }

    private ServiceRequest convertToFileMateBatchRequest(ProductEvent event){
        return null;
    }

    public void closeConnected() {
        if(transport!=null&&transport.isOpen()){transport.close();}
        logger.info("close connection success");
    }
}
