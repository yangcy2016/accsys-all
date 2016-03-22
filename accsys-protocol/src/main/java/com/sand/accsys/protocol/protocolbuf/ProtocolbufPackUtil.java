package com.sand.accsys.protocol.protocolbuf;

import com.google.common.base.Preconditions;
import com.sand.accsys.common.util.datetime.DateTimeHelper;
import com.sand.accsys.common.util.hash.HashUtils;
import com.sand.accsys.protocol.Packer;
import com.sand.accsys.protocol.ProtocolConstant;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author : huanghy
 * @create : 2016/3/14 0014 下午 4:09
 * @since : ${VERSION}
 */
public class ProtocolbufPackUtil {

    private static final Packer<IProtocol.Request,IProtocol.Response> packer = new ProtocolbufPacker();

    public static IProtocol.Request pack(Map<String,Object> requestParams){
        Preconditions.checkNotNull(requestParams,"request map must not be null");
        if(!requestParams.containsKey(ProtocolConstant.VERSION)){
            requestParams.put(ProtocolConstant.VERSION,"1.0.0");
        }
        if(!requestParams.containsKey(ProtocolConstant.CMARK)){
            requestParams.put(ProtocolConstant.CMARK,"00000000");
        }

        if(!requestParams.containsKey(ProtocolConstant.CTYPE)){
            requestParams.put(ProtocolConstant.CTYPE,IProtocol.Header.Ctype.TCP);
        }else {
            String ctypeStr = (String) requestParams.get(ProtocolConstant.CTYPE);
            IProtocol.Header.Ctype ctype =  IProtocol.Header.Ctype.valueOf(Integer.parseInt(ctypeStr));
            requestParams.put(ProtocolConstant.CTYPE,ctype);
        }
        if(!requestParams.containsKey(ProtocolConstant.DTIME)){
            requestParams.put(ProtocolConstant.DTIME, DateTimeHelper.nowDateTimeString("yyyyMMddHHmmss"));
        }
        Integer bimpl = Integer.parseInt((String) requestParams.get(ProtocolConstant.BIMPL));
        Preconditions.checkNotNull(bimpl,"bimpl must not be null");
        IProtocol.Header.Bimpl bimpl1 =  IProtocol.Header.Bimpl.valueOf(bimpl);
        switch (bimpl1){
            case RPC:
                requestParams.put(ProtocolConstant.BIMPL,IProtocol.Header.Bimpl.RPC);//convert to enum type
                checkRpcBody(requestParams);
                break;
            case MessageBatch:
                requestParams.put(ProtocolConstant.BIMPL,IProtocol.Header.Bimpl.MessageBatch);//convert to enum type
                checkMessagBatchBody(requestParams);
                break;
            case FileMeta:
                requestParams.put(ProtocolConstant.BIMPL,IProtocol.Header.Bimpl.FileMeta);//convert to enum type
                checkFileMeta(requestParams);
                break;
            default:
                break;
        }
        IProtocol.Request request = packer.pack(requestParams);
        return signPack(request);
    }

    public static IProtocol.Request signPack(IProtocol.Request request){
        IProtocol.Header header = request.getHeader();
        byte[] bytes = null;
        switch (header.getBimpl()){
            case RPC:
                bytes = request.getRBody().toByteArray();
                break;
            case MessageBatch:
                bytes = request.getMBody().toByteArray();
                break;
            case FileMeta:
                bytes = request.getFBody().toByteArray();
                break;
            default:
                break;
        }
        header = header.toBuilder()
                .setBlength(StringUtils.leftPad(Integer.toString(bytes.length), 8, '0'))
                .setCsum(HashUtils.hash(bytes)).build();
        return request.toBuilder().setHeader(header).build();
    }

    public static boolean validSign(IProtocol.Request request){
        IProtocol.Header header = request.getHeader();
        byte[] bytes = null;
        switch (header.getBimpl()){
            case RPC:
               bytes = request.getRBody().toByteArray();
                break;
            case MessageBatch:
                bytes = request.getMBody().toByteArray();
                break;
            case FileMeta:
                bytes = request.getFBody().toByteArray();
                break;
            default:
                break;
        }
        String blength = StringUtils.leftPad(Integer.toString(bytes.length),8,'0');
        String csum    = HashUtils.hash(bytes);
        return blength.equals(header.getBlength())&&csum.equals(header.getCsum());
    }

    private static void checkRpcBody(Map<String,Object> requestParams){
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.R_DISPATCH),"rpc dispatch must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.R_INVOKER),"rpc invoker must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.R_TCODE),"rpc tcode must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.R_DATA),"rpc data must not be null");
    }
    private static void checkMessagBatchBody(Map<String,Object> requestParams){
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.M_DISPATCH),"msg dispatch must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.M_INVOKER),"msg invoker must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.M_TCODE),"msg tcode must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.M_DATA),"msg data must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.M_HEAD),"msg head must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.M_BATCHNO),"msg batchno must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.M_MESSAGES) instanceof List,"msg messages must be a list");
    }
    private static void checkFileMeta(Map<String,Object> requestParams){
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.F_DISPATCH),"file dispatch must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.F_INVOKER),"file invoker must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.F_TCODE),"file tcode must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.F_DATA),"file data must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.F_HEAD),"file head must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.F_BATCHNO),"file batchno must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.F_FILENAME),"file filename must not be null");
        Preconditions.checkNotNull(requestParams.get(ProtocolConstant.F_FILEUNIT),"file fileunit must not be null");
    }

    public static Map<String,Object> unpack(IProtocol.Response response){
        return null;
    }

    public static String getDispatch(IProtocol.Request request) {
        switch (request.getHeader().getBimpl()){
            case RPC:
                return request.getRBody().getPayhead().getDispath();
            case MessageBatch:
                return request.getMBody().getPayhead().getDispath();
            case FileMeta:
                return request.getFBody().getPayhead().getDispath();
            default:
                return null;
        }
    }

    public static IProtocol.Response copyFromPackAndSign(IProtocol.Request request, String respCode, String respMsg, String data) {
        IProtocol.Response response =  packer.copyFromPack(request,respCode,respMsg,data);
        return signPack(response);
    }
    public static IProtocol.Response signPack(IProtocol.Response response){
        IProtocol.Header header = response.getHeader();
        byte[] bytes = null;
        switch (header.getBimpl()){
            case RPC:
                bytes = response.getRBody().toByteArray();
                break;
            case MessageBatch:
                bytes = response.getMBody().toByteArray();
                break;
            case FileMeta:
                bytes = response.getFBody().toByteArray();
                break;
            default:
                break;
        }
        header = header.toBuilder()
                .setBlength(StringUtils.leftPad(Integer.toString(bytes.length), 8, '0'))
                .setCsum(HashUtils.hash(bytes)).build();
        return response.toBuilder().setHeader(header).build();
    }

    public static boolean validSign(IProtocol.Response Response){
        IProtocol.Header header = Response.getHeader();
        byte[] bytes = null;
        switch (header.getBimpl()){
            case RPC:
                bytes = Response.getRBody().toByteArray();
                break;
            case MessageBatch:
                bytes = Response.getMBody().toByteArray();
                break;
            case FileMeta:
                bytes = Response.getFBody().toByteArray();
                break;
            default:
                break;
        }
        String blength = StringUtils.leftPad(Integer.toString(bytes.length),8,'0');
        String csum    = HashUtils.hash(bytes);
        return blength.equals(header.getBlength())&&csum.equals(header.getCsum());
    }
}
