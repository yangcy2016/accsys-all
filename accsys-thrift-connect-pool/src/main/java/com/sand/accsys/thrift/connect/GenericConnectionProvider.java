package com.sand.accsys.thrift.connect;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * @author : huanghy
 * @create : 2016/3/22 0022 上午 11:15
 * @since : ${VERSION}
 */
public class GenericConnectionProvider implements ConnectionProvider,InitializingBean,DisposableBean{

    private static final Logger logger = LoggerFactory.getLogger(GenericConnectionProvider.class);

    /**服务ip地址*/
    private String serviceHost;
    /**服务端口号*/
    private int servicePort;
    /**连接超时时间*/
    private int connTimeout;
    /**可以从缓存池中分配对象的最大数量*/
    private int maxActive;
    /**缓存池中最大空闲对象数*/
    private int maxIdle;
    /**缓存池中最小空闲对象数*/
    private int minIdle;
    /**阻塞的最大数量*/
    private long maxWait;
    /** 从缓存池中分配对象，是否执行PoolableObjectFactory.validateObject方法 */
    private boolean testOnBorrow    = GenericObjectPool.DEFAULT_TEST_ON_BORROW;
    private boolean testOnReturn    = GenericObjectPool.DEFAULT_TEST_ON_RETURN;
    private boolean testWhileIdle   = GenericObjectPool.DEFAULT_TEST_WHILE_IDLE;
    /**对象缓冲池*/
    private ObjectPool objectPool;


    public GenericConnectionProvider() {
    }



    public GenericConnectionProvider(String serviceHost, int servicePort, int connTimeout, int maxActive, int maxIdle, int minIdle, long maxWait) {
        this.serviceHost = serviceHost;
        this.servicePort = servicePort;
        this.connTimeout = connTimeout;
        this.maxActive = maxActive;
        this.maxIdle = maxIdle;
        this.minIdle = minIdle;
        this.maxWait = maxWait;
    }

    @Override
    public TSocket getConnection() {
        TSocket socket = null;
        try {
            socket = (TSocket) objectPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("getConnection error",e);
        }
        return socket;
    }

    @Override
    public void returnCon(TSocket socket) {
        try {
            objectPool.returnObject(socket);
        } catch (Exception e) {
            throw new RuntimeException("return connection error ",e);
        }
    }

    @Override
    public void destroy() throws Exception {
        objectPool.close();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        objectPool = new GenericObjectPool();
        ((GenericObjectPool) objectPool).setMaxActive(maxActive);
        ((GenericObjectPool) objectPool).setMaxIdle(maxIdle);
        ((GenericObjectPool) objectPool).setMinIdle(minIdle);
        ((GenericObjectPool) objectPool).setMaxWait(maxWait);
        ((GenericObjectPool) objectPool).setTestOnReturn(testOnReturn);
        ((GenericObjectPool) objectPool).setTestOnBorrow(testOnBorrow);
        ((GenericObjectPool) objectPool).setTestWhileIdle(testWhileIdle);
        ((GenericObjectPool) objectPool).setWhenExhaustedAction(GenericObjectPool.DEFAULT_WHEN_EXHAUSTED_ACTION);
        ((GenericObjectPool) objectPool).setFactory(new ThriftPoolableObjectFactory(serviceHost,servicePort,connTimeout));
    }


    public String getServiceHost() {
        return serviceHost;
    }

    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public long getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }
}
