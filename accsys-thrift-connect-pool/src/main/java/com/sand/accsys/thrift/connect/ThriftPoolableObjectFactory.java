package com.sand.accsys.thrift.connect;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : huanghy
 * @create : 2016/3/22 0022 下午 1:24
 * @since : ${VERSION}
 */
public class ThriftPoolableObjectFactory implements PoolableObjectFactory<TSocket>{
    private static final Logger logger = LoggerFactory.getLogger(ThriftPoolableObjectFactory.class);
    private String  serviceHost;
    private int     servicePort;
    private int     connTimeout;

    public ThriftPoolableObjectFactory(String serviceHost, int servicePort, int connTimeout) {
        this.serviceHost = serviceHost;
        this.servicePort = servicePort;
        this.connTimeout = connTimeout;
    }

    @Override
    public TSocket makeObject() throws Exception {
        if(logger.isDebugEnabled()){
            logger.debug("instance a poolable object");
        }
        return new TSocket(serviceHost,servicePort,connTimeout);
    }

    @Override
    public void destroyObject(TSocket obj) throws Exception {
        if(obj.isOpen()){
            obj.close();
        }
    }

    @Override
    public boolean validateObject(TSocket obj) {
        return obj.isOpen();
    }

    @Override
    public void activateObject(TSocket obj) throws Exception {
        obj.open();
    }

    @Override
    public void passivateObject(TSocket obj) throws Exception {
        if(obj.isOpen()){
            obj.close();
        }
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

    public int getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }
}
