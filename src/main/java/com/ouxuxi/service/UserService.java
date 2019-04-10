package com.ouxuxi.service;

import com.ouxuxi.entity.User;

public interface UserService {
    User getUserById(long userId);
    User getByUsernameAndPassword(User user);
    int  addUser(User user) throws Exception;
    User getUserByName(String userLoginName);
    int  updateUser(User user)throws Exception;
    int  updateUserByAdmin(User user)throws Exception;
    int  updateUserByTea(User user)throws Exception;
}
