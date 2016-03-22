package com.sand.accsys.gateway.exchange.disruptor.handler;

import com.google.common.base.Optional;
import com.sand.accsys.client.product.ProductInvoker;
import com.sand.accsys.client.product.event.ProductEvent;
import com.sand.accsys.client.product.thrift.ProductInvokerManager;
import com.sand.accsys.gateway.server.netty.NettyInnerEvent;
import com.sand.accsys.protocol.protocolbuf.IProtocol;
import com.sand.accsys.protocol.protocolbuf.ProtocolbufPackUtil;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 上午 10:02
 * @since : ${VERSION}
 */
public class GatewayWorkHandler extends AbstractWorkHandler<NettyInnerEvent>{
    private static final Logger logger = LoggerFactory.getLogger(GatewayWorkHandler.class);
    @Override
    public void doHandle(NettyInnerEvent innerEvent) {
        IProtocol.Response response = null;
        ChannelHandlerContext ctx = innerEvent.getCtx();
        IProtocol.Request request = innerEvent.getRequest();
        try {
            ProductEvent productEvent = innerEvent.getProductEvent();
            ProductInvoker invoker = ProductInvokerManager.getInstance().getInvoker(productEvent.getProductCode());
            ProductEvent respEvent = Optional.of(invoker.execute(productEvent)).get();
            response = ProtocolbufPackUtil.copyFromPackAndSign(
                    request,
                    respEvent.getRespCode(),
                    respEvent.getRespMsg(),
                    respEvent.toStringData()
            );
        }catch (Exception e){
            logger.error("gateway call product occur a error,cause by {}",e.getMessage());
            e.printStackTrace();
        }finally {
            if(response!=null){
                logger.info("gateway receive response ->{}",response);
                ctx.writeAndFlush(response);
            }else {
                response = ProtocolbufPackUtil.copyFromPackAndSign(request,"","","");
                ctx.writeAndFlush(response);
            }
        }


    }
}
