package com.ouxuxi.service.impl;

import com.ouxuxi.dao.CourseSessionDao;
import com.ouxuxi.dao.UserCourseSessionDao;
import com.ouxuxi.entity.CourseSession;
import com.ouxuxi.entity.UserCourseSession;
import com.ouxuxi.service.UserCourseSessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
@Service
public class UserCourseSessionServiceImpl implements UserCourseSessionService {

    @Resource
    private UserCourseSessionDao userCourseSessionDao;
    @Resource
    private CourseSessionDao courseSessionDao;

    @Override
    @Transactional
    public CourseSession getCourseSession(long courseSessionId, long userId) {
        UserCourseSession userCourseSession=new UserCourseSession();
        userCourseSession.setCourseSessionId(courseSessionId);
        userCourseSession.setUserId(userId);
        CourseSession courseSession=courseSessionDao.queryCourseSessionBySessionId(courseSessionId);
        if(courseSession==null) throw new NullPointerException("courseSession为空");
        UserCourseSession result=userCourseSessionDao.queryLatest(userCourseSession);
        if(result==null){
            userCourseSession.setCourseId(courseSession.getCourse().getCourseId());
            userCourseSession.setUpdateTime(new Date());
            userCourseSession.setCreateTime(new Date());
            int num=userCourseSessionDao.add(userCourseSession);
        }else{
            result.setUpdateTime(new Date());
            userCourseSessionDao.update(result);
        }
        return courseSession;
    }
}
