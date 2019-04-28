package com.ouxuxi.service.impl;

import com.ouxuxi.dao.CourseClassifyDao;
import com.ouxuxi.dto.CourseClassifyList;
import com.ouxuxi.entity.CourseClassify;
import com.ouxuxi.service.CourseClassifyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseClassifyServiceImpl implements CourseClassifyService {

    @Resource
    private CourseClassifyDao courseClassifyDao;

    @Override
    public List<CourseClassifyList> getCourseClassifyList() {
        List<CourseClassifyList> courseClassifyLists=new ArrayList<CourseClassifyList>();
        //获取一级分类
        List<CourseClassify> listPatent =courseClassifyDao.queryCourseClassifyListByParent(0);
        //遍历将每个一级分类的二级分类加到集合中
        for(CourseClassify courseClassify:listPatent){
            //dto处理类
            CourseClassifyList courseClassifyList=new CourseClassifyList();
            //将一级分类放到dto处理类
            courseClassifyList.setCourseClassify(courseClassify);
            int id=(int)courseClassify.getClassifyId();
            //获取二级分类
            List<CourseClassify> list=courseClassifyDao.queryCourseClassifyListByParent(id);
            courseClassifyList.setList(list);
            courseClassifyLists.add(courseClassifyList);
        }
        return courseClassifyLists;
    }

    @Override
    public List<CourseClassify> getCourseClassifyParent() {
        List<CourseClassify> list=courseClassifyDao.queryCourseClassifyListByParent(0);
        return list;
    }

    @Override
    public List<CourseClassify> getCourseClassifyByParentId(int parentId) {
        return courseClassifyDao.queryCourseClassifyListByParent(parentId);
    }

    @Override
    public List<CourseClassify> getCourseClassify() {
        return courseClassifyDao.queryCourseClassify();
    }

    @Override
    public CourseClassify getCourseClassufyById(long classifyId) {
        return courseClassifyDao.queryCourseClassifyById(classifyId);
    }

    @Override
    public int addCourseClassufy(CourseClassify courseClassify) {
        return courseClassifyDao.addCourseClassify(courseClassify);
    }
}
