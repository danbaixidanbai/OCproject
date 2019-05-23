package com.ouxuxi.dao;

import com.ouxuxi.entity.CourseComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseCommentDao {
    public List<CourseComment> getAll(@Param("courseComment") CourseComment courseComment);
    public CourseComment getById(long courseCommentId);
    public int addCourseComment(@Param("courseComment") CourseComment courseComment);
    public int deleteCourseComment(long courseCommentId);
}
