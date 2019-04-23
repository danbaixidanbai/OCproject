package com.ouxuxi.dao;

import com.ouxuxi.entity.CourseClassify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseClassifyDao {
    //根据id获取
    CourseClassify queryCourseClassifyById(long classifyId);
    //获取所有
    List<CourseClassify> queryCourseClassifyList();
    //获取总数量
    Integer queryCourseClassifyCount(@Param("condition")CourseClassify condition);
    //根据parent获取
    List<CourseClassify> queryCourseClassifyListByParent(int parent);
    //获取非一级目录
    List<CourseClassify> queryCourseClassify();

    //添加新纪录
    int addCourseClassify(@Param("condition")CourseClassify condition);
    //更新
    int updateCourseClassify(@Param("condition")CourseClassify condition);
    //删除
    int deleteCourseClassify(long classifyId);




}