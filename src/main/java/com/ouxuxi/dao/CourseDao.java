package com.ouxuxi.dao;

import com.ouxuxi.entity.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseDao {

    int addCourse(@Param("course") Course course);
    int updateCourse(@Param("course") Course course);
    int deleteCourse(long courseId);
    Course queryCourseById(long courseId);
    List<Course> queryCourseList(@Param("course") Course course,@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);
    Integer queryCourseCount(@Param("course") Course course);

}
