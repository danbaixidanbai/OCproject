package com.ouxuxi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CourseComment {
    private long courseCommentId;
    private String courseCommentContent;
    private long courseId;
    private long courseSessionId;
    private User user;
    private Date createTime;
    private Date updateTime;
}
