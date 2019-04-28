package com.ouxuxi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Course {
    private long courseId;
    private String courseName;
    private String courseImage;
    private CourseClassify courseClassify;
    private CourseClassify courseClassifyParent;
    private User user;
    private int courseCount;
    private int coursePriority;
    private String courseContent;
    private String courseTime;//时长
    private Date courseCreateTime;
    private Date courseUpdateTime;
    private int del;//是否上架
}
