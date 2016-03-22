package com.sand.accsys.gateway.server.netty;

import com.sand.accsys.client.product.event.ProductEvent;
import com.sand.accsys.infrastructure.exchange.event.InnerEvent;
import com.sand.accsys.protocol.protocolbuf.IProtocol;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author : huanghy
 * @create : 2016/3/15 0015 下午 4:38
 * @since : ${VERSION}
 */
public class NettyInnerEvent implements InnerEvent {

    private long id;
    private String name;
    private ChannelHandlerContext ctx;
    private IProtocol.Request request;

    private ProductEvent productEvent;

    public NettyInnerEvent(ChannelHandlerContext ctx, IProtocol.Request request, long id){
        this(ctx,request,id,null);
    }
    public NettyInnerEvent(ChannelHandlerContext ctx, IProtocol.Request request, long id, String name) {
        this.id = id;
        this.name = name;
        this.ctx = ctx;
        this.request = request;
    }

    public IProtocol.Request getRequest() {
        return request;
    }

    public ProductEvent getProductEvent() {
        return productEvent;
    }

    public void setProductEvent(ProductEvent productEvent) {
        this.productEvent = productEvent;
    }

    public long id() {
        return id;
    }
    public String name() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    @Override
    public String toString() {
        return "NettyDataEvent{" +
                "request=" + request +
                '}';
    }
}
