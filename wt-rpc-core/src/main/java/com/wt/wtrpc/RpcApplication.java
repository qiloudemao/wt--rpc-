package com.wt.wtrpc;

import com.wt.wtrpc.config.RegistryConfig;
import com.wt.wtrpc.config.RpcConfig;
import com.wt.wtrpc.constant.RpcConstant;
import com.wt.wtrpc.registry.Registry;
import com.wt.wtrpc.registry.RegistryFacatory;
import com.wt.wtrpc.utils.ConfigUtils;
import io.netty.handler.codec.http2.Http2Exception;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcApplication {
    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化，支持传入自定义配置
     *
     * @param newRpcConfig
     */

    public static void init(RpcConfig newRpcConfig){
        rpcConfig=newRpcConfig;
        log.info("rpc init, config={}",newRpcConfig.toString());
        //注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFacatory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init,config={}",registryConfig);
        //创建并注册
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }

    /**
     * 初始化
     */
    public static void init(){
        RpcConfig newRpcConfig;
        try {
            newRpcConfig= ConfigUtils.loadConfig(RpcConfig.class,
                    RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            //配置加载失败，使用默认值
           newRpcConfig= new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 获取配置
     *
     * @return
     */

    public static RpcConfig getRpcConfig(){
        if (rpcConfig==null){
            synchronized (RpcApplication.class){
                if(rpcConfig==null){
                    init();
                }
            }
        }
        return rpcConfig;
    }

}
