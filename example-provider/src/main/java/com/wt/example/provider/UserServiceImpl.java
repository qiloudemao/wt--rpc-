package com.wt.example.provider;

import com.wt.example.common.model.User;
import com.wt.example.common.service.UserService;

public class UserServiceImpl implements UserService{
    /**
     * 用户服务实现类
     */
    public User getUser(User user) {
        System.out.println("用户名："+user.getName());
        return user;
    }


}
