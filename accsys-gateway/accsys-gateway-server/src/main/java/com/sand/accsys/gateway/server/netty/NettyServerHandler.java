package com.sand.accsys.gateway.server.netty;

import com.sand.accsys.gateway.dispatcher.Dispatcher;
import com.sand.accsys.protocol.protocolbuf.IProtocol;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author : huanghy
 * @create : 2016/3/15 0015 下午 1:37
 * @since : ${VERSION}
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof IProtocol.Request){
            Dispatcher dispatcher =  NettyDispatcherFactory.get("netty-server-1");
            dispatcher.asyncDispatch(new NettyInnerEvent(ctx, (IProtocol.Request) msg,1L));
        }
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("channelInactive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    private void closeRequestChannel(ChannelHandlerContext ctx){
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

}
