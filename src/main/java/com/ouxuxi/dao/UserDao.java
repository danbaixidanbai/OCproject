package com.ouxuxi.dao;

import com.ouxuxi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    User queryUserById(long userId);
    User getByUsernameAndPassword(@Param("user") User User);
    int  addUser(@Param("user")User user);
    User queryUserByName(String userLoginName);
    int  updateUser(@Param("user")User user);
    int  updateUserByAdmin(@Param("user")User user);
    int  updateUserByTea(@Param("user")User user);
    User getAdmin(@Param("user")User user);
    List<User> queryAllUser();
}
