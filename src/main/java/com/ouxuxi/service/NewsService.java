package com.ouxuxi.service;

import com.ouxuxi.entity.News;

import java.util.List;


public interface NewsService {
    List<News> getNewsList();
    List<News> getNewsAll(int pageIndex,int pageSize);
    int  getCount();
    int addNews(News news);
    News getById(long newsId);
    int updateNews(News news);
    int deleteNews(long newsId);

}
