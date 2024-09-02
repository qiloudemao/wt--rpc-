package com.wt.wtrpc.serializer;

import com.wt.wtrpc.spi.SpiLoader;


public class SerializerFactory {

    static {
        SpiLoader.load(Serializer.class);
    }
    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER=new JdkSerializer();
    /**
     * 获取实例
     */
    public static Serializer getInstance(String key){
        return SpiLoader.getInstance(Serializer.class,key);
    }
}
