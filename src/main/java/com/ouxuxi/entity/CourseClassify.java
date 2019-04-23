package com.ouxuxi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CourseClassify {
    private long classifyId;
    private String  classifyName;
    private int classifyPriority;
    private Date classifyCreateTime;
    private Date classifyUpdateTime;
    private long parent;//父级别
    private int del;
}
