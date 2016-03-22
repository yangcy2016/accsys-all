package com.sand.accsys.client.product.thrift;

import com.google.common.base.Splitter;
import com.netflix.config.DynamicStringProperty;
import com.sand.accsys.client.product.Client;
import com.sand.accsys.client.product.ProductInvoker;
import com.sand.accsys.common.config.DynamicPropertyHelper;
import com.sand.accsys.thrift.connect.ConnectionManager;
import com.sand.accsys.thrift.connect.GenericConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author : huanghy
 * @create : 2016/3/22 0022 下午 2:05
 * @since : ${VERSION}
 */
public class ProductInvokerManager {
    private static final ProductInvokerManager instance = new ProductInvokerManager();
    private final Map<String,ProductInvoker> invokerMap = new HashMap<String, ProductInvoker>(10);
    private static final Logger logger = LoggerFactory.getLogger(ProductInvokerManager.class);
    private ProductInvokerManager(){
    }
    public static ProductInvokerManager getInstance(){
        return instance;
    }
    public void loadAll(){
        DynamicStringProperty support = DynamicPropertyHelper.getStringProperty("accsys.gateway.support.products", "");
        Iterator<String> it = Splitter.on(',').trimResults().omitEmptyStrings().split(support.get()).iterator();
        while (it.hasNext()){
            String product = it.next();
            int maxActive           = DynamicPropertyHelper.getIntProperty("accsys.product."+product+".client.connect.pool.maxActive",30).get();
            int maxIdle             = DynamicPropertyHelper.getIntProperty("accsys.product."+product+".client.connect.pool.maxIdle",10).get();
            int minIdle             = DynamicPropertyHelper.getIntProperty("accsys.product."+product+".client.connect.pool.minIdle",5).get();
            long maxWait             = DynamicPropertyHelper.getLongProperty("accsys.product." + product + ".client.connect.pool.maxWait", 200000).get();
            String serviceHost      = DynamicPropertyHelper.getStringProperty("accsys.product"+product+".client.toremote.host","127.0.0.1").get();
            int servicePort         = DynamicPropertyHelper.getIntProperty("accsys.product."+product+".client.toremote.port",5568).get();
            int timeout             = DynamicPropertyHelper.getIntProperty("accsys.product."+product+".client.toremote.timeout",20000).get();
            GenericConnectionProvider provider = new GenericConnectionProvider(
                    serviceHost,
                    servicePort,
                    timeout,
                    maxActive,
                    maxIdle,
                    minIdle,
                    maxWait
            );
            try {
                provider.afterPropertiesSet();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ConnectionManager manager = new ConnectionManager();
            manager.setProvider(provider);
            Client client = new ThriftClient(manager);
            ProductInvoker invoker = new ProductThriftInvokerImpl(client);
            invokerMap.put(product,invoker);
            logger.info("put product invoker {} at map {}",product,invoker);
        }
    }

    public ProductInvoker getInvoker(String key){
        return invokerMap.get(key);
    }
}
