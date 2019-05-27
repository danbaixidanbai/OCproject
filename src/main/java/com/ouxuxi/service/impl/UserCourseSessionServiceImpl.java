package com.ouxuxi.service.impl;

import com.ouxuxi.dao.CourseDao;
import com.ouxuxi.dao.CourseSessionDao;
import com.ouxuxi.dao.UserCourseSessionDao;
import com.ouxuxi.entity.Course;
import com.ouxuxi.entity.CourseSession;
import com.ouxuxi.entity.UserCourseSession;
import com.ouxuxi.service.UserCourseSessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserCourseSessionServiceImpl implements UserCourseSessionService {

    @Resource
    private UserCourseSessionDao userCourseSessionDao;
    @Resource
    private CourseSessionDao courseSessionDao;

    @Resource
    private CourseDao courseDao;

    @Override
    @Transactional
    //获取章视频，并且决定是否添加课程学习人数
    public CourseSession getCourseSession(long courseSessionId, long userId) {
        UserCourseSession userCourseSession=new UserCourseSession();
        userCourseSession.setUserId(userId);
        List<UserCourseSession> list=userCourseSessionDao.getAll(userCourseSession);
        CourseSession courseSession=courseSessionDao.queryCourseSessionBySessionId(courseSessionId);
        if(courseSession==null){
            throw new NullPointerException("courseSession为空");
        }
        //若用户章节学习表没，说明还没学习过，让课程学习人数+1
        if(list.size()<=0){
            courseDao.updateCourseCount(courseSession.getCourse().getCourseId());
        }
        userCourseSession.setCourseSessionId(courseSessionId);
        UserCourseSession result=userCourseSessionDao.queryLatest(userCourseSession);
        if(result==null){
            userCourseSession.setCourseId(courseSession.getCourse().getCourseId());
            userCourseSession.setUpdateTime(new Date());
            userCourseSession.setCreateTime(new Date());
            userCourseSessionDao.add(userCourseSession);
        }else{
            result.setUpdateTime(new Date());
            userCourseSessionDao.update(result);
        }
        return courseSession;
    }

    @Override
    public UserCourseSession getLastSession(long courseId, long userId) {
        UserCourseSession result=new UserCourseSession();
        result.setUserId(userId);
        result.setCourseId(courseId);
        UserCourseSession userCourseSession=userCourseSessionDao.queryLatest(result);
        return userCourseSession;
    }
}
