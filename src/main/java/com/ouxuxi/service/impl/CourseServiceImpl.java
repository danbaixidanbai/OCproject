package com.ouxuxi.service.impl;

import com.ouxuxi.dao.CourseDao;
import com.ouxuxi.entity.Course;
import com.ouxuxi.service.CourseService;
import com.ouxuxi.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseDao courseDao;

    @Override
    public List<Course> getCourseByCondition(Course course,int pagetIndex,int pageSize) {
        int pageNum= PageCalculator.calculateRowIndex(pagetIndex,pageSize);
        return courseDao.queryCourseList(course,pageNum,pageSize);
    }

    @Override
    public int getCourseCountByCondition(Course course) {
        return courseDao.queryCourseCount(course);
    }

    @Override
    public int updateCourse(Course course) {
        return courseDao.updateCourse(course);
    }

    @Override
    public Course getCourseByCourseId(long courseId) {
        return courseDao.queryCourseById(courseId);
    }

    @Override
    public int addCourse(Course course) {
        return courseDao.addCourse(course);
    }
}
