package com.ouxuxi;

import com.ouxuxi.dao.UserCollectionsDao;
import com.ouxuxi.entity.Course;
import com.ouxuxi.entity.User;
import com.ouxuxi.entity.UserCollections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCollectionDaoTest {
    @Resource
    private UserCollectionsDao userCollectionsDao;

    @Test
    public void addTest(){
        UserCollections userCollections=new UserCollections();
        userCollections.setCollectionsUpdateTime(new Date());
        userCollections.setCollectionsCreateTime(new Date());
        Course course=new Course();
        course.setCourseId(1);
        User user=new User();
        user.setUserId(1);
        userCollections.setCourse(course);
        userCollections.setUser(user);
        int num=userCollectionsDao.addUserCollections(userCollections);
        System.out.println("num:"+num);
    }
    @Test
    public void queryAllTest(){
        UserCollections userCollections=new UserCollections();
        Course course=new Course();
        course.setCourseId(1);
        User user=new User();
        user.setUserId(1);
        userCollections.setCourse(course);
        userCollections.setUser(user);
        List<UserCollections> list=userCollectionsDao.getAll(userCollections);
        System.out.println("size:"+list.size());
    }
    @Test
    public void deleteTest(){
        int num=userCollectionsDao.delUserCollections(1,1);
        System.out.println("num:"+num);
    }
}
