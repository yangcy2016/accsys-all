package com.sand.accsys.client.gateway.server.netty;

import com.sand.accsys.protocol.ProtocolConstant;
import com.sand.accsys.protocol.protocolbuf.IProtocol;
import com.sand.accsys.protocol.protocolbuf.ProtocolbufPackUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : huanghy
 * @create : 2016/3/15 0015 下午 2:49
 * @since : ${VERSION}
 */
public class NettyClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("localhost",5555))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtobufVarint32FrameDecoder())
                                    .addLast(new ProtobufDecoder(IProtocol.Request.getDefaultInstance()))
                                    .addLast(new ProtobufVarint32LengthFieldPrepender())
                                    .addLast(new ProtobufEncoder())
                                    .addLast(new SimpleChannelInboundHandler() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            Map<String,Object> reqParams = new HashMap<String, Object>();
                                            reqParams.put(ProtocolConstant.BIMPL,"01");
                                            reqParams.put(ProtocolConstant.R_DISPATCH,"100010");
                                            reqParams.put(ProtocolConstant.R_INVOKER,"sacp");
                                            reqParams.put(ProtocolConstant.R_TCODE,"Abacus.Freezing.batchUnfreezeMoney");
                                            reqParams.put(ProtocolConstant.R_DATA,"{}");
                                            IProtocol.Request request = ProtocolbufPackUtil.pack(reqParams);
                                            for(int i=0;i<100;i++){
                                                ctx.writeAndFlush(request);
                                            }
                                            //ctx.writeAndFlush(request);

                                        }
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            System.out.println(msg);
                                        }
                                    });
                        }
                    });

            ChannelFuture future = bootstrap.connect().sync();
            future.channel().closeFuture().sync();

        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}
