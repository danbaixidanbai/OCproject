package com.ouxuxi.controller;

import com.ouxuxi.dto.CourseSessionDto;
import com.ouxuxi.entity.CourseSession;
import com.ouxuxi.service.CourseSessionService;
import com.ouxuxi.util.HttpServletRequestUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/coursesession")
public class CourseSessionController {
    @Resource
    private CourseSessionService courseSessionService;

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
}
