package com.sand.accsys.client.product.thrift;

import com.sand.accsys.client.product.Client;
import com.sand.accsys.client.product.ProductInvoker;
import com.sand.accsys.client.product.event.ProductEvent;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 上午 11:17
 * @since : ${VERSION}
 */
public class ProductThriftInvokerImpl implements ProductInvoker {
    private Client client;

    public ProductThriftInvokerImpl(Client client) {
        this.client = client;
    }

    public ProductEvent execute(ProductEvent event) {
        client.onConnected();
        return client.invoke(event);
    }
}
