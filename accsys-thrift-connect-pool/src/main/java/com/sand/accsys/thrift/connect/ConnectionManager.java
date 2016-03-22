package com.sand.accsys.thrift.connect;

import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : huanghy
 * @create : 2016/3/22 0022 下午 1:33
 * @since : ${VERSION}
 */
public class ConnectionManager{
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    private ConnectionProvider provider;
    private ThreadLocal<TSocket> local = new ThreadLocal<>();

    public TSocket getConnection(){
        TSocket socket = local.get();
        if(socket==null){
            if(provider==null){
                throw new IllegalArgumentException("provider must be set ,before call this method");
            }
            socket = provider.getConnection();
            local.set(socket);
        }
        return socket;
    }

    public ConnectionProvider getProvider() {
        return provider;
    }

    public void setProvider(ConnectionProvider provider) {
        this.provider = provider;
    }
}
