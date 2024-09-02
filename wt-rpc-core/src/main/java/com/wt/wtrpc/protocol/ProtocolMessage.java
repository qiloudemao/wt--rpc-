package com.wt.wtrpc.protocol;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolMessage <T>{
    /**
     * 消息头
     */
    private Header header;
    /**
     * 消息体（请求或响应对象）
     */
    private T body;

    @Data
    public static class Header{
        /**
         * 魔数，保证安全性
         */
        private byte magic;
        /**
         * 序列化器
         */
        private byte serializer;
        /**
         * 状态
         */
        private byte status;
        /**
         * 请求id
         */
        private long requestId;
        /**
         * 消息体长度
         */
        private int bodyLength;
    }
}
