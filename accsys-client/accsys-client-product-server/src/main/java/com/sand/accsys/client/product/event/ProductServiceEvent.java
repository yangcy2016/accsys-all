package com.sand.accsys.client.product.event;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 上午 11:30
 * @since : ${VERSION}
 */
public class ProductServiceEvent extends ProductEvent{
    private String data;

    public ProductServiceEvent(String productCode, EventType eventType) {
        super(productCode, eventType);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toStringData() {
        return data;
    }
}
