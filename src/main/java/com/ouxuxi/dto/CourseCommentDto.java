package com.ouxuxi.dto;

import com.ouxuxi.entity.CourseComment;
import com.ouxuxi.entity.CourseReply;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CourseCommentDto {
    private CourseComment courseComment;
    private List<CourseReply> list;
}
