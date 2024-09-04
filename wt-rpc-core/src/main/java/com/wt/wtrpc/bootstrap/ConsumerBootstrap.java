package com.wt.wtrpc.bootstrap;

import com.wt.wtrpc.RpcApplication;

/**
 * 服务消费者启动类（初始化）
 */
public class ConsumerBootstrap {
    /**
     * 初始化
     */
    public static void init(){
        //RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
    }
}
