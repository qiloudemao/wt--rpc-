package com.wt.example.consemer;

import com.wt.example.common.model.User;
import com.wt.example.common.service.UserService;
import com.wt.wtrpc.proxy.ServiceProxyFactory;

public class EasyConsumerExample {
    public static void main(String[] args) {

        //todo 需要获取UserService的实现类对象
//        UserService userService=new UserServiceProxy();
        //动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user=new User();
        user.setName("wtt");
        User newUser=userService.getUser(user);
        if (newUser!=null)
            System.out.println(newUser.getName());
        else System.out.println("user==null");
    }
}
