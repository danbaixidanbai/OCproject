package com.ouxuxi;

import com.ouxuxi.dao.UserFollowDao;
import com.ouxuxi.entity.User;
import com.ouxuxi.entity.UserFollow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserFollowDaoTest {
    @Resource
    private UserFollowDao userFollowDao;

    @Test
    public void addTest(){
        UserFollow userFollow=new UserFollow();
        userFollow.setUserId(1);
        User follower =new User();
        follower.setUserId(2);
        userFollow.setFollower(follower);
        userFollow.setCreateTime(new Date());
        userFollow.setUpdateTime(new Date());
        int num=userFollowDao.addUserFollow(userFollow);
        System.out.println("num:"+num);
    }
    @Test
    public void getAllTest(){
        UserFollow userFollow=new UserFollow();
        userFollow.setUserId(1);
        User follower =new User();
        follower.setUserId(2);
        userFollow.setFollower(follower);
        List<UserFollow> list=userFollowDao.getAll(userFollow);
        System.out.println("size:"+list.size());
    }
    @Test
    public void deleteTest(){
        int num=userFollowDao.deleteUserFollow(1,4);
        System.out.println("num:"+num);
    }
}
