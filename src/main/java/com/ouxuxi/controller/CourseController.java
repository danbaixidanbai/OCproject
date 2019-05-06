package com.ouxuxi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouxuxi.entity.Course;
import com.ouxuxi.entity.CourseClassify;
import com.ouxuxi.entity.User;
import com.ouxuxi.service.CourseService;
import com.ouxuxi.util.HttpServletRequestUtil;
import com.ouxuxi.util.PageCalculator;
import com.ouxuxi.util.QiniuCloudUtil;
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
@RequestMapping(value = "/course")
public class CourseController {

    @Resource
    private CourseService courseService;



    @PostMapping(value = "/updatecourse")
    private Map<String,Object> updateCourse(HttpServletRequest request) throws  Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        String courseStr= HttpServletRequestUtil.getString(request,"courseStr");
        String imageKey=  HttpServletRequestUtil.getString(request,"imageKey");
        ObjectMapper mapper=new ObjectMapper();
        Course course=null;
        course=mapper.readValue(courseStr,Course.class);
        boolean flag=false;
        MultipartHttpServletRequest mulRequest=request instanceof MultipartHttpServletRequest?(MultipartHttpServletRequest) request : null;
        MultipartFile file=mulRequest.getFile("picture");
        if(file!=null) {
            byte[] bytes = file.getBytes();
            String courseImage= QiniuCloudUtil.upLoadImage(bytes);
            if(courseImage!=null&& !courseImage.equals("")){
                course.setCourseImage(courseImage);
                flag=true;
            }else{
                map.put("errCode",2);
                map.put("errMsg","修改课程图片失败");
                return map;
            }
        }
        course.setCourseUpdateTime(new Date());
        int num=courseService.updateCourse(course);
        if(num>0){
            if(imageKey!=null&& !imageKey.equals("")&&flag==true){
                QiniuCloudUtil.deleteImage(imageKey);
            }
            map.put("errCode",1);
            return map;
        }else{
            map.put("errCode",2);
            map.put("errMsg","修改用户失败");
            return map;
        }
    }

    @GetMapping(value = "/getcoursebyid")
    private Map<String,Object> getCourseById(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        long courseId=HttpServletRequestUtil.getLong(request,"courseId");
        if(courseId>0){
            Course course=courseService.getCourseByCourseId(courseId);
            if (course!=null){
                map.put("errCode",1);
                map.put("course",course);
                return map;
            }else{
                map.put("errCode",3);
                map.put("errMsg","加载course失败");
                return map;
            }
        }else{
            map.put("errCode",2);
            map.put("errMsg","加载courseId失败");
            return map;
        }
    }

    //获取首页和分类的课程
    @GetMapping(value = "/getcourse")
    private Map<String,Object> getCourse(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        Course course=new Course();
        int pageIndex= HttpServletRequestUtil.getInt(request,"pageIndex");
        int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
        if(pageIndex>0&&pageSize>0){
            long classifyId=HttpServletRequestUtil.getLong(request,"classifyId");
            long parentId=HttpServletRequestUtil.getLong(request,"parentId");
            if(parentId>0){
                CourseClassify classifyparent=new CourseClassify();
                classifyparent.setClassifyId(parentId);
                course.setCourseClassifyParent(classifyparent);
            }
            if(classifyId>0){
                CourseClassify classify=new CourseClassify();
                classify.setClassifyId(classifyId);
                course.setCourseClassify(classify);
            }
        }else{
            map.put("errCode",3);
            map.put("errMsg","加载页数失败");
            return map;
        }
        course.setDel(1);
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

    @GetMapping(value = "/getcourseinfo")
    private Map<String,Object> getCmsCourse(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user= (User) request.getSession().getAttribute("user");
        Course course=new Course();
        if(user!=null){
            course.setUser(user);
        }else{
            map.put("errCode", 4);
            map.put("errMsg", "你还未登陆");
            return map;
        }
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex > 0 && pageSize > 0) {
            String courseName = HttpServletRequestUtil.getString(request, "courseName");
            int del = HttpServletRequestUtil.getInt(request, "del");
            if (courseName != null) course.setCourseName(courseName);
            if (del >= 0) course.setDel(del);
        } else {
            map.put("errCode", 3);
            map.put("errMsg", "加载页数失败");
            return map;
        }
        List<Course> list = courseService.getCourseByCondition(course, pageIndex, pageSize);
        int count = courseService.getCourseCountByCondition(course);
        int pageCount = PageCalculator.calculatePageCount(count, pageSize);
        if (list != null&&list.size()>0) {
            map.put("list", list);
            map.put("pageCount", pageCount);
            map.put("errCode", 1);
        } else {
            map.put("errCode", 2);
            map.put("errMsg", "你还没有自己的课程！！");
            return map;
        }
        return map;
    }
}
