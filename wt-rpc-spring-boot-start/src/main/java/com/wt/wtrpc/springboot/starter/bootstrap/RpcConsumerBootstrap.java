package com.wt.wtrpc.springboot.starter.bootstrap;

import com.wt.wtrpc.proxy.ServiceProxyFactory;
import com.wt.wtrpc.serializer.SerializerFactory;
import com.wt.wtrpc.springboot.starter.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * RPC 服务消费者启动
 */
@Slf4j
public class RpcConsumerBootstrap implements BeanPostProcessor {
    /**
     * Bean初始化 后执行，注入服务
     * @param bean
     * @param beanName
     * @return
     */
    public Object postProcessAfterInitialization(Object bean,String beanName){
        Class<?> beanClass = bean.getClass();
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field:declaredFields){
            RpcReference rpcReference = field.getAnnotation(RpcReference.class);
            if (rpcReference!=null){
                //为属性生成代理对象
                Class<?> interfacedClass = rpcReference.interfaceCliass();
                if (interfacedClass==void.class)
                    interfacedClass=field.getType();
                field.setAccessible(true);
                Object proxyObject = ServiceProxyFactory.getProxy(interfacedClass);
                try {
                    field.set(bean,proxyObject);
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("为字段注入代理对象失败",e);
                }
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean,beanName);
    }
}
