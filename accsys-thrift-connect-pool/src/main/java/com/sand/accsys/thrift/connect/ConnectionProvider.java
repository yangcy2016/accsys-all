package com.sand.accsys.thrift.connect;

import org.apache.thrift.transport.TSocket;

/**
 * @author : huanghy
 * @create : 2016/3/22 0022 上午 11:11
 * @since : ${VERSION}
 */
public interface ConnectionProvider {
    
    TSocket getConnection();

    void returnCon(TSocket socket);
}
