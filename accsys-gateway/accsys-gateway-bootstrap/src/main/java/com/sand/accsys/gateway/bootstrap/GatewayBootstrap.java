package com.sand.accsys.gateway.bootstrap;

import com.sand.accsys.client.product.thrift.ProductInvokerManager;
import com.sand.accsys.common.config.DynamicPropertyHelper;
import com.sand.accsys.gateway.exchange.disruptor.DisruptorExchangeGroup;
import com.sand.accsys.gateway.server.api.IServer;
import com.sand.accsys.gateway.server.netty.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : huanghy
 * @create : 2016/3/17 0017 下午 1:46
 * @since : ${VERSION}
 */
public class GatewayBootstrap {
    private static final Logger logger = LoggerFactory.getLogger(GatewayBootstrap.class);
    public static void main(String[] args) {
        logger.info("start all exchanges begin ...");
        startAllExchange();

        logger.info("start all product client begin ...");
        startAllProductClient();
        logger.info("start server begin ...");
        startNettyServer();
    }

    private static void startAllExchange(){
        DisruptorExchangeGroup group = DisruptorExchangeGroup.getInstance();
        group.startAll();
        logger.info("start all exchanges size {} success",group.size());
    }

    private static void startNettyServer(){
        int endPoint = DynamicPropertyHelper.getIntProperty("accsys.gateway.netty.listen.port",5555).get();
        IServer iServer = new NettyServer(endPoint);
        iServer.start();
    }

    private static void startAllProductClient(){
        ProductInvokerManager.getInstance().loadAll();
    }
}
