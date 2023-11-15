package com.bcb.service.impl;

import com.bcb.mapper.UserMapper;
import com.bcb.pojo.User;
import com.bcb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    public User login(User user)
    {
        return userMapper.login(user);
    }

    @Override
    public User register(User user) {

        return userMapper.register(user);
    }
    public void insert(User user)
    {
        userMapper.insert(user);
    }

    /*@Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }*/
}
