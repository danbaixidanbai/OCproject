package com.ouxuxi.service.impl;

import com.ouxuxi.dao.UserDao;
import com.ouxuxi.entity.User;
import com.ouxuxi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User getUserById(long userId) {
        return userDao.queryUserById(userId);
    }

    @Override
    public User getByUsernameAndPassword(User user) {
        return userDao.getByUsernameAndPassword(user);
    }

    @Override
    @Transactional
    public int addUser(User user) throws Exception {
        return userDao.addUser(user);
    }

    @Override
    public User getUserByName(String userLoginName) {
        return userDao.queryUserByName(userLoginName);
    }
}
