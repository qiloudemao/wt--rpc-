package com.wt.example.consemer;

import com.wt.example.common.model.User;
import com.wt.example.common.service.UserService;
import com.wt.wtrpc.bootstrap.ConsumerBootstrap;
import com.wt.wtrpc.config.RpcConfig;
import com.wt.wtrpc.proxy.ServiceProxyFactory;
import com.wt.wtrpc.utils.ConfigUtils;

public class ConsumerExample {
    public static void main(String[] args) {
        //服务提供者初始化
        ConsumerBootstrap.init();

        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("123456");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser !=null){
            System.out.println(newUser.getName());
        }
        else System.out.println("user==null");
        long number = userService.getNumber();
        System.out.println(number);
    }
}
