package com.sand.accsys.gateway.server.test;

import com.sand.accsys.gateway.server.netty.NettyServer;
import com.sand.accsys.protocol.protocolbuf.IProtocol;
import org.junit.Test;
import sun.misc.Unsafe;
import sun.reflect.Reflection;

import java.lang.reflect.Field;

/**
 * @author : huanghy
 * @create : 2016/3/15 0015 下午 1:47
 * @since : ${VERSION}
 */
public class NettyServerTest {

    @Test
    public void testStartServer(){
        NettyServer nettyServer = new NettyServer(8080);
        nettyServer.start();
    }

    @Test
    public void test(){
        System.out.println(Reflection.getCallerClass());
        System.out.println(Unsafe.getUnsafe().arrayIndexScale(Object[].class));
    }

    public static void main(String[] args) throws IllegalAccessException {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe) field.get(null);
            System.out.println(unsafe.arrayIndexScale(IProtocol[].class));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
