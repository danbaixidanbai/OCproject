package com.ouxuxi.service.impl;

import com.ouxuxi.dao.CourseDao;
import com.ouxuxi.dao.CourseSessionDao;
import com.ouxuxi.dto.CourseSessionDto;
import com.ouxuxi.entity.Course;
import com.ouxuxi.entity.CourseSession;
import com.ouxuxi.service.CourseSessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CourseSessionServiceImpl implements CourseSessionService {

    @Resource
    private CourseSessionDao courseSessionDao;
    @Resource
    private CourseDao courseDao;

    @Override
    public List<CourseSession> getCourseSessionByCourseId(long courseId) {
        return courseSessionDao.queryCourseSessionByCourseId(courseId);
    }

    @Override
    public List<CourseSession> getCourseSessionByParent(int parent) {
        return courseSessionDao.queryCourseSessionByParent(parent);
    }

    @Override
    public CourseSession getCourseSessionBySessionId(long courseSessionId) {
        return courseSessionDao.queryCourseSessionBySessionId(courseSessionId);
    }

    @Override
    public int updateCourseSession(CourseSession courseSession) {
        return courseSessionDao.updateCourseSession(courseSession);
    }

    @Override
    @Transactional
    public int addCourseSessison(CourseSession courseSession) throws Exception {
        Course course=courseSession.getCourse();
        course.setDel(2);
        course.setCourseUpdateTime(new Date());
        int num=courseDao.updateCourse(course);
        if(num<0){
            throw new Exception("修改课程下架失败");
        }
        int num1=courseSessionDao.addCourseSessison(courseSession);
        if(num1<0){
            throw new Exception("添加节失败");
        }
        if(num>0&&num1>0) return 1;
        return 0;
    }

    @Override
    public int delCourseSessionBySessionId(long courseSessionId) {
        return courseSessionDao.delCourseSessionBySessionId(courseSessionId);
    }

    @Override
    public int delCourseSessionByCourseId(long courseId) {
        return courseSessionDao.delCourseSessionByCourseId(courseId);
    }

    @Override
    public List<CourseSessionDto> getCourseSessionDto(long courseId) {
        CourseSession courseSession=new CourseSession();
        Course course=new Course();
        course.setCourseId(courseId);
        courseSession.setCourse(course);
        //查出全部标题
        List<CourseSession> list=courseSessionDao.queryCourseSessionByCondition(courseSession);
        List<CourseSessionDto> listDto=new ArrayList<CourseSessionDto>();
        for(CourseSession session : list){
            //各标题下对应章节
            int parent=(int)session.getCourseSessionId();
            courseSession.setParent(parent);
            List<CourseSession> listsession=courseSessionDao.queryCourseSessionByCondition(courseSession);
            CourseSessionDto courseSessionDto=new CourseSessionDto();
            courseSessionDto.setCourseSession(session);
            courseSessionDto.setList(listsession);
            listDto.add(courseSessionDto);
        }
        return listDto;
    }

    @Override
    public int updateSessionStatus(int status, long sessionId) {
        return courseSessionDao.updateSessionStatus(status,sessionId);
    }

    @Override
    public CourseSession getfirstCourseSession() {
        return courseSessionDao.getFirstBytime();
    }
}
