package com.ouxuxi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class News {
    private long newsId;
    private String newsName;
    private String newsImage;
    private String newsUrl;
    private int newsPriority;
    private int newsEnable;
    private Date newsCreateTime;
    private Date newsUpdateTime;
    private int del;
}
