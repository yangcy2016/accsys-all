package com.sand.accsys.protocol.test;

import com.sand.accsys.protocol.protocolbuf.IProtocol;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : huanghy
 * @create : 2016/3/14 0014 下午 3:36
 * @since : ${VERSION}
 */
public class IProtocolTest {

    @Test
    public void testBuildReq(){
        long start = System.currentTimeMillis();
        IProtocol.Header
                header = IProtocol.Header.newBuilder()
                .setVersion("1.0.0")
                .setBimpl(IProtocol.Header.Bimpl.RPC)
                .setBlength("10000")
                .setCmark("11000000")
                .setCsum("abc122323sasdasd")
                .setDtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
                .build();
        IProtocol.RpcBody.Payhead
                payhead = IProtocol.RpcBody.Payhead.newBuilder()
                .setDispath("100030")
                .setInvoker("test_client")
                .setTcode("Abacus.Freezing.batchUnfreezeMoney")
                .setRespCode("000000")
                .setRespMsg("OK")
                .build();
        IProtocol.RpcBody.Payload payload = IProtocol.RpcBody.Payload.newBuilder()
                .setData("test_data")
                .build();
        IProtocol.RpcBody rpcBody = IProtocol.RpcBody.newBuilder()
                .setPayhead(payhead)
                .setPayload(payload)
                .build();
        IProtocol.Request request = IProtocol.Request.newBuilder()
                .setHeader(header)
                .setRBody(rpcBody)
                .build();
        long end = System.currentTimeMillis();
        System.out.println("cast:"+(end-start));

        System.out.println(request);
    }
    @Test
    public void test2(){
        testBuildReq();
        testBuildReq();
        testBuildReq();
        testBuildReq();
    }
}
