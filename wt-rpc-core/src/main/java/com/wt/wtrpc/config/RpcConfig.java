package com.wt.wtrpc.config;

import com.wt.wtrpc.fault.retry.RetryStrategyKeys;
import com.wt.wtrpc.fault.tolerant.TolerantStrategyKeys;
import com.wt.wtrpc.loadbalancer.LoadBalancerKeys;
import com.wt.wtrpc.serializer.SerializerKeys;
import lombok.Data;

@Data
public class RpcConfig {
    /**
     * 名称
     */
    private String name="wt-rpc";
    /**
     * 版本号
     */
    private String version="1.0";
    /**
     * 服务器主机名
     */
    private String serverHost="localhost";
    /**
     * 服务器端口号
     */
    private  Integer serverPort=8080;
    /**
     * 序列化器
     */
    private String serializer= SerializerKeys.JDK;
    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig=new RegistryConfig();
    /**
     * 负载均衡器
     */
    private String LoadBalancer= LoadBalancerKeys.ROUND_ROBIN;
    /**
     * 重试策略
     */
    private String retryStrategy= RetryStrategyKeys.NO;
    /**
     * 容错策略
     */
    private String tolerantStrategy= TolerantStrategyKeys.FAIL_FAST;
    /**
     * 模拟调用
     *
     */
    private boolean mock=false;

}
