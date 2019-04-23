package com.ouxuxi.dao;

import com.ouxuxi.entity.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsDao {
    int addNews(@Param("news") News news);
    int deleteNews(long newsId);
    List<News> queryNewsList(@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);
    List<News> queryNewsListAll(@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);
    int updateNews(@Param("news") News news);
    News queryNewsById(long newsId);
    Integer newsCount();
    Integer newsCountAll();
}
