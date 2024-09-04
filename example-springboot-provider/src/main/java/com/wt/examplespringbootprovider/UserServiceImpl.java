package com.wt.examplespringbootprovider;

import com.wt.example.common.model.User;
import com.wt.example.common.service.UserService;
import com.wt.wtrpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

@Service
@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名： "+user.getName());
        return user;
    }
}
