package com.ouxuxi.controller.cms;


import com.ouxuxi.entity.Course;
import com.ouxuxi.service.CourseService;
import com.ouxuxi.util.HttpServletRequestUtil;
import com.ouxuxi.util.PageCalculator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/cms/course")
public class CmsCourseController {

    @Resource
    private CourseService courseService;

    @GetMapping(value = "/getcmscourse")
    private Map<String,Object> getCmsCourse(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        Course course=new Course();
        int pageIndex= HttpServletRequestUtil.getInt(request,"pageIndex");
        int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
        if(pageIndex>0&&pageSize>0){
            String courseName=HttpServletRequestUtil.getString(request,"courseName");
            int del=HttpServletRequestUtil.getInt(request,"del");
            if(courseName!=null) course.setCourseName(courseName);
            if(del>=0) course.setDel(del);
        }else{
            map.put("errCode",3);
            map.put("errMsg","加载页数失败");
            return map;
        }
        List<Course> list=courseService.getCourseByCondition(course,pageIndex,pageSize);
        int count=courseService.getCourseCountByCondition(course);
        int pageCount= PageCalculator.calculatePageCount(count,pageSize);
        if(list!=null){
            map.put("list",list);
            map.put("pageCount",pageCount);
            map.put("errCode",1);
        }else{
            map.put("errCode",2);
            map.put("errMsg","加载课程失败");
            return map;
        }
        return map;
    }
    @PostMapping(value = "/updatedel")
    private Map<String,Object> updatedel(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        Course course=new Course();
        long courseId=HttpServletRequestUtil.getLong(request,"courseId");
        int del=HttpServletRequestUtil.getInt(request,"del");
        if(courseId>0&&del>0){
            course.setDel(del);
            course.setCourseId(courseId);
            int num=courseService.updateCourse(course);
            if(num>0){
                map.put("errCode",1);
            }else{
                map.put("errCode",2);
                map.put("errMsg","课程上/下架失败");
                return map;
            }
        }else{
            map.put("errCode",3);
            map.put("errMsg","获取courseId或del失败");
            return map;
        }
        return map;
    }
}
