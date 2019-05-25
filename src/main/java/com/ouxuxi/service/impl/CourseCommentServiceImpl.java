package com.ouxuxi.service.impl;

import com.ouxuxi.dao.CourseCommentDao;
import com.ouxuxi.dao.CourseReplyDao;
import com.ouxuxi.dto.CourseCommentDto;
import com.ouxuxi.entity.CourseComment;
import com.ouxuxi.entity.CourseReply;
import com.ouxuxi.service.CourseCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseCommentServiceImpl implements CourseCommentService {

    @Resource
    private CourseCommentDao courseCommentDao;
    @Resource
    private CourseReplyDao courseReplyDao;

    @Override
    public List<CourseCommentDto> getComment(CourseComment courseComment) {
        List<CourseComment> courseComments=courseCommentDao.getAll(courseComment);
        List<CourseCommentDto> courseCommentDtos=new ArrayList<CourseCommentDto>();
        for (CourseComment c :courseComments){
            CourseReply courseReply=new CourseReply();
            courseReply.setCommentId(c.getCourseCommentId());
            List<CourseReply> courseReplies=courseReplyDao.getAll(courseReply);
            CourseCommentDto courseCommentDto=new CourseCommentDto();
            courseCommentDto.setCourseComment(c);
            courseCommentDto.setList(courseReplies);
            courseCommentDtos.add(courseCommentDto);
        }
        return courseCommentDtos;
    }

    @Override
    public int addCourseComment(CourseComment courseComment) {
        return courseCommentDao.addCourseComment(courseComment);
    }

    @Override
    public int addCourseReply(CourseReply courseReply) {
        return courseReplyDao.addCourseReply(courseReply);
    }
}
