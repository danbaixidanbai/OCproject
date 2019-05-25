package com.ouxuxi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserCollections {
    private long collectionsId;
    private User user;
    private Course course;
    private String tips;
    private Date collectionsCreateTime;
    private Date collectionsUpdateTime;
    private int del;
}
