package com.sand.accsys.gateway.server.netty;

import com.sand.accsys.gateway.server.AbstractServer;
import com.sand.accsys.gateway.server.api.ServerType;
import com.sand.accsys.protocol.protocolbuf.IProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author : huanghy
 * @create : 2016/3/15 0015 上午 11:19
 * @since : ${VERSION}
 */
public class NettyServer extends AbstractServer {

    private  int bossThreadCount;
    private  int workThreadCount;
    private  int backlog = 2048;
    private  boolean tcpNodelay = true;
    private  boolean keepAlive  = true;
    private  LogLevel logLevel = LogLevel.INFO;
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private ChannelFuture channelFuture;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;

    public NettyServer(int endPoint) {
        this(endPoint,Runtime.getRuntime().availableProcessors()+1,Runtime.getRuntime().availableProcessors()*2+1);
    }
    public NettyServer(int endPoint,int bossThreadCount,int workThreadCount){
        this(endPoint,bossThreadCount,workThreadCount,2048);
    }
    public NettyServer(int endPoint,int bossThreadCount,int workThreadCount,int backlog){
       this(endPoint,bossThreadCount,workThreadCount,backlog,LogLevel.INFO);
    }
    public NettyServer(int endPoint,int bossThreadCount,int workThreadCount,int backlog,LogLevel logLevel){
        this.endPoint        = endPoint;
        this.bossThreadCount = bossThreadCount;
        this.workThreadCount = workThreadCount;
        this.backlog         = backlog;
        this.logLevel        = logLevel;
    }
    @Override
    protected void startInner() {
        bossGroup = new NioEventLoopGroup(bossThreadCount);
        workGroup = new NioEventLoopGroup(workThreadCount);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, backlog)
                    .childOption(ChannelOption.TCP_NODELAY, tcpNodelay)
                    .childOption(ChannelOption.SO_KEEPALIVE, keepAlive)
                    .handler(new LoggingHandler(logLevel))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtobufVarint32FrameDecoder())
                                    .addLast(new ProtobufDecoder(IProtocol.Request.getDefaultInstance()))
                                    .addLast(new ProtobufVarint32LengthFieldPrepender())
                                    .addLast(new ProtobufEncoder())
                                    .addLast(new NettyServerHandler());
                        }
                    });
             channelFuture =bootstrap.bind(endPoint).sync();
            logger.info("netty server start at point:{},success",endPoint);
        }catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("when start netty occur an error cause by :{}",e.getCause());
        }finally {
            stopInner();
        }
    }

    @Override
    protected void stopInner() {
        try {
            channelFuture.channel().closeFuture().sync();
            if(bossGroup!=null){
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
            }
            logger.info("netty server stop success");
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("stop netty server failed cause by:{}",e.getCause());
        }

    }

    public ServerType serverType() {
        return ServerType.SOCKET;
    }

    public static void main(String[] args) {
    }
}
