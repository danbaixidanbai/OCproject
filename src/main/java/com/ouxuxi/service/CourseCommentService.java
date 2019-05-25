package com.ouxuxi.service;

import com.ouxuxi.dto.CourseCommentDto;
import com.ouxuxi.entity.CourseComment;
import com.ouxuxi.entity.CourseReply;

import java.util.List;

public interface CourseCommentService {
    public List<CourseCommentDto> getComment(CourseComment courseComment);
    public int addCourseComment(CourseComment courseComment);
    public int addCourseReply(CourseReply courseReply);
}
