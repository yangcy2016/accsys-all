package com.sand.accsys.protocol;

import java.util.Map;

/**
 * @author : huanghy
 * @create : 2016/3/14 0014 下午 3:26
 * @since : ${VERSION}
 */
public interface Packer<R,T> {
    /**
     * 从map将请求报文打包
     * @param target
     * @return
     */
    R pack(Map<String,Object> target);

    /**
     * 从返回报文中解包
     * @param t
     * @return
     */
    Map<String,Object> unpack(T t);

    /**
     * 复用请求中的公用数据，打包生成响应
     * @param r
     * @param respCode
     * @param respMsg
     * @param data
     * @return
     */
    T copyFromPack(R r,String respCode,String respMsg,String data);
}
