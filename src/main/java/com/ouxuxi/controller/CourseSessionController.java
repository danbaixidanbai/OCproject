package com.ouxuxi.controller;

import com.ouxuxi.dto.CourseSessionDto;
import com.ouxuxi.entity.Course;
import com.ouxuxi.entity.CourseSession;
import com.ouxuxi.entity.User;
import com.ouxuxi.entity.UserCourseSession;
import com.ouxuxi.service.CourseSessionService;
import com.ouxuxi.service.UserCourseSessionService;
import com.ouxuxi.util.HttpServletRequestUtil;
import com.ouxuxi.util.QiniuCloudUtil;
import javafx.scene.chart.ValueAxis;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/coursesession")
public class CourseSessionController {
    @Resource
    private CourseSessionService courseSessionService;

    @Resource
    private UserCourseSessionService userCourseSessionService;


    @GetMapping(value = "/updatesessionstatus")
    private Map<String,Object> updateSessionStatus(int status,long sessionId){
        Map<String,Object> map=new HashMap<String,Object>();
        if(status<=0){
            map.put("errCode",2);
            map.put("errMsg","获取审核状态失败");
            return map;
        }
        if(sessionId<=0){
            map.put("errCode",3);
            map.put("errMsg","获取节失败");
            return map;
        }
        int num=courseSessionService.updateSessionStatus(status,sessionId);
        if(num>0){
            map.put("errCode",1);
            return map;
        }else{
            map.put("errCode",4);
            map.put("errMsg","修改status失败");
            return map;
        }

    }

    @GetMapping(value = "/getcoursesession")
    private Map<String,Object> getCourseSession(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        long courseId= HttpServletRequestUtil.getLong(request,"courseId");
        if(courseId>0){
            List<CourseSessionDto> list=courseSessionService.getCourseSessionDto(courseId);
            if(list!=null&&list.size()>0){
                map.put("errCode",1);
                map.put("list",list);
                return map;
            }else{
                map.put("errCode",3);
                map.put("errMsg","你还没有添加章节");
                return map;
            }
        }else{
            map.put("errCode",2);
            map.put("errMsg","courseId为空");
            return map;
        }
    }

    @GetMapping(value = "/addsession")
    private Map<String,Object> addCourseSession(HttpServletRequest request) throws Exception {
        Map<String,Object> map=new HashMap<String,Object>();
        long courseId= HttpServletRequestUtil.getLong(request,"courseId");
        String sessionName=HttpServletRequestUtil.getString(request,"sessionName");
        if(sessionName==null&&sessionName.equals("")){
            map.put("errCode",3);
            map.put("errMsg","请输入章名称");
            return map;
        }
        if(courseId>0){
            CourseSession courseSession=new CourseSession();
            Course course=new Course();
            course.setCourseId(courseId);
            courseSession.setCourse(course);
            courseSession.setCourseSessionName(sessionName);
            courseSession.setCourseSessionVideoUrl("");
            courseSession.setCourseSessionCreateTime(new Date());
            courseSession.setCourseSessionUpdateTime(new Date());
            int num=courseSessionService.addCourseSessison(courseSession);
            if(num>0){
                map.put("errCode",1);
                return map;
            }else{
                map.put("errCode",4);
                map.put("errMsg","添加失败session");
                return map;
            }
        }else{
            map.put("errCode",2);
            map.put("errMsg","courseId为空");
            return map;
        }

    }

    @PostMapping(value = "/addsessiontitle")
    private Map<String,Object> addCourseSessionTitle (HttpServletRequest request)throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        long courseId= HttpServletRequestUtil.getLong(request,"courseId");
        String sessiontitleName=HttpServletRequestUtil.getString(request,"sessiontitleName");
        int parent=HttpServletRequestUtil.getInt(request,"parent");
        if(parent<0){
            map.put("errCode",5);
            map.put("errMsg","获取章出错，请重试");
            return map;
        }
        if(sessiontitleName==null&&sessiontitleName.equals("")){
            map.put("errCode",3);
            map.put("errMsg","请输入章名称");
            return map;
        }
        if(courseId>0){
            MultipartHttpServletRequest mulRequest=request instanceof MultipartHttpServletRequest?(MultipartHttpServletRequest) request : null;
            MultipartFile file=mulRequest.getFile("sessiontitleUrl");
            byte[] bytes=file.getBytes();
            String url= QiniuCloudUtil.upLoadImage(bytes);
            if(url==null&&url.equals("")){
                map.put("errCode",4);
                map.put("errMsg","上传视频失败，请重试！");
                return map;
            }
            CourseSession courseSession=new CourseSession();
            courseSession.setCourseSessionVideoUrl(url);
            Course course=new Course();
            course.setCourseId(courseId);
            courseSession.setCourse(course);
            courseSession.setParent(parent);
            courseSession.setCourseSessionName(sessiontitleName);
            courseSession.setCourseSessionCreateTime(new Date());
            courseSession.setCourseSessionUpdateTime(new Date());
            int num=courseSessionService.addCourseSessison(courseSession);
            if(num>0){
                map.put("errCode",1);
                return map;
            }else{
                map.put("errCode",6);
                map.put("errMsg","添加节失败");
                return map;
            }
        }else{
            map.put("errCode",2);
            map.put("errMsg","courseId为空");
            return map;
        }
    }

    @GetMapping(value = "/getvideosession")
    private Map<String,Object> getvideosession(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        long sessionId=HttpServletRequestUtil.getLong(request,"sessionId");
        if(sessionId<=0){
            map.put("errCode",3);
            map.put("errMsg","获取节失败");
            return map;
        }
        User user=(User)request.getSession().getAttribute("user");
        long userId=user.getUserId();
        CourseSession courseSession=userCourseSessionService.getCourseSession(sessionId,userId);
        if(courseSession==null){
            map.put("errCode",2);
            map.put("errMsg","获取节出错");
            return map;
        }
        map.put("courseSession",courseSession);
        map.put("errCode",1);
        return map;
    }

    @GetMapping(value = "/getlasted")
    private Map<String,Object> getlasted(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        long courseId=HttpServletRequestUtil.getLong(request,"courseId");
        if(courseId<0){
            map.put("errCode",2);
            map.put("errMsg","获取courseId失败");
            return map;
        }
        User user= (User) request.getSession().getAttribute("user");
        if(user==null||user.getUserId()<0){
            map.put("errCode",3);
            map.put("errMsg","请重新登录");
            return map;
        }
        UserCourseSession userCourseSession=userCourseSessionService.getLastSession(courseId,user.getUserId());
        CourseSession courseSession;
        if(userCourseSession==null){
            courseSession=courseSessionService.getfirstCourseSession();
            map.put("errCode",1);
            map.put("courseSession",courseSession);
            return map;
        }else{
            courseSession=courseSessionService.getCourseSessionBySessionId(userCourseSession.getCourseSessionId());
            map.put("errCode",5);
            map.put("courseSession",courseSession);
            return map;
        }

    }
}
