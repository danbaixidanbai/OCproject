package com.ouxuxi.service;

import com.ouxuxi.entity.CourseSession;
import com.ouxuxi.entity.UserCourseSession;

public interface UserCourseSessionService {
    //获取章视频，并且决定是否添加课程学习人数
    public CourseSession getCourseSession(long courseSessionId,long userId);
    //获取最新学习状态
    public UserCourseSession getLastSession(long courseId, long userId);

}
