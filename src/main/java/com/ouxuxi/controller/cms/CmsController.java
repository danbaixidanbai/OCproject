package com.ouxuxi.controller.cms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouxuxi.entity.User;
import com.ouxuxi.service.UserService;
import com.ouxuxi.util.CodeUtil;
import com.ouxuxi.util.HttpServletRequestUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/cms")
public class CmsController {
    @Resource
    private UserService userService;


    @GetMapping(value="/islogin")
    public Map<String,Object> isLogin(HttpServletRequest request) throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        User user= (User) request.getSession().getAttribute("admin");
        if(user!=null&&user.getUserLoginName()!=null){
            map.put("errCode",0);
            map.put("admin",user);
            return map;
        }
        map.put("errCode",1);
        return map;
    }


    @PostMapping(value = "/getcmslogin")
    public Map<String,Object> getLogin(HttpServletRequest request) throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            map.put("errCode",2);
            return map;
        }
        String userStr= HttpServletRequestUtil.getString(request,"userStr");
        ObjectMapper mapper=new ObjectMapper();
        User user=null;
        user=mapper.readValue(userStr,User.class);
        user.setUserType(1);
        User userDemo=userService.getAdmin(user);
        if(userDemo!=null ){
            request.getSession().setAttribute("admin",userDemo);
            map.put("errCode",0);
            return map;
        }
        map.put("errCode",1);
        return map;
    }

    @GetMapping(value = "/getalluser")
    private Map<String,Object> getAllUser(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        List<User> list=userService.getAllUser();
        if (list.size()>0){
            map.put("errCode",1);
            map.put("list",list);
            return map;
        }else{
            map.put("errCode",2);
            map.put("errMsg","获取user为空");
            return map;
        }
    }

    @PostMapping(value = "/user/setjy")
    private Map<String,Object> userSetJy (HttpServletRequest request) throws Exception {
        Map<String,Object> map=new HashMap<String,Object>();
        long userId=HttpServletRequestUtil.getLong(request,"userId");
        int del=HttpServletRequestUtil.getInt(request,"del");
        if(userId<0){
            map.put("errCode",2);
            map.put("errMsg","获取userId出错");
            return map;
        }
        if(del<0){
            map.put("errCode",3);
            map.put("errMsg","获取del出错");
            return map;
        }
        User user=new User();
        user.setUserId(userId);
        user.setDel(del);
        user.setUserUpdateTime(new Date());
        int num=userService.updateUser(user);
        if(num>0){
            map.put("errCode",1);
            return map;
        }else{
            map.put("errCode",4);
            map.put("errMsg","修改出错");
            return map;
        }

    }
}
