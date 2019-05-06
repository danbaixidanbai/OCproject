package com.ouxuxi.dto;

import com.ouxuxi.entity.CourseSession;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CourseSessionDto {
    private CourseSession courseSession;
    private List<CourseSession> list;
}
