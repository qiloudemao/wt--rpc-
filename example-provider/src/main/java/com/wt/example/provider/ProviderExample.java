package com.wt.example.provider;

import com.wt.wtrpc.bootstrap.ProviderBootstrap;
import com.wt.wtrpc.model.ServiceMetaInfo;
import com.wt.example.common.service.UserService;
import com.wt.wtrpc.RpcApplication;
import com.wt.wtrpc.config.RegistryConfig;
import com.wt.wtrpc.config.RpcConfig;
import com.wt.wtrpc.model.ServiceRegisterInfo;
import com.wt.wtrpc.registry.LocalRegistry;
import com.wt.wtrpc.registry.Registry;
import com.wt.wtrpc.registry.RegistryFactory;
import com.wt.wtrpc.server.tcp.VertxTcpServer;

import java.util.ArrayList;
import java.util.List;

public class ProviderExample {
    public static void main(String[] args) {
        //要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInforList = new ArrayList<>();
        ServiceRegisterInfo serviceRegisterInfo = new
                ServiceRegisterInfo(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInforList.add(serviceRegisterInfo);

        //服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInforList);
    }
}
