package com.sand.accsys.client.product.event;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 上午 11:30
 * @since : ${VERSION}
 */
public class ProductFileMetaEvent extends ProductEvent{
    public ProductFileMetaEvent(String productCode, EventType eventType) {
        super(productCode, eventType);
    }

    @Override
    public String toStringData() {
        return null;
    }
}
