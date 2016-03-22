package com.sand.accsys.client.product;

import com.sand.accsys.client.product.event.ProductEvent;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 上午 11:16
 * @since : ${VERSION}
 */
public interface ProductInvoker {
    ProductEvent execute(ProductEvent event);
}
