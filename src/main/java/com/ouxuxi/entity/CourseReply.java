package com.ouxuxi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CourseReply {
    private long replyId;
    private String replyContent;
    private long commentId;
    //回复对应回复id,为0说明根回复
    private long toReplyId;
    private User user;
    private User toUser;
    private Date createTime;
    private Date updateTime;
}
