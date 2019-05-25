package com.ouxuxi.controller;

import com.ouxuxi.entity.Course;
import com.ouxuxi.entity.User;
import com.ouxuxi.entity.UserCollections;
import com.ouxuxi.service.UserCollectionsService;
import com.ouxuxi.util.HttpServletRequestUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/collection")
public class UserCollectionController {
    @Resource
    private UserCollectionsService userCollectionsService;

    @GetMapping(value = "/iscollection")
    private Map<String,Object> iscollection(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        long courseId= HttpServletRequestUtil.getLong(request,"courseId");
        if(courseId<0){
            map.put("errCode",2);
            map.put("errMsg","courseId为空");
            return map;
        }
        User user= (User) request.getSession().getAttribute("user");
        if(user==null&&user.getUserId()<0){
            map.put("errCode",3);
            map.put("errMsg","请重新登录");
            return map;
        }
        List<UserCollections> list=userCollectionsService.getAll(user.getUserId(),courseId);
        if(list.size()>0){
            map.put("errCode",1);
            map.put("list",list.get(0));
            return map;
        }else{
            map.put("errCode",4);
            return map;
        }
    }

    @GetMapping(value = "/docollection")
    private Map<String,Object> docollection(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        long courseId= HttpServletRequestUtil.getLong(request,"courseId");
        int flag=HttpServletRequestUtil.getInt(request,"flag");
        if(courseId<0){
            map.put("errCode",2);
            map.put("errMsg","courseId为空");
            return map;
        }
        if(flag<0){
            map.put("errCode",5);
            map.put("errMsg","获取flag出错");
            return map;
        }
        User user= (User) request.getSession().getAttribute("user");
        if(user==null&&user.getUserId()<0){
            map.put("errCode",3);
            map.put("errMsg","请重新登录");
            return map;
        }
        if(flag==1){
            UserCollections userCollections=new UserCollections();
            Course course=new Course();
            course.setCourseId(courseId);
            userCollections.setCourse(course);
            userCollections.setUser(user);
            userCollections.setCollectionsCreateTime(new Date());
            userCollections.setCollectionsUpdateTime(new Date());
            int num=userCollectionsService.addUserCollection(userCollections);
            if(num>0){
                map.put("errCode",1);
                return map;
            }
        }else if(flag==0){
            int num=userCollectionsService.deleteUserCollection(user.getUserId(),courseId);
            if(num>0){
                map.put("errCode",1);
                return map;
            }
        }
        map.put("errCode",4);
        map.put("errMsg","修改收藏失败");
        return map;
    }
}
