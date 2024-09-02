package com.wt.wtrpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地注册中心
 */
public class LocalRegistry {
    /**
     * 注册信息存储
     */
    private static final Map<String,Class<?>>map=new ConcurrentHashMap<>();

    /***
     * 注册服务
     * @param servicenName
     * @param implClass
     */
    public static void register(String servicenName,Class<?>implClass){
        map.put(servicenName,implClass);
    }

    /**
     * 获取服务
     * @param serviceName
     * @return
     */
    public static Class<?> get(String serviceName){
        return map.get(serviceName);
    }

    /**
     * 删除服务
     * @param serviceName
     */
    public static void remove(String serviceName){
        map.remove(serviceName);
    }
}
