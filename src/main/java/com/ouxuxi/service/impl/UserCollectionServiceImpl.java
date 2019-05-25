package com.ouxuxi.service.impl;

import com.ouxuxi.dao.UserCollectionsDao;
import com.ouxuxi.entity.Course;
import com.ouxuxi.entity.User;
import com.ouxuxi.entity.UserCollections;
import com.ouxuxi.service.UserCollectionsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserCollectionServiceImpl implements UserCollectionsService {
    @Resource
    private UserCollectionsDao userCollectionsDao;
    @Override
    public List<UserCollections> getAll(long userId, long courseId) {
        UserCollections userCollection=new UserCollections();
        User user=new User();
        user.setUserId(userId);
        Course course=new Course();
        course.setCourseId(courseId);
        userCollection.setUser(user);
        userCollection.setCourse(course);
        List<UserCollections> list=userCollectionsDao.getAll(userCollection);
        return list;
    }

    @Override
    public int addUserCollection(UserCollections userCollections) {
        return userCollectionsDao.addUserCollections(userCollections);
    }

    @Override
    public int deleteUserCollection(long userId, long courseId) {
        return userCollectionsDao.delUserCollections(userId,courseId);
    }
}
