package com.ouxuxi.service.impl;

import com.ouxuxi.dao.NewsDao;
import com.ouxuxi.entity.News;
import com.ouxuxi.service.NewsService;
import com.ouxuxi.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsDao newsDao;

//查6条最多
    @Override
    public List<News> getNewsList() {
        return newsDao.queryNewsList(0,6);
    }

    @Override
    public List<News> getNewsAll(int pageIndex, int pageSize) {
        int page= PageCalculator.calculateRowIndex(pageIndex,pageSize);
        return newsDao.queryNewsListAll(page,pageSize);
    }

    @Override
    public int getCount() {
        return newsDao.newsCountAll();
    }

    @Override
    public int addNews(News news) {
        return newsDao.addNews(news);
    }

    @Override
    public News getById(long newsId) {
        return newsDao.queryNewsById(newsId);
    }

    @Override
    public int updateNews(News news) {
        return newsDao.updateNews(news);
    }

    @Override
    public int deleteNews(long newsId) {
        return newsDao.deleteNews(newsId);
    }
}
