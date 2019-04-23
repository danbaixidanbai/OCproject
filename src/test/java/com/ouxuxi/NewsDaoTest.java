package com.ouxuxi;
import com.ouxuxi.dao.NewsDao;
import com.ouxuxi.entity.News;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsDaoTest {

    @Resource
    private NewsDao newsDao;

    @Test
    public void testAddNews(){
        News news=new News();
        news.setNewsName("news5");
        news.setNewsPriority(1);
        news.setNewsImage("c5.jpg");
        news.setNewsUrl("http://www.baidu.com");
        news.setNewsEnable(1);
        news.setNewsCreateTime(new Date());
        news.setNewsUpdateTime(new Date());
        int num=newsDao.addNews(news);
        System.out.println("num:"+num);
        System.out.println("newsID:"+news.getNewsId());
    }

    @Test
    public void testUpdateNews(){
        News news=new News();
        news.setNewsId(2);
        news.setNewsName("news2");
        news.setNewsPriority(1);
        news.setNewsImage("c2.jpg");
        news.setNewsUrl("http://www.baidu.com");
        news.setNewsEnable(1);
        //news.setNewsCreateTime(new Date());
        news.setNewsUpdateTime(new Date());
        int num=newsDao.updateNews(news);
        System.out.println("num:"+num);
        System.out.println("newsID:"+news.getNewsId());
    }

    @Test
    public void testDeleteNews(){
        int num=newsDao.deleteNews(3);
        System.out.println("num:"+num);

    }
    @Test
    public void testQuery(){
        News news=newsDao.queryNewsById(1);
        System.out.println(news.toString());
    }
    @Test
    public void testQueryAll() {
        int count=newsDao.newsCountAll();
        List<News> list=newsDao.queryNewsListAll(0,2);
        System.out.println("count:"+count);
        System.out.println("size:"+list.size());
    }

    @Test
    public void testQueryEnable() {
        int count=newsDao.newsCount();
        List<News> list=newsDao.queryNewsList(0,2);
        System.out.println("count:"+count);
        System.out.println("size:"+list.size());
    }
}
