package com.ouxuxi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserFollow {
    private long userFollowId;
    private long userId;
    private User follower;
    private Date createTime;
    private Date updateTime;
}
