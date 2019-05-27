package com.ouxuxi.controller;

import com.ouxuxi.dto.CourseCommentDto;
import com.ouxuxi.entity.CourseComment;
import com.ouxuxi.entity.CourseReply;
import com.ouxuxi.entity.User;
import com.ouxuxi.service.CourseCommentService;
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
@RequestMapping(value = "/coursecomment")
public class CourseCommentController {
    @Resource
    private CourseCommentService courseCommentService;

    @GetMapping(value = "/getcomment")
    public Map<String,Object> getCommentByCourseId(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        Long courseId= HttpServletRequestUtil.getLong(request,"courseId");
        Long sessionId= HttpServletRequestUtil.getLong(request,"sessionId");
        CourseComment courseComment=new CourseComment();
        if(courseId>0){
            courseComment.setCourseId(courseId);
        }
        if(sessionId>0){
            courseComment.setCourseSessionId(sessionId);
        }
        List<CourseCommentDto> list=courseCommentService.getComment(courseComment);
        if(list.size()>0){
            map.put("errCode",1);
            map.put("courseCommentDto",list);
            return map;
        }else{
            map.put("errCode",2);
            map.put("errMsg","还没有人评论哎");
            return map;
        }

    }
    @PostMapping(value = "/addcomment")
    private Map<String,Object> addComment(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        Long courseId= HttpServletRequestUtil.getLong(request,"courseId");
        Long sessionId= HttpServletRequestUtil.getLong(request,"sessionId");
        String comment=HttpServletRequestUtil.getString(request,"comment");
        User user= (User) request.getSession().getAttribute("user");
        if(user==null&&user.getUserId()<0){
            map.put("errCode",3);
            map.put("errMsg","请重新登录");
            return map;
        }
        CourseComment courseComment=new CourseComment();
        if(courseId>0){
            courseComment.setCourseId(courseId);
        }
        if(sessionId>0){
            courseComment.setCourseSessionId(sessionId);
        }
        courseComment.setCourseId(courseId);
        courseComment.setUser(user);
        courseComment.setCourseCommentContent(comment);

        courseComment.setCreateTime(new Date());
        courseComment.setUpdateTime(new Date());
        int num=courseCommentService.addCourseComment(courseComment);
        if(num>0){
            map.put("errCode",1);
            return map;
        }else{
            map.put("errCode",4);
            map.put("errMsg","评论失败");
            return map;
        }
    }
    @PostMapping(value = "/addreply")
    private Map<String,Object> addReply(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        long toUserId=HttpServletRequestUtil.getLong(request,"toUserId");
        long commentId=HttpServletRequestUtil.getLong(request,"commentId");
        long replyId=HttpServletRequestUtil.getLong(request,"replyId");
        String replyContent=HttpServletRequestUtil.getString(request,"replyContent");
        User user= (User) request.getSession().getAttribute("user");
        if(user==null&&user.getUserId()<0){
            map.put("errCode",3);
            map.put("errMsg","请重新登录");
            return map;
        }
        if(commentId<0){
            map.put("errCode",2);
            map.put("errMsg","获取评论Id出错");
            return map;
        }
        CourseReply courseReply=new CourseReply();
        courseReply.setUser(user);
        User toUser=new User();
        toUser.setUserId(toUserId);
        courseReply.setToUser(toUser);
        courseReply.setCommentId(commentId);
        courseReply.setReplyId(replyId);
        courseReply.setReplyContent(replyContent);
        courseReply.setUpdateTime(new Date());
        courseReply.setCreateTime(new Date());
        int num=courseCommentService.addCourseReply(courseReply);
        if(num>0){
            map.put("errCode",1);
            return map;
        }else{
            map.put("errCode",4);
            map.put("errMsg","回复失败，请稍后重试");
            return map;
        }
    }

}
