package com.ouxuxi.dao;

import com.ouxuxi.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    User queryUserById(long userId);
    User getByUsernameAndPassword(@Param("user") User User);
    int  addUser(@Param("user")User user);
    User queryUserByName(String userLoginName);
}
