package com.ouxuxi.dao;

import com.ouxuxi.entity.UserCourseSession;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCourseSessionDao {
    public UserCourseSession getById(long userCourseSessionId);
    public List<UserCourseSession> getAll(@Param("userCourseSession") UserCourseSession userCourseSession);
    public UserCourseSession queryLatest(@Param("userCourseSession") UserCourseSession userCourseSession);
    public int add(@Param("userCourseSession") UserCourseSession userCourseSession);
    public int update(@Param("userCourseSession") UserCourseSession userCourseSession);
    public int del(long userCourseSessionId);
}
