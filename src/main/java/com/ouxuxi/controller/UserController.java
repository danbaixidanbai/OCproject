package com.ouxuxi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouxuxi.entity.User;
import com.ouxuxi.service.UserService;
import com.ouxuxi.util.CodeUtil;
import com.ouxuxi.util.HttpServletRequestUtil;
import com.ouxuxi.util.QiniuCloudUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
            if(userDemo.getDel()<=0){
                map.put("errCode",3);
                return map;
            }
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
        user.setDel(1);
        int num=userService.addUser(user);
        if(num>0){
            map.put("errCode",0);
            return map;
        }
        return map;
    }
    //为什么这么做，而不是直接在session，session不一定存所有的信息，如密码等，所以得从数据库拿
    @GetMapping(value ="/getuserbyid")
    private Map<String,Object> getUserById(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        User user=(User)request.getSession().getAttribute("user");
        if(user==null||user.getUserId()<=0){
            map.put("errCode",2);
            map.put("errMsg","你还没登录！！！");
            return map;
        }
        long userId=user.getUserId();
        User userDemo=userService.getUserById(userId);
        if(userDemo==null||userDemo.getUserId()<=0){
            map.put("errCode",3);
            map.put("errMsg","内部错误，请联系工作人员！！！");
        }
        QiniuCloudUtil.getImage(userDemo);
        map.put("errCode",1);
        map.put("user",userDemo);
        return map;
    }
    @PostMapping(value = "/updateuser")
    private Map<String,Object> updateUser(HttpServletRequest request)throws Exception{
        Map<String,Object> map=new HashMap<String,Object>();
        String userStr= HttpServletRequestUtil.getString(request,"userStr");
        String headImage=HttpServletRequestUtil.getString(request,"headImage");
        ObjectMapper mapper=new ObjectMapper();
        User user=null;
        user=mapper.readValue(userStr,User.class);
        boolean flag=false;
        MultipartHttpServletRequest mulRequest=request instanceof MultipartHttpServletRequest?(MultipartHttpServletRequest) request : null;
        MultipartFile file=mulRequest.getFile("userImage");
        if(file!=null) {
            byte[] bytes = file.getBytes();
            String userImage=QiniuCloudUtil.upLoadImage(bytes);
            if(userImage!=null&& !userImage.equals("")){
                user.setUserImage(userImage);
                flag=true;
            }else{
                map.put("errCode",2);
                map.put("errMsg","修改头像失败");
                return map;
            }
        }
        user.setDel(1);
        user.setUserUpdateTime(new Date());
        int num=userService.updateUser(user);
        if(num>0){
            if(headImage!=null&& !headImage.equals("")&& !headImage.equals("undefined")&&flag==true){
                QiniuCloudUtil.deleteImage(headImage);
            }
            User userDemo=userService.getUserById(user.getUserId());
            request.getSession().setAttribute("user",userDemo);
            map.put("errCode",1);
            return map;
        }else{
            map.put("errCode",2);
            map.put("errMsg","修改用户失败");
            return map;
        }
    }

    @GetMapping(value ="/getuser")
    private Map<String,Object> getUser(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        long userId=HttpServletRequestUtil.getLong(request,"userId");
        if(userId<=0){
            map.put("errCode",2);
            map.put("errMsg","userId为空");
            return map;
        }
        User userDemo=userService.getUserById(userId);
        if(userDemo==null||userDemo.getUserId()<=0){
            map.put("errCode",3);
            map.put("errMsg","内部错误，请联系工作人员！！！");
        }
        QiniuCloudUtil.getImage(userDemo);
        map.put("errCode",1);
        map.put("user",userDemo);
        return map;
    }

}
