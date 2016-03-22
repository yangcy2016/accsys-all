package com.sand.accsys.client.product.event;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 上午 11:18
 * @since : ${VERSION}
 */
public abstract class ProductEvent {
    private String from;            //数据来源

    private String routeInfo;       //路由信息

    private String productCode;     //产品编号

    private String respCode;        //响应码

    private String respMsg ;        //响应消息

    private EventType eventType;    //事件类型

    public ProductEvent(String productCode, EventType eventType) {
        this.productCode = productCode;
        this.eventType = eventType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(String routeInfo) {
        this.routeInfo = routeInfo;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public abstract String toStringData();

    public EventType getEventType() {
        return eventType;
    }

    public static enum EventType{
        SERVICE,  /**服务调用事件*/
        MESSAGE,  /**批量消息事件*/
        FILEMETA; /**文件通知事件*/
    }

}
