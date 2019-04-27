package com.ouxuxi.controller;

import com.ouxuxi.entity.Course;
import com.ouxuxi.service.CourseService;
import com.ouxuxi.util.HttpServletRequestUtil;
import com.ouxuxi.util.PageCalculator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @GetMapping(value = "/getcourse")
    private Map<String,Object> getCourse(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        Course course=new Course();
        int pageIndex= HttpServletRequestUtil.getInt(request,"pageIndex");
        int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
        if(pageIndex>0&&pageSize>0){
            long classifyId=HttpServletRequestUtil.getLong(request,"classifyId");
            long parentId=HttpServletRequestUtil.getLong(request,"parentId");
            if(parentId>0) course.setCourseClassifyParent(parentId);
            if(classifyId>0) course.setCourseClassify(classifyId);
        }else{
            map.put("errCode",3);
            map.put("errMsg","加载页数失败");
            return map;
        }
        List<Course> list=courseService.getCourseByCondition(course,pageIndex,pageSize);
        int count=courseService.getCourseCountByCondition(course);
        int pageCount= PageCalculator.calculatePageCount(count,pageSize);
        map.put("pageCount",pageCount);
        map.put("count",count);
        if(list!=null){
            map.put("course",list);
            map.put("errCode",1);
        }else{
            map.put("errCode",2);
            map.put("errMsg","加载课程失败");
        }
        return map;
    }
}
