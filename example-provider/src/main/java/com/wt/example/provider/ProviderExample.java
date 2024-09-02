package com.wt.example.provider;

import com.wt.wtrpc.model.ServiceMetaInfo;
import com.wt.example.common.service.UserService;
import com.wt.wtrpc.RpcApplication;
import com.wt.wtrpc.config.RegistryConfig;
import com.wt.wtrpc.config.RpcConfig;
import com.wt.wtrpc.registry.LocalRegistry;
import com.wt.wtrpc.registry.Registry;
import com.wt.wtrpc.registry.RegistryFacatory;
import com.wt.wtrpc.server.VertxHttpServer;

public class ProviderExample {
    public static void main(String[] args) {
        //RPC框架初始
        RpcApplication.init();
        //注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        //注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();

        Registry registry = RegistryFacatory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();

        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceAddress(rpcConfig.getServerHost()+":"+rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
            System.out.println("服务注册成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //启动web服务
        VertxHttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
