package com.wt.example.provider;

import com.wt.example.common.service.UserService;
import com.wt.wtrpc.RpcApplication;
import com.wt.wtrpc.registry.LocalRegistry;
import com.wt.wtrpc.server.HttpServer;
import com.wt.wtrpc.server.VertxHttpServer;

public class EasyProviderExample {

    public static void main(String[] args) {
        //RPC 框架初始化
        RpcApplication.init();

        //注册服务
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);

        //启动web服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
