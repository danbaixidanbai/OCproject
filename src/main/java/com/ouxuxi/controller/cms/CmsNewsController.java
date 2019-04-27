package com.ouxuxi.controller.cms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouxuxi.entity.News;
import com.ouxuxi.service.NewsService;
import com.ouxuxi.util.HttpServletRequestUtil;
import com.ouxuxi.util.PageCalculator;
import com.ouxuxi.util.QiniuCloudUtil;
import com.qiniu.common.QiniuException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/cms/news")
public class CmsNewsController {

    @Resource
    private NewsService newsService;


    @GetMapping(value = "/getnewsinfo")
    private Map<String, Object> getNewsInfo(long newsId)throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if(newsId>0){
            News news=newsService.getById(newsId);
            if(news!=null){
                map.put("errCode", 1);
                map.put("news", news);
                return map;
            }else{
                map.put("errCode", 2);
                map.put("errMsg", "加载轮播图失败");
                return map;
            }
        }else{
            map.put("errCode", 2);
            map.put("errMsg", "加载newsId失败");
            return map;
        }
    }
    @PostMapping(value = "/editnews")
    private Map<String, Object> editNews(HttpServletRequest request)throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String newsStr = HttpServletRequestUtil.getString(request, "newsStr");
        String imageKey=HttpServletRequestUtil.getString(request, "imageKey");
        boolean flag=false;
        ObjectMapper mapper = new ObjectMapper();
        News news = null;
        news = mapper.readValue(newsStr, News.class);
        MultipartHttpServletRequest mulRequest = request instanceof MultipartHttpServletRequest ? (MultipartHttpServletRequest) request : null;
        MultipartFile file = mulRequest.getFile("newsImage");
        if (file != null){
            byte[] bytes = file.getBytes();
            String newsImage = QiniuCloudUtil.upLoadImage(bytes);
            if (newsImage != null && !newsImage.equals("")) {
                news.setNewsImage(newsImage);
                flag=true;
            }
        }

        news.setNewsUpdateTime(new Date());
        int num=newsService.updateNews(news);
        if(num>0){
            if(flag){
                QiniuCloudUtil.deleteImage(imageKey);
            }
            map.put("errCode",1);
            return map;
        }else{
            map.put("errCode",3);
            map.put("errMsg","修改轮播图失败");
            return map;
        }
    }

    @PostMapping(value = "/addnews")
    private Map<String, Object> addNews(HttpServletRequest request)throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String newsStr = HttpServletRequestUtil.getString(request, "newsStr");
        ObjectMapper mapper = new ObjectMapper();
        News news = null;
        news = mapper.readValue(newsStr, News.class);
        MultipartHttpServletRequest mulRequest = request instanceof MultipartHttpServletRequest ? (MultipartHttpServletRequest) request : null;
        MultipartFile file = mulRequest.getFile("newsImage");
        if (file != null){
            byte[] bytes = file.getBytes();
            String newsImage = QiniuCloudUtil.upLoadImage(bytes);
            if (newsImage != null && !newsImage.equals("")) {
                news.setNewsImage(newsImage);
            } else {
                map.put("errCode", 2);
                map.put("errMsg", "图片上传失败");
                return map;
            }
        }else{
            map.put("errCode",4);
            map.put("errMsg","图片不能为空！");
            return map;
        }
        news.setNewsCreateTime(new Date());
        news.setNewsUpdateTime(new Date());
        int num=newsService.addNews(news);
        if(num>0){
            map.put("errCode",1);
            return map;
        }else{
            map.put("errCode",3);
            map.put("errMsg","添加轮播图失败");
            return map;
        }
    }
    @GetMapping(value = "/getpagelist")
    private Map<String, Object> getPageList(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex > 0 && pageSize > 0) {
            List<News> list = newsService.getNewsAll(pageIndex, pageSize);
            map.put("list",list);
            int count=newsService.getCount();
            int pageCount= PageCalculator.calculatePageCount(count,pageSize);
            map.put("pageCount",pageCount);

        }else{
            map.put("errCode", 3);
            map.put("errMsg", "加载页数失败");
            return map;
        }
        map.put("errCode",0);
        return map;
    }

    @PostMapping(value = "/deletenews")
    private Map<String, Object> deleteNews(long newsId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String imageKey="";
        if(newsId>0){
            News newsImage=newsService.getById(newsId);
            if(newsImage!=null&&newsImage.getNewsImage()!=null){
                imageKey=newsImage.getNewsImage();
            }
            int num=newsService.deleteNews(newsId);
            if(num>0){
                QiniuCloudUtil.deleteImage(imageKey);
                map.put("errCode", 0);
                return map;
            }else{
                map.put("errCode", 3);
                map.put("errMsg", "删除轮播图失败");
                return map;
            }
        }else{
            map.put("errCode", 2);
            map.put("errMsg", "加载newsId失败");
            return map;
        }
    }
}