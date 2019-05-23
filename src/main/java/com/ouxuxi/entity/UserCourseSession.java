package com.ouxuxi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserCourseSession {
    private long userCourseSessionId;
    private long userId;
    private long courseId;
    private long courseSessionId;
    private int status;
    private int rate;
    private Date createTime;
    private Date updateTime;
}
