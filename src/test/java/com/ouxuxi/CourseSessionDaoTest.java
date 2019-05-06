package com.ouxuxi;

import com.ouxuxi.dao.CourseSessionDao;
import com.ouxuxi.entity.Course;
import com.ouxuxi.entity.CourseSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseSessionDaoTest {
    @Resource
    private CourseSessionDao courseSessionDao;

    @Test
    public void addCourseSessisonTest(){
        CourseSession courseSession=new CourseSession();
        Course course=new Course();
        course.setCourseId(22);
        courseSession.setCourse(course);
        courseSession.setCourseSessionName("phython的创始人？");
        courseSession.setParent(1);
        courseSession.setCourseSessionPriority(1);
        courseSession.setCourseSessionVideoUrl("demo.mp4");
        courseSession.setCourseSessionCreateTime(new Date());
        courseSession.setCourseSessionUpdateTime(new Date());
        int num=courseSessionDao.addCourseSessison(courseSession);
        System.out.println("num:"+num);
        System.out.println("courseSessionId:"+courseSession.getCourseSessionId());
    }

    @Test
    public void updateCourseSessionTest(){
        CourseSession courseSession=new CourseSession();
        courseSession.setCourseSessionId(3);
        courseSession.setCourseSessionName("什么是phython");
        courseSession.setCourseSessionPriority(2);
        courseSession.setCourseSessionUpdateTime(new Date());
        int num=courseSessionDao.updateCourseSession(courseSession);
        System.out.println("num:"+num);
    }

    @Test
    public void queryCourseSessionByCourseIdTest(){
        List<CourseSession> list=courseSessionDao.queryCourseSessionByCourseId(22);
        System.out.println("num:"+list.size());
    }

    @Test
    public void queryCourseSessionByParent(){
        List<CourseSession> list=courseSessionDao.queryCourseSessionByParent(1);
        System.out.println("num:"+list.size());
    }
    @Test
    public void queryCourseSessionBySessionId(){
        CourseSession courseSession=courseSessionDao.queryCourseSessionBySessionId(4);
        System.out.println(courseSession.toString());
    }
}
