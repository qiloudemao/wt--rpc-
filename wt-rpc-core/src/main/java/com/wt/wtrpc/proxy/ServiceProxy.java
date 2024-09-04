package com.wt.wtrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.wt.wtrpc.fault.retry.RetryStrategy;
import com.wt.wtrpc.fault.retry.RetryStrategyFactory;
import com.wt.wtrpc.loadbalancer.LoadBalancer;
import com.wt.wtrpc.loadbalancer.LoadBalancerFactory;
import com.wt.wtrpc.model.ServiceMetaInfo;
import com.wt.wtrpc.RpcApplication;
import com.wt.wtrpc.config.RpcConfig;
import com.wt.wtrpc.constant.RpcConstant;
import com.wt.wtrpc.model.RpcRequest;
import com.wt.wtrpc.model.RpcResponse;
import com.wt.wtrpc.protocol.*;
import com.wt.wtrpc.registry.Registry;
import com.wt.wtrpc.registry.RegistryFacatory;
import com.wt.wtrpc.serializer.Serializer;
import com.wt.wtrpc.serializer.SerializerFactory;
import com.wt.wtrpc.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 服务代理
 */
public class ServiceProxy implements InvocationHandler {
    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            //序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry =
                    RegistryFacatory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            //负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            //将调用方法名（请求路径）作为负载均衡参数
            HashMap<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName",rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

            //发送 rpc 请求
//            RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo);
//            return  rpcResponse.getData();
            //发送 rpc 请求
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            RpcResponse rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
            return rpcResponse.getData();
        } catch (IOException e) {
            throw new RuntimeException("调用失败");
        }
//        return null;
    }
}
