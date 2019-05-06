package com.ouxuxi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CourseSession {
    private long courseSessionId;
    private Course course;
    private int courseSessionPriority;
    private String courseSessionName;
    private int parent;
    private int courseSessionStatus;
    private String courseSessionVideoUrl;
    private Date courseSessionCreateTime;
    private Date courseSessionUpdateTime;
    private int del;
}
