package com.ouxuxi.service;

import com.ouxuxi.entity.Course;

import java.util.List;

public interface CourseService {
    public List<Course> getCourseByCondition(Course course,int pagetIndex,int pageSize);
    public int getCourseCountByCondition(Course course);
    public int updateCourse(Course course);
    public Course getCourseByCourseId(long courseId);
    public int addCourse(Course course);
}
