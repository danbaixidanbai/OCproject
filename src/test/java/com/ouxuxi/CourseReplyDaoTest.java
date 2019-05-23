package com.ouxuxi;

import com.ouxuxi.dao.CourseReplyDao;
import com.ouxuxi.entity.CourseReply;
import com.ouxuxi.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseReplyDaoTest {
    @Resource
    private CourseReplyDao courseReplyDao;

    @Test
    public void addTest(){
        CourseReply courseReply=new CourseReply();
        courseReply.setReplyContent("asd");
        courseReply.setCommentId(1);
        courseReply.setReplyId(1);
        courseReply.setCreateTime(new Date());
        courseReply.setUpdateTime(new Date());
        User user=new User();
        user.setUserId(1);
        User touser=new User();
        touser.setUserId(2);
        courseReply.setUser(user);
        courseReply.setToUser(touser);
        int num=courseReplyDao.addCourseReply(courseReply);
        System.out.println("num:"+num);
    }

    @Test
    public void getByIdTest(){
        CourseReply courseReply=courseReplyDao.getById(1);
        System.out.println(courseReply.toString());
    }
}
