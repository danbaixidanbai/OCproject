package com.ouxuxi.dao;

import com.ouxuxi.entity.UserFollow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFollowDao {
    public List<UserFollow> getAll(@Param("userFollow") UserFollow userFollow);
    public int addUserFollow(@Param("userFollow") UserFollow userFollow);
    public int deleteUserFollow(@Param("userId")long userId,@Param("followId")long followId);
}
