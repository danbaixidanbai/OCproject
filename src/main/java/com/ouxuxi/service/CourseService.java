package com.ouxuxi.service;

import com.ouxuxi.entity.Course;

import java.util.List;

public interface CourseService {
    public List<Course> getCourseByCondition(Course course,int pagetIndex,int pageSize);
    public int getCourseCountByCondition(Course course);
}
