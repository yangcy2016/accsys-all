package com.sand.accsys.protocol.protocolbuf;

import com.sand.accsys.common.util.datetime.DateTimeHelper;
import com.sand.accsys.protocol.Packer;
import com.sand.accsys.protocol.ProtocolConstant;

import java.util.List;
import java.util.Map;

/**
 * @author : huanghy
 * @create : 2016/3/14 0014 下午 4:10
 * @since : ${VERSION}
 */
class ProtocolbufPacker implements Packer<IProtocol.Request,IProtocol.Response>{

    public IProtocol.Request pack(Map<String, Object> target) {
        IProtocol.Header header = IProtocol.Header.newBuilder()
                .setBimpl((IProtocol.Header.Bimpl) target.get(ProtocolConstant.BIMPL))
                .setCmark((String) target.get(ProtocolConstant.CMARK))
                .setCtype((IProtocol.Header.Ctype) target.get(ProtocolConstant.CTYPE))
                .setDtime((String) target.get(ProtocolConstant.DTIME))
                .setVersion((String) target.get(ProtocolConstant.VERSION))
                .build();
        IProtocol.Header.Bimpl bimpl = (IProtocol.Header.Bimpl) target.get(ProtocolConstant.BIMPL);
        IProtocol.Request.Builder builder = IProtocol.Request.newBuilder();
        builder.setHeader(header);
        switch (bimpl){
            case RPC:
                builder.setRBody(IProtocol.RpcBody.newBuilder()
                .setPayhead(IProtocol.RpcBody.Payhead.newBuilder()
                        .setDispath((String) target.get(ProtocolConstant.R_DISPATCH))
                        .setInvoker((String) target.get(ProtocolConstant.R_INVOKER))
                        .setTcode((String) target.get(ProtocolConstant.R_TCODE))
                        .build())
                .setPayload(IProtocol.RpcBody.Payload.newBuilder().setData((String) target.get(ProtocolConstant.R_DATA)))
                .build());
                break;
            case MessageBatch:
                builder.setMBody(IProtocol.MessageBatchBody.newBuilder()
                        .setPayhead(IProtocol.MessageBatchBody.Payhead.newBuilder()
                                .setDispath((String) target.get(ProtocolConstant.M_DISPATCH))
                                .setInvoker((String) target.get(ProtocolConstant.M_INVOKER))
                                .setTcode((String) target.get(ProtocolConstant.M_TCODE))
                                .build())
                        .setPayload(IProtocol.MessageBatchBody.Payload.newBuilder()
                                .setMbatchNo((String) target.get(ProtocolConstant.M_BATCHNO))
                                .setMhead((String) target.get(ProtocolConstant.M_HEAD))
                                .addAllMessages((List)target.get(ProtocolConstant.M_MESSAGES))
                                .build())
                        .build());
                break;
            case FileMeta:
                builder.setFBody(IProtocol.FileMetaBody.newBuilder()
                .setPayhead(IProtocol.FileMetaBody.Payhead.newBuilder()
                        .setDispath((String) target.get(ProtocolConstant.F_DISPATCH))
                        .setInvoker((String) target.get(ProtocolConstant.F_INVOKER))
                        .setTcode((String) target.get(ProtocolConstant.F_TCODE))
                        .build())
                .setPayload(IProtocol.FileMetaBody.Payload.newBuilder()
                        .setFbatchNo((String) target.get(ProtocolConstant.F_BATCHNO))
                        .setFhead((String) target.get(ProtocolConstant.F_HEAD))
                        .setFmeta(IProtocol.FileMetaBody.FileMeta.newBuilder()
                                .setFileName((String) target.get(ProtocolConstant.F_FILENAME))
                                .setFileUnit((String) target.get(ProtocolConstant.F_FILEUNIT))))
                .build());
                break;
        }
        return builder.build();
    }



    public Map<String, Object> unpack(IProtocol.Response response) {
        return null;
    }

    public IProtocol.Response copyFromPack(IProtocol.Request request, String respCode, String respMsg, String data) {
        IProtocol.Response.Builder builder = IProtocol.Response.newBuilder();
        switch (request.getHeader().getBimpl()){
            case RPC:
            builder.setRBody(
                    IProtocol.RpcBody.newBuilder()
                            .setPayhead(
                                    IProtocol.RpcBody.Payhead.newBuilder().setRespCode(respCode).setRespMsg(respMsg).build())
                            .setPayload(
                                    IProtocol.RpcBody.Payload.newBuilder().setData(data).build()
                            ));
                break;
            case MessageBatch:
                builder.setMBody(IProtocol.MessageBatchBody.newBuilder()
                        .setPayhead(
                                IProtocol.MessageBatchBody.Payhead.newBuilder().setRespCode(respCode).setRespMsg(respMsg).build())
                        .setPayload(
                                IProtocol.MessageBatchBody.Payload.newBuilder().setMbatchNo(request.getMBody().getPayload().getMbatchNo()).build()
                        ));
                break;
            case FileMeta:
                builder.setFBody(IProtocol.FileMetaBody.newBuilder()
                        .setPayhead(
                                IProtocol.FileMetaBody.Payhead.newBuilder().setRespCode(respCode).setRespMsg(respMsg).build())
                        .setPayload(
                                IProtocol.FileMetaBody.Payload.newBuilder().setFbatchNo(request.getFBody().getPayload().getFbatchNo()).build()
                        ));
                break;
            default:
                break;
        }
        IProtocol.Header header = IProtocol.Header.newBuilder()
                .setBimpl(request.getHeader().getBimpl())
                .setCmark(request.getHeader().getCmark())
                .setCtype(request.getHeader().getCtype())
                .setDtime(DateTimeHelper.nowDateTimeString("yyyyMMddHHmmss"))
                .setVersion(request.getHeader().getVersion())
                .build();
        return builder.setHeader(header).build();
    }
}
