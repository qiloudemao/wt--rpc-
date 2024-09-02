package com.wt.example.consemer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.wt.example.common.model.User;
import com.wt.example.common.service.UserService;
import com.wt.wtrpc.RpcApplication;
import com.wt.wtrpc.model.RpcRequest;
import com.wt.wtrpc.model.RpcResponse;
import com.wt.wtrpc.serializer.JdkSerializer;
import com.wt.wtrpc.serializer.Serializer;
import com.wt.wtrpc.serializer.SerializerFactory;

import java.io.IOException;

public class UserServiceProxy implements UserService{
    @Override
    public User getUser(User user) {
        //指定序列化器
        Serializer serializer = new JdkSerializer();
//        Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        //发请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();

        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化（字节数组 => Java 对象）
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);

                return (User) rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
