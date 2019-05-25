package com.ouxuxi.dao;

import com.ouxuxi.entity.UserCollections;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface UserCollectionsDao {

    public UserCollections getById(long collectionsId);
    public List<UserCollections> getAll(@Param("usercollections") UserCollections usercollections);
    public int addUserCollections(@Param("usercollections") UserCollections usercollections);
    public int delUserCollections(@Param("userId")long userId,@Param("courseId")long courseId);
}
