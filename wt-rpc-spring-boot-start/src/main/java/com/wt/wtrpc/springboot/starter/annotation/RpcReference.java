package com.wt.wtrpc.springboot.starter.annotation;

import com.wt.wtrpc.constant.RpcConstant;
import com.wt.wtrpc.fault.retry.RetryStrategyKeys;
import com.wt.wtrpc.fault.tolerant.TolerantStrategyKeys;
import com.wt.wtrpc.loadbalancer.LoadBalancerKeys;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务消费者注解 （用于注入服务）
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RpcReference {
    /**
     * 服务接口类
     * @return
     */
    Class<?> interfaceCliass()default void.class;

    /**
     * 版本
     * @return
     */
    String serviceVersion()default RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 负载均衡器
     * @return
     */
    String loadBalancer()default LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     * @return
     */
    String retryStrategy() default RetryStrategyKeys.NO;

    /**
     * 容错策略
     * @return
     */
    String tolerantStrategy()default TolerantStrategyKeys.FAIL_FAST;

    /**
     * 模拟调用
     * @return
     */
    boolean mock() default false;
}
