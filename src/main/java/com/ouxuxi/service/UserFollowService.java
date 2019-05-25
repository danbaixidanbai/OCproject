package com.ouxuxi.service;

import com.ouxuxi.entity.UserFollow;

import java.util.List;

public interface UserFollowService {
    public List<UserFollow> getAll(long userid,long followId);
    public int addUserFollow(UserFollow userFollow);
    public int deleteUserFollow(long userid,long followId);
}
