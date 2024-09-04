package com.wt.examplespringbootconsumer;

import com.wt.example.common.model.User;
import com.wt.example.common.service.UserService;
import com.wt.wtrpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl {
    @RpcReference
    private UserService userService;

    public void test(){
        User user = new User();
        user.setName("wttttt");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }
}
