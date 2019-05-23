package com.ouxuxi;

import com.ouxuxi.dao.CourseCommentDao;
import com.ouxuxi.entity.CourseComment;
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
public class CourseCommentDaoTest {
    @Resource
    private CourseCommentDao courseCommentDao;

    @Test
    public void addTest(){
        CourseComment courseComment=new CourseComment();
        courseComment.setCourseCommentContent("sadadas");
        courseComment.setCourseId(1);
        courseComment.setCourseSessionId(1);
        User user=new User();
        user.setUserId(1);
        courseComment.setUpdateTime(new Date());
        courseComment.setUser(user);
        courseComment.setCreateTime(new Date());
        int num=courseCommentDao.addCourseComment(courseComment);
        System.out.println("num:"+num);
    }

    @Test
    public void getByIdTest(){
        CourseComment courseComment=courseCommentDao.getById(1);
        System.out.println(courseComment.toString());
    }

    @Test
    public void getAllTest(){
        CourseComment courseComment=new CourseComment();
        courseComment.setCourseId(1);
        List<CourseComment> list=courseCommentDao.getAll(courseComment);
        System.out.println("size:"+list.size());
    }

    @Test
    public void deleteTest(){
        int num=courseCommentDao.deleteCourseComment(2);
        System.out.println("num:"+num);
    }
}
