package com.ouxuxi.service.impl;

import com.ouxuxi.dao.UserDao;
import com.ouxuxi.entity.User;
import com.ouxuxi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public int updateUser(User user)throws Exception {
        return userDao.updateUser(user);
    }

    @Override
    public int updateUserByAdmin(User user)throws Exception {
        return userDao.updateUserByAdmin(user);
    }

    @Override
    public int updateUserByTea(User user)throws Exception {
        return userDao.updateUserByTea(user);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.queryAllUser();
    }

    @Override
    public User getAdmin(User user) {
        return userDao.getAdmin(user);
    }
}
