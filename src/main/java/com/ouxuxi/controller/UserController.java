package com.ouxuxi.controller;

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
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private UserService userService;


    @GetMapping(value="/islogin")
    public Map<String,Object> isLogin(HttpServletRequest request) throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        User user= (User) request.getSession().getAttribute("user");
        if(user!=null&&user.getUserLoginName()!=null){
            map.put("errCode",0);
            map.put("user",user);
            return map;
        }
        map.put("errCode",1);
        return map;
    }

    @PostMapping(value = "/getlogin")
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
        User userDemo=userService.getByUsernameAndPassword(user);
        if(userDemo!=null ){
            request.getSession().setAttribute("user",userDemo);
            map.put("errCode",0);
            return map;
        }
        map.put("errCode",1);
        return map;
    }



    @PostMapping(value = "/getregister")
    public Map<String,Object> getRegister(HttpServletRequest request) throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            map.put("errCode",2);
            return map;
        }
        String userStr= HttpServletRequestUtil.getString(request,"userStr");
        ObjectMapper mapper=new ObjectMapper();
        User user=null;
        user=mapper.readValue(userStr,User.class);
        User userDemo=userService.getUserByName(user.getUserLoginName());
        if(userDemo!=null ){
            map.put("errCode",1);
            return map;
        }
        user.setUserCreateTime(new Date());
        user.setUserUpdateTime(new Date());
        int num=userService.addUser(user);
        if(num>0){
            map.put("errCode",0);
            return map;
        }
        return map;
    }
}
