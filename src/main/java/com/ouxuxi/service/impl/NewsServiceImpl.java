package com.ouxuxi.service.impl;

import com.ouxuxi.dao.NewsDao;
import com.ouxuxi.entity.News;
import com.ouxuxi.service.NewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsDao newsDao;


    @Override
    public List<News> getNewsList() {
        return newsDao.queryNewsList(0,6);
    }
}
