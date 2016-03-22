package com.sand.accsys.client.product;

import com.sand.accsys.client.product.event.ProductEvent;

/**
 * @author : huanghy
 * @create : 2016/3/21 0021 下午 3:48
 * @since : ${VERSION}
 */
public interface Client {
    void onConnected();

    ProductEvent invoke(ProductEvent event);

    void closeConnected();
}
