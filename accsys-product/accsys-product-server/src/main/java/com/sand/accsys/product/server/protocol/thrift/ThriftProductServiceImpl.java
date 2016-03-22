package com.sand.accsys.product.server.protocol.thrift;

import com.sand.accsys.infrastructure.idl.thrift.service.FileMateBatchException;
import com.sand.accsys.infrastructure.idl.thrift.service.MessageBatchException;
import com.sand.accsys.infrastructure.idl.thrift.service.ProductService;
import com.sand.accsys.infrastructure.idl.thrift.service.ServiceException;
import com.sand.accsys.infrastructure.idl.thrift.struct.*;
import org.apache.thrift.TException;

/**
 * @author : huanghy
 * @create : 2016/3/21 0021 下午 3:08
 * @since : ${VERSION}
 */
public class ThriftProductServiceImpl implements ProductService.Iface{
    public ServiceResponse doInService(ServiceRequest request) throws ServiceException, TException {
        System.out.println("doInService invoked");
        System.out.println(request);
        ServiceResponse response = new ServiceResponse();
        response.setProductInfo(request.getProductInfo());
        response.setData("{}");
        ResponseInfo responseInfo = new ResponseInfo(
                "000000",
                "success"
        );
        response.setResponseInfo(responseInfo);
        return response;
    }

    public MessageBatchResponse doMessageBatch(MessageBatchRequest request) throws MessageBatchException, TException {
        System.out.println("doMessageBatch invoked");
        return null;
    }

    public FileMateBatchResponse doFileMateBatch(FileMateBatchRequest request) throws FileMateBatchException, TException {
        System.out.println("doFileMateBatch invoked");
        return null;
    }
}
