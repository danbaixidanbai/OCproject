package com.ouxuxi.service;

import com.ouxuxi.dto.CourseSessionDto;
import com.ouxuxi.entity.CourseSession;

import java.util.List;

public interface CourseSessionService {
    List<CourseSession> getCourseSessionByCourseId(long courseId);
    List<CourseSession> getCourseSessionByParent(int parent);
    CourseSession getCourseSessionBySessionId(long courseSessionId);
    int updateCourseSession(CourseSession courseSession);
    int addCourseSessison(CourseSession courseSession);
    int delCourseSessionBySessionId(long courseSessionId);
    int delCourseSessionByCourseId(long courseId);

    List<CourseSessionDto> getCourseSessionDto(long courseId);
}
