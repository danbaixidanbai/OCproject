package com.ouxuxi.controller;

import com.ouxuxi.entity.CourseClassify;
import com.ouxuxi.service.CourseClassifyService;
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
@RequestMapping(value = "/courseclassify")
public class CourseClassifyController {

    @Resource
    private CourseClassifyService courseClassifyService;

    @GetMapping(value = "/getallclassify")
    private Map<String,Object> getClassify(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        List<CourseClassify> listParent=courseClassifyService.getCourseClassifyParent();
        List<CourseClassify> list=courseClassifyService.getCourseClassify();
        if(listParent!=null&&listParent.size()>0){
            map.put("classifyParent",listParent);
        }else{
            map.put("errCode",2);
            map.put("errMsg","获取一级分类失败");
            return map;
        }
        if(list!=null&&list.size()>0){
            map.put("classify",list);
        }else{
            map.put("errCode",3);
            map.put("errMsg","获取二级分类失败");
            return map;
        }
        map.put("errCode",1);
        return map;
    }
    @GetMapping(value = "/getsecondclassify")
    private Map<String,Object> getSecondClassify(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        int parentId=HttpServletRequestUtil.getInt(request,"parentId");
        List<CourseClassify> list=null;
        if(parentId>0){
          list=courseClassifyService.getCourseClassifyByParentId(parentId);
          if(list!=null&&list.size()>0){
              map.put("classify",list);
          }else{
              map.put("errCode",3);
              map.put("errMsg","获取二级分类失败");
              return map;
          }
        }else{
            map.put("errCode",2);
            map.put("errMsg","parentId为空");
            return map;
        }
        map.put("errCode",1);
        return map;
    }
    //首页classifyId不为空
    @GetMapping(value = "/getbyclassifyid")
    private Map<String,Object> getByClassifyId(HttpServletRequest request){

    }
}
