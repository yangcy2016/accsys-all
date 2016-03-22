/*
* Apache thrift base definition file
*/

namespace java com.sand.accsys.infrastructure.idl.thrift.struct
namespace cpp  struct_idl
namespace php  struct_idl
namespace perl struct_idl

/**base struct/
/**产品数据*/
struct ProductInfo{
    /**产品编码*/
    1:required string productCode,
    /**请求渠道*/
    2:optional string fromInfo,
    /**路由到服务*/
    3:optional string routeInfo
}

struct ResponseInfo{
    /**响应编码*/
    1:required string respCode,
    /**响应信息*/
    2:required string respMsg
}

struct ServiceRequest{
    1:required ProductInfo productInfo,
    2:required string     data
}

struct ServiceResponse{
    1:required ProductInfo  productInfo,
    2:required ResponseInfo responseInfo,
    3:required string      data
}

struct MessageBatchRequest{
    1:required ProductInfo productInfo,
    2:required string            batchNo,
    3:required string            messageHeader,
    4:required list<string>     messageBody
}

struct MessageBatchResponse{
    1:required ProductInfo  productInfo,
    2:required ResponseInfo responeInfo,
    3:required string      batchNo
}

struct FileMate{
    /**文件名*/
    1:required string fileName,
    /**文件单元*/
    2:required string fileUnit
}

struct FileMateBatchRequest{
    1:required ProductInfo productInfo,
    2:required FileMate    fileMate,
    3:required string     batchNo
}

struct FileMateBatchResponse{
    1:required ProductInfo  productInfo,
    2:required ResponseInfo responseInfo,
    3:required string      batchNo
}





