package com.sand.accsys.product.server.protocol.thrift;

import com.google.common.base.Splitter;
import com.netflix.config.DynamicStringProperty;
import com.sand.accsys.common.config.DynamicPropertyHelper;
import com.sand.accsys.product.server.protocol.ProductServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : huanghy
 * @create : 2016/3/22 0022 上午 9:52
 * @since : ${VERSION}
 */
public class ProductServerManager {
    private static final ProductServerManager instance = new ProductServerManager();

    private static final Logger logger = LoggerFactory.getLogger(ProductServerManager.class);

    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final Map<String,ProductServer> rpcServerMap = new HashMap<>();
    public static ProductServerManager getInstance(){
        return instance;
    }

    public ProductServerManager createRpcServer(){
        DynamicStringProperty supportProp = DynamicPropertyHelper.getStringProperty("accsys.product.support","");
        Iterator<String> it = Splitter.on(',').trimResults().omitEmptyStrings().split(supportProp.get()).iterator();
        while (it.hasNext()){
            String product = it.next();
            int port                     = DynamicPropertyHelper.getIntProperty("accsys.product."+product+".server.port",0).get();
            int selectorthreads          = DynamicPropertyHelper.getIntProperty("accsys.product."+product+".server.selectorthreads",2).get();
            int workthreads              = DynamicPropertyHelper.getIntProperty("accsys.product."+product+".server.workthreads",5).get();
            int acceptqueuesizeterthread = DynamicPropertyHelper.getIntProperty("accsys.product."+product+".server.acceptqueuesizeterthread",100).get();
            String policy                = DynamicPropertyHelper.getStringProperty("accsys.product"+product+".server.policy","FAIR").get();
            int fixedThreadPoolSize      = DynamicPropertyHelper.getIntProperty("accsys.product" + product + ".server.fixedThreadPoolSize", 100).get();
            ProductServer server = new ThriftProductServer(
                    selectorthreads,
                    workthreads,
                    acceptqueuesizeterthread,
                    policy,
                    fixedThreadPoolSize,
                    port
            );
            rpcServerMap.put(product,server);
        }
        return this;
    }

    public boolean startAllRpcServer(){
        Iterator<String> it  = rpcServerMap.keySet().iterator();
        int i = 0;
        while (it.hasNext()){
            String product = it.next();
            ProductServer server = rpcServerMap.get(product);
            executor.submit(new RunRpcServerTask(server));
            i++;
        }
        return i==rpcServerMap.size();
    }

    public void stopAllRpcServer(){
        Iterator<String> it  = rpcServerMap.keySet().iterator();
        while (it.hasNext()){
            String product = it.next();
            ProductServer server = rpcServerMap.get(product);
            if(server.isActive()){
                server.stop();
                logger.info("stop rpc server {},success",product,server.port());
            }
        }
    }

    static class RunRpcServerTask implements Runnable{
        private final ProductServer server;

        public RunRpcServerTask(ProductServer server) {
        this.server = server;
        }
        @Override
        public void run() {
            if(!server.isActive()){
                server.start();
                logger.info("start rpc server on port {},success",server.port());
            }
        }
    }
}
