package com.it.shiro.service;

import com.it.shiro.dao.UserDao;
import com.it.shiro.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceIm implements UserService {
    @Autowired
    private UserDao userDao;
    public User findUserByName(String userName){
        List<User> users=userDao.findAll();
        for (User u:users){
            if (u.getUsername().equals(userName)){
                return u;
            }
        }

        return  null;
    }

    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }
}
