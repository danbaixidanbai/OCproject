package com.ouxuxi.service;

import com.ouxuxi.entity.UserCollections;

import java.util.List;

public interface UserCollectionsService {
    public List<UserCollections> getAll(long userId,long courseId);
    public int addUserCollection(UserCollections userCollections);
    public int deleteUserCollection(long userId,long courseId);
}
