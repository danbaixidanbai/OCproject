package com.ouxuxi.service;

import com.ouxuxi.dto.CourseClassifyList;
import com.ouxuxi.entity.CourseClassify;

import java.util.List;

public interface CourseClassifyService {

    public List<CourseClassifyList> getCourseClassifyList();
    public List<CourseClassify> getCourseClassifyParent();
    public List<CourseClassify> getCourseClassifyByParentId(int parentId);
    //获取二级目录
    public List<CourseClassify> getCourseClassify();
}
