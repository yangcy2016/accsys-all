package com.sand.accsys.product.server.protocol.thrift;

import com.sand.accsys.infrastructure.idl.thrift.service.ProductService;
import com.sand.accsys.product.server.protocol.ProductServer;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.*;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author : huanghy
 * @create : 2016/3/21 0021 下午 3:13
 * @since : ${VERSION}
 */
public class ThriftProductServer extends ProductServer {

    /** 轮询线程的数量 */
    private int selectorThreads  = 2;

    /** 工作线程数量*/
    private int workThreads = 10;

    /** 单位线程队列的长度 */
    private int acceptQueueSizePerThread = 100;

    /** 选择策略*/
    private String policy = "FAST";

    /** 业务线程数量*/
    private int fixedThreadPoolSize = 10;

    TServer server;

    TNonblockingServerTransport serverTransport;

    public ThriftProductServer() {
    }

    public ThriftProductServer(int port) {
        this.port = port;
    }

    public ThriftProductServer(int selectorThreads, int workThreads, int acceptQueueSizePerThread, String policy, int fixedThreadPoolSize, int port) {
        this.selectorThreads = selectorThreads;
        this.workThreads = workThreads;
        this.acceptQueueSizePerThread = acceptQueueSizePerThread;
        this.policy = policy;
        this.fixedThreadPoolSize = fixedThreadPoolSize;
        this.port = port;
    }

    @Override
    public void start() {
        try {
            serverTransport = new TNonblockingServerSocket(port);
            TTransportFactory transportFactory = new TFramedTransport.Factory();
            TProtocolFactory  protocolFactory  = new TCompactProtocol.Factory();
            TProcessor        processor        = new ProductService.Processor(new ThriftProductServiceImpl());
            TThreadedSelectorServer.Args.AcceptPolicy acceptPolicy = null;
            if("FAIR".equals(policy)){
                acceptPolicy =  TThreadedSelectorServer.Args.AcceptPolicy.FAIR_ACCEPT;
            }else if("FAST".equals(policy)){
                acceptPolicy =  TThreadedSelectorServer.Args.AcceptPolicy.FAST_ACCEPT;
            }
            TThreadedSelectorServer.Args serverArgs =  new TThreadedSelectorServer
                    .Args(serverTransport)
                    .protocolFactory(protocolFactory)
                    .transportFactory(transportFactory)
                    .processor(processor)
                    .selectorThreads(selectorThreads)
                    .acceptQueueSizePerThread(acceptQueueSizePerThread)
                    .workerThreads(workThreads)
                    .executorService(Executors.newFixedThreadPool(fixedThreadPoolSize))
                    .acceptPolicy(acceptPolicy)
                    .stopTimeoutVal(60)
                    .stopTimeoutUnit(TimeUnit.SECONDS);
            server = new TThreadedSelectorServer(serverArgs);
            logger.info("start product service server at {},success",port);
            this.active = true;
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
            logger.error("product server error: cause by {}",e.getCause());
            stop();
        }
    }

    @Override
    public void stop() {
        if(serverTransport!=null){
            serverTransport.close();
        }
        if(server!=null&&server.isServing()){
            server.stop();
        }
        this.active = false;
        logger.info("stop product server success");
    }

    public static void main(String[] args) {
        ProductServer server = new ThriftProductServer();
        server.start();
    }
}
