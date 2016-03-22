package com.sand.accsys.product.server.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : huanghy
 * @create : 2016/3/21 0021 下午 3:01
 * @since : ${VERSION}
 */
public abstract class ProductServer {
    protected Logger logger = LoggerFactory.getLogger(ProductServer.class);
    protected int port = 6666;
    protected volatile boolean active;

    public abstract void    start();

    public abstract void    stop();

    public boolean isActive(){
        return this.active;
    }
    public int port(){
        return this.port;
    }
}
