package com.ouxuxi.dao;

import com.ouxuxi.entity.CourseReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseReplyDao {
    public CourseReply getById(long replyId);
    public List<CourseReply> getAll(@Param("courseReply")CourseReply courseReply);
    public int addCourseReply(@Param("courseReply")CourseReply courseReply);
    public int delCourseReply(@Param("courseReply")CourseReply courseReply);
}
