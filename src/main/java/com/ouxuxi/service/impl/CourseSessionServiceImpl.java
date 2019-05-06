package com.ouxuxi.service.impl;

import com.ouxuxi.dao.CourseSessionDao;
import com.ouxuxi.dto.CourseSessionDto;
import com.ouxuxi.entity.Course;
import com.ouxuxi.entity.CourseSession;
import com.ouxuxi.service.CourseSessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseSessionServiceImpl implements CourseSessionService {

    @Resource
    private CourseSessionDao courseSessionDao;

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
    public int addCourseSessison(CourseSession courseSession) {
        return courseSessionDao.addCourseSessison(courseSession);
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
}
