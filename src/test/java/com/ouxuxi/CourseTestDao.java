package com.ouxuxi;


import com.ouxuxi.dao.CourseDao;
import com.ouxuxi.entity.Course;
import com.ouxuxi.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseTestDao {
    @Resource
    private CourseDao courseDao;

    @Test
    public void addCourse(){
        Course course=new Course();
        course.setCourseName("springboot2.0");
        course.setCourseImage("urltest");
        /*course.setCourseClassify(7);
        course.setCourseClassifyParent(2);*/
        course.setCourseCreateTime(new Date());
        course.setCourseUpdateTime(new Date());
        User user=new User();
        user.setUserId(4);
        course.setUser(user);
        int num=courseDao.addCourse(course);
        System.out.println("num:"+num);
    }
    @Test
    public void updateCourse(){
        Course course=new Course();
        course.setCourseId(1);
        //course.setCourseName("springboot2.0");
        course.setCourseImage("test");
        /*course.setCourseClassify(7);
        course.setCourseClassifyParent(2);*/
        course.setCourseCreateTime(new Date());
        course.setCourseUpdateTime(new Date());
        User user=new User();
        user.setUserId(4);
        course.setUser(user);
        int num=courseDao.updateCourse(course);
        System.out.println("num:"+num);
    }
    @Test
    public void queryCourseByCondition(){
        Course course=new Course();
        System.out.println(course.toString());
        List<Course> list=courseDao.queryCourseList(course,0,10);
        System.out.println("size:"+list.size());
    }
    @Test
    public void queryCourseById(){
        Course course=courseDao.queryCourseById(1);
        System.out.println(course.toString());
    }
}
