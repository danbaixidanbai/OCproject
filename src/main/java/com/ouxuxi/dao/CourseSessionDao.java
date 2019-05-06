package com.ouxuxi.dao;

import com.ouxuxi.entity.CourseSession;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseSessionDao {
    List<CourseSession> queryCourseSessionByCourseId(long courseId);
    List<CourseSession> queryCourseSessionByParent(int parent);
    List<CourseSession> queryCourseSessionByCondition(@Param("courseSession")CourseSession courseSession);
    CourseSession queryCourseSessionBySessionId(long courseSessionId);
    int updateCourseSession(@Param("courseSession") CourseSession courseSession);
    int addCourseSessison(@Param("courseSession")CourseSession courseSession);
    int delCourseSessionBySessionId(long courseSessionId);
    int delCourseSessionByCourseId(long courseId);
}
