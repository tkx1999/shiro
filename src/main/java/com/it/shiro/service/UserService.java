package com.it.shiro.service;

import com.it.shiro.pojo.User;


public interface UserService {
    public User findUserByName(String userName);

    void saveUser(User user);
}
