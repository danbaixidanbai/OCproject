package com.ouxuxi;

import com.ouxuxi.dao.UserCourseSessionDao;
import com.ouxuxi.entity.UserCourseSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCourseSessionDaoTest {
    @Resource
    private UserCourseSessionDao userCourseSessionDao;

    @Test
    public void addTest(){
        UserCourseSession userCourseSession=new UserCourseSession();
        userCourseSession.setUserId(1);
        userCourseSession.setCourseId(1);
        userCourseSession.setCourseSessionId(1);
        userCourseSession.setStatus(1);
        userCourseSession.setRate(1);
        userCourseSession.setCreateTime(new Date());
        userCourseSession.setUpdateTime(new Date());
        int num=userCourseSessionDao.add(userCourseSession);
        System.out.println("num:"+num);
    }
    @Test
    public void updateTest(){
        UserCourseSession userCourseSession=new UserCourseSession();
        userCourseSession.setUserCourseSessionId(1);
        userCourseSession.setUserId(3);
        userCourseSession.setCourseId(3);
        userCourseSession.setCourseSessionId(3);
        userCourseSession.setStatus(2);
        userCourseSession.setRate(2);
        userCourseSession.setCreateTime(new Date());
        userCourseSession.setUpdateTime(new Date());
        int num=userCourseSessionDao.update(userCourseSession);
        System.out.println("num:"+num);
    }
    @Test
    public void getByIdTest(){
        UserCourseSession userCourseSession=userCourseSessionDao.getById(1);
        System.out.println(userCourseSession.toString());
    }
    @Test
    public void getAllTest(){
        UserCourseSession userCourseSession=new UserCourseSession();
        userCourseSession.setUserId(3);
        userCourseSession.setCourseSessionId(3);
        List<UserCourseSession> list=userCourseSessionDao.getAll(userCourseSession);
        System.out.println("size:"+list.size());
    }

    @Test
    public void del(){
        int num=userCourseSessionDao.del(1);
        System.out.println("num:"+num);
    }
}
