package com.sand.accsys.protocol;

/**
 * @author : huanghy
 * @create : 2016/3/14 0014 下午 4:28
 * @since : ${VERSION}
 */
public interface ProtocolConstant {
    /*****************head****************/
    static final String VERSION         = "accsys.protocol.version";
    static final String CTYPE           = "accsys.protocol.ctype";
    static final String DTIME           = "accsys.protocol.dtime";
    static final String BIMPL           = "accsys.protocol.bimpl";
    static final String CMARK           = "accsys.protocol.cmark";
    static final String BLENGTH         = "accsys.protocol.blength";
    static final String CSUM            = "accsys.protocol.csum";

    static final String RESP_CODE       = "accsys.protocol.body.respCode";
    static final String RESP_MSG        = "accsys.protocol.body.respMsg";

    /****************rpc body*********************/
    static final String R_DISPATCH       = "accsys.protocol.body.rpc.dispatch";
    static final String R_INVOKER        = "accsys.protocol.body.rpc.invoker";
    static final String R_TCODE          = "accsys.protocol.body.rpc.tcode";
    static final String R_DATA           = "accsys.protocol.body.rpc.data";

    /*************message batch*******************/
    static final String M_DISPATCH       = "accsys.protocol.body.msg.dispatch";
    static final String M_INVOKER        = "accsys.protocol.body.msg.invoker";
    static final String M_TCODE          = "accsys.protocol.body.msg.tcode";
    static final String M_DATA           = "accsys.protocol.body.msg.data";
    static final String M_BATCHNO        = "accsys.protocol.body.msg.batchno";
    static final String M_HEAD           = "accsys.protocol.body.msg.head";
    static final String M_MESSAGES       = "accsys.protocol.body.msg.messages";//json array
    /*************file batch*********************/
    static final String F_DISPATCH       = "accsys.protocol.body.file.dispatch";
    static final String F_INVOKER        = "accsys.protocol.body.file.invoker";
    static final String F_TCODE          = "accsys.protocol.body.file.tcode";
    static final String F_BATCHNO        = "accsys.protocol.body.file.batchno";
    static final String F_HEAD           = "accsys.protocol.body.file.head";
    static final String F_FILENAME       = "accsys.protocol.body.file.filename";
    static final String F_FILEUNIT       = "accsys.protocol.body.file.fileunit";
    static final String F_DATA           = "accsys.protocol.body.file.data";

}
