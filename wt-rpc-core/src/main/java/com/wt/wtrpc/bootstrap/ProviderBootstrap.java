package com.wt.wtrpc.bootstrap;

import com.wt.wtrpc.RpcApplication;
import com.wt.wtrpc.config.RegistryConfig;
import com.wt.wtrpc.config.RpcConfig;
import com.wt.wtrpc.model.ServiceMetaInfo;
import com.wt.wtrpc.model.ServiceRegisterInfo;
import com.wt.wtrpc.registry.LocalRegistry;
import com.wt.wtrpc.registry.Registry;
import com.wt.wtrpc.registry.RegistryFactory;
import com.wt.wtrpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * 服务提供者初始化
 */
public class ProviderBootstrap {
    /**
     * 初始化
     * @param serviceRegisterInfoList
     */
    public static void init(List<ServiceRegisterInfo<?>>serviceRegisterInfoList){
        //RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
        //全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        //注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo:serviceRegisterInfoList){
            String serviceName = serviceRegisterInfo.getServiceName();
            //本地注册
            LocalRegistry.register(serviceName,serviceRegisterInfo.getImplClass());

            //注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName+" 服务注册失败",e);
            }


            //启动服务器
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.doStart(rpcConfig.getServerPort());
        }
    }
}
