package com.ouxuxi.service.impl;

import com.ouxuxi.dao.UserFollowDao;
import com.ouxuxi.entity.User;
import com.ouxuxi.entity.UserFollow;
import com.ouxuxi.service.UserFollowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

@Service
public class UserFollowServiceImpl implements UserFollowService {
    @Resource
    private UserFollowDao userFollowDao;
    @Override
    public List<UserFollow> getAll(long userid, long followId) {
        UserFollow userFollow=new UserFollow();
        userFollow.setUserId(userid);
        User follower =new User();
        follower.setUserId(followId);
        userFollow.setFollower(follower);
        return userFollowDao.getAll(userFollow);
    }

    @Override
    public int addUserFollow(UserFollow userFollow) {
        return userFollowDao.addUserFollow(userFollow);
    }

    @Override
    public int deleteUserFollow(long userid, long followId) {
        return userFollowDao.deleteUserFollow(userid,followId);
    }
}
