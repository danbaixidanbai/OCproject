package com.ouxuxi.dto;

import com.ouxuxi.entity.CourseClassify;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CourseClassifyList {

    private CourseClassify courseClassify;
    private List<CourseClassify> list;
}
