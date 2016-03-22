package com.sand.accsys.product.bootstrap;

import com.sand.accsys.product.server.protocol.thrift.ProductServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : huanghy
 * @create : 2016/3/22 0022 上午 10:21
 * @since : ${VERSION}
 */
public class ProductBootstrap {
    private static final Logger logger = LoggerFactory.getLogger("ProductBootstrap");
    public static void main(String[] args) {
        logger.info("start all rpc server begin");
        if(startAllRpcServer()){
            logger.info("start all epc server success");
        }

    }

    public static boolean startAllRpcServer(){
        return ProductServerManager.getInstance().createRpcServer().startAllRpcServer();
    }
}
