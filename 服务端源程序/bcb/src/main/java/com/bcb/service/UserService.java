package com.bcb.service;

import com.bcb.pojo.User;

import java.util.List;

public interface UserService {
    public User login(User user);
    public User register(User user);
    public void insert(User user);
    /*public void delete(Integer id);*/
}
