package com.ouxuxi.controller;

import com.ouxuxi.dto.CourseClassifyList;
import com.ouxuxi.entity.News;
import com.ouxuxi.service.CourseClassifyService;
import com.ouxuxi.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//首页控制器
@RestController
@RequestMapping(value = "/index")
public class IndexController {

    @Resource
    private NewsService newsService;

    @Resource
    private CourseClassifyService courseClassifyService;

    //获取轮播图
    @PostMapping(value = "/getmainbg")
    private Map<String,Object> getMainBg(){
        Map<String,Object> map=new HashMap<String,Object>();
        List<News> list=newsService.getNewsList();
        if(list!=null&&list.size()>0){
            map.put("errCode",1);
            map.put("news",list);
            map.put("size",list.size());
            return map;
        }else{
            map.put("errCode",2);
            map.put("errMsg","加载轮播图失败！！！");
            return map;
        }

    }

    //获取首页分类
    @GetMapping(value = "/getmenu")
    private Map<String,Object> getMenu(){
        Map<String,Object> map=new HashMap<String,Object>();
        List<CourseClassifyList> courseClassifyLists=courseClassifyService.getCourseClassifyList();
        if(courseClassifyLists!=null&&courseClassifyLists.size()>0){
            map.put("errCode",1);
            map.put("list",courseClassifyLists);
        }else{
            map.put("errCode",2);
            map.put("errMsg","加载menu分类失败！！！");
        }
        return map;
    }
}
