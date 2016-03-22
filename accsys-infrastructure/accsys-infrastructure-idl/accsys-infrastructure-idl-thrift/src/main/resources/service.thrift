

namespace java com.sand.accsys.infrastructure.idl.thrift.service
namespace cpp  service_idl
namespace php  service_idl
namespace perl service_idl

include "struct.thrift"

exception ServiceException{
    1:string what,
    2:string why
}

exception MessageBatchException{
    1:string what,
    2:string why,
    3:string who
}

exception FileMateBatchException{
    1:string what,
    2:string why,
    3:string who
}

service ProductService{
    struct.ServiceResponse          doInService(1:struct.ServiceRequest request)            throws(1:ServiceException e),
    struct.MessageBatchResponse     doMessageBatch(1:struct.MessageBatchRequest request)    throws(1:MessageBatchException e),
    struct.FileMateBatchResponse    doFileMateBatch(1:struct.FileMateBatchRequest request)  throws(1:FileMateBatchException e)
}