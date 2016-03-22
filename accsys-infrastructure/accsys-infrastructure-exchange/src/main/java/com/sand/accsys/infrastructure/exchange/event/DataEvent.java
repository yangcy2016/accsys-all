package com.sand.accsys.infrastructure.exchange.event;

/**
 * @author : huanghy
 * @create : 2016/3/16 0016 上午 10:36
 * @since : ${VERSION}
 */
public abstract class DataEvent {
    private InnerEvent event;

    public DataEvent(){

    }
    public DataEvent(InnerEvent event) {
        this.event = event;
    }

    public InnerEvent getEvent() {
        return event;
    }

    public void setEvent(InnerEvent event) {
        this.event = event;
    }
}
