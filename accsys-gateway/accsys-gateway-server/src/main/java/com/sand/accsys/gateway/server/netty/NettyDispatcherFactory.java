package com.sand.accsys.gateway.server.netty;

import com.sand.accsys.gateway.dispatcher.Dispatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : huanghy
 * @create : 2016/3/15 0015 下午 5:19
 * @since : ${VERSION}
 */
public class NettyDispatcherFactory {

    private static Map<String,Dispatcher> dispatcherCache = new HashMap<String, Dispatcher>(5);

    public static synchronized Dispatcher create(String name){
        Dispatcher dispatcher = dispatcherCache.get(name);
        if(dispatcher==null){
            dispatcher = new NettyServerDispatcher();
            dispatcherCache.put(name,dispatcher);
        }
        return dispatcher;
    }

    public static Dispatcher get(String name){
        return create(name);
    }
}
