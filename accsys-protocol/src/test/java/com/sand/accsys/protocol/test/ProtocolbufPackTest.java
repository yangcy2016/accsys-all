package com.sand.accsys.protocol.test;

import com.sand.accsys.protocol.ProtocolConstant;
import com.sand.accsys.protocol.protocolbuf.IProtocol;
import com.sand.accsys.protocol.protocolbuf.ProtocolbufPackUtil;
import org.junit.Test;

import java.util.*;

/**
 * @author : huanghy
 * @create : 2016/3/15 0015 上午 10:26
 * @since : ${VERSION}
 */
public class ProtocolbufPackTest {

    private IProtocol.Request buildRpcRequest(){
        Map<String,Object> reqParams = new HashMap<String, Object>();
        reqParams.put(ProtocolConstant.BIMPL,"01");
        reqParams.put(ProtocolConstant.R_DISPATCH,"2200010");
        reqParams.put(ProtocolConstant.R_INVOKER,"sacp");
        reqParams.put(ProtocolConstant.R_TCODE,"Abacus.Freezing.batchUnfreezeMoney");
        reqParams.put(ProtocolConstant.R_DATA,"{}");
        IProtocol.Request request = ProtocolbufPackUtil.pack(reqParams);
        return request;
    }

    @Test
    public void testRpcPack(){
        IProtocol.Request request = buildRpcRequest();
        System.out.println(request);
    }
    @Test
    public void testRpcValidSign(){
        IProtocol.Request request = buildRpcRequest();
        boolean valid = ProtocolbufPackUtil.validSign(request);
        System.out.println(valid);
    }

    private IProtocol.Request buildMessageBatch(){
        Map<String,Object> reqParams = new HashMap<String, Object>();
        reqParams.put(ProtocolConstant.BIMPL,"02");
        reqParams.put(ProtocolConstant.M_DISPATCH,"1200001");
        reqParams.put(ProtocolConstant.M_INVOKER,"mass");
        reqParams.put(ProtocolConstant.M_HEAD,"{}");
        reqParams.put(ProtocolConstant.M_BATCHNO, UUID.randomUUID().toString());
        reqParams.put(ProtocolConstant.M_DATA,"{}");
        reqParams.put(ProtocolConstant.M_TCODE,"Abacus.Freezing.batchUnfreezeMoney");
        reqParams.put(ProtocolConstant.M_MESSAGES,new ArrayList<String>());
        IProtocol.Request request = ProtocolbufPackUtil.pack(reqParams);
        return  request;
    }

    @Test
    public void testMessageBatch(){
        IProtocol.Request request = buildMessageBatch();
        System.out.println(request);
    }

    @Test
    public void testMessageBatchValidSign(){
       boolean valid =  ProtocolbufPackUtil.validSign(buildMessageBatch());
        System.out.println(valid);
    }

    private IProtocol.Request buildFileMedaRequest(){
        Map<String,Object> reqParams = new HashMap<String, Object>();
        reqParams.put(ProtocolConstant.BIMPL,"03");
        reqParams.put(ProtocolConstant.F_DISPATCH,"1000010");
        reqParams.put(ProtocolConstant.F_INVOKER,"test");
        reqParams.put(ProtocolConstant.F_HEAD,"{}");
        reqParams.put(ProtocolConstant.F_BATCHNO,UUID.randomUUID().toString());
        reqParams.put(ProtocolConstant.F_DATA,"{}");
        reqParams.put(ProtocolConstant.F_FILENAME,"/test/file.txt");
        reqParams.put(ProtocolConstant.F_FILEUNIT,"/home/app/file");
        reqParams.put(ProtocolConstant.F_TCODE,"Abacus.Freezing.batchUnfreezeMoney");
        return ProtocolbufPackUtil.pack(reqParams);
    }

    @Test
    public void testFileMetaPack(){
        IProtocol.Request request = buildFileMedaRequest();
        System.out.println(request);
    }

    @Test
    public void testFileMetaValidSign(){
        boolean valid = ProtocolbufPackUtil.validSign(buildFileMedaRequest());
        System.out.println(valid);
    }

}
