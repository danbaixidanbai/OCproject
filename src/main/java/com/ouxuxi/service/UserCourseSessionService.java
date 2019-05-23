package com.ouxuxi.service;

import com.ouxuxi.entity.CourseSession;

public interface UserCourseSessionService {
    public CourseSession getCourseSession(long courseSessionId,long userId);
}
