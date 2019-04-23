package com.ouxuxi;


import com.ouxuxi.dao.CourseClassifyDao;
import com.ouxuxi.entity.CourseClassify;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseClassifyDaoTest {

    @Resource
    private CourseClassifyDao courseClassifyDao;

    @Test
    public void addCourseClassify(){
        CourseClassify courseClassify=new CourseClassify();
        courseClassify.setClassifyName("react");
        courseClassify.setClassifyPriority(100);
        courseClassify.setParent(1);
        courseClassify.setClassifyCreateTime(new Date());
        courseClassify.setClassifyUpdateTime(new Date());
        int num=courseClassifyDao.addCourseClassify(courseClassify);
        System.out.println("num:"+num);
        System.out.println("classifyid:"+courseClassify.getClassifyId());
    }

    @Test
    public void updateCourseClassify(){
        CourseClassify courseClassify=new CourseClassify();
        courseClassify.setClassifyId(7);
        courseClassify.setClassifyName("java");
        courseClassify.setClassifyPriority(100);
        courseClassify.setParent(2);
        //courseClassify.setClassifyCreateTime(new Date());
        courseClassify.setClassifyUpdateTime(new Date());
        int num=courseClassifyDao.updateCourseClassify(courseClassify);
        System.out.println("num:"+num);
        System.out.println("classifyid:"+courseClassify.getClassifyId());
    }

    @Test
    public void queryCourseClassifyById(){
        CourseClassify courseClassify=courseClassifyDao.queryCourseClassifyById(1);
        System.out.println(courseClassify.toString());
    }

    @Test
    public void queryCourseClassifyList(){
        List<CourseClassify> list=courseClassifyDao.queryCourseClassifyList();
        System.out.println(list.size());
    }

    @Test
    public void queryCourseClassifyCount(){
        CourseClassify courseClassify=new CourseClassify();
        courseClassify.setParent(1);
        int count=courseClassifyDao.queryCourseClassifyCount(courseClassify);
        System.out.println("count:"+count);
    }

    @Test
    public void queryCourseClassifyListByParent(){
        List<CourseClassify> list=courseClassifyDao.queryCourseClassifyListByParent(2);
        System.out.println(list.size());
    }

    @Test
    public void queryCourseClassifyListByCondition(){
        CourseClassify courseClassify=new CourseClassify();
        courseClassify.setParent(0);
        //List<CourseClassify> list=courseClassifyDao.queryCourseClassifyListByCondition(courseClassify,0,4);
        //System.out.println(list.size());
    }

}
