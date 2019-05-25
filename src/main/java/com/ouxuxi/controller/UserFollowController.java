package com.ouxuxi.controller;

import com.ouxuxi.entity.User;
import com.ouxuxi.entity.UserFollow;
import com.ouxuxi.service.UserFollowService;
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
@RequestMapping(value = "/userfollow")
public class UserFollowController {
    @Resource
    private UserFollowService userFollowService;

    @GetMapping(value = "/isfollow")
    private Map<String,Object> isfollow(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        long followId= HttpServletRequestUtil.getLong(request,"userId");
        if(followId<0){
            map.put("errCode",2);
            map.put("errMsg","followId为空");
            return map;
        }
        User user= (User) request.getSession().getAttribute("user");
        if(user==null&&user.getUserId()<0){
            map.put("errCode",3);
            map.put("errMsg","请重新登录");
            return map;
        }
        List<UserFollow> list=userFollowService.getAll(user.getUserId(),followId);
        if(list.size()>0){
            map.put("errCode",1);
            return map;
        }else{
            map.put("errCode",4);
            return map;
        }
    }
    @GetMapping(value = "/dofollow")
    private Map<String,Object> dofollow(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        long followId= HttpServletRequestUtil.getLong(request,"followId");
        int flag=HttpServletRequestUtil.getInt(request,"flag");
        if(followId<0){
            map.put("errCode",2);
            map.put("errMsg","followId为空");
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
            UserFollow userFollow=new UserFollow();
            userFollow.setUserId(user.getUserId());
            User folloer=new User();
            folloer.setUserId(followId);
            userFollow.setFollower(folloer);
            userFollow.setUpdateTime(new Date());
            userFollow.setCreateTime(new Date());
            int num=userFollowService.addUserFollow(userFollow);
            if(num>0){
                map.put("errCode",1);
                return map;
            }
        }else if(flag==0){
            int num=userFollowService.deleteUserFollow(user.getUserId(),followId);
            if(num>0){
                map.put("errCode",6);
                return map;
            }
        }
        map.put("errCode",4);
        map.put("errMsg","修改关注失败");
        return map;
    }
}
