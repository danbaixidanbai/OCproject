package com.ouxuxi.controller;

import com.ouxuxi.util.QiniuCloudUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class QiNiuController {

    @PostMapping(value = "/api/test/qiniu")
    public Map<String,Object> testQiniu(@RequestParam MultipartFile image, HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        if(image.isEmpty()) {
            map.put("success",false);
            map.put("msgCode",400);
            map.put("message","文件为空，请重新上传");
            return map;
        }
        try{
            byte[] bytes = image.getBytes();
            QiniuCloudUtil qiniuUtil = new QiniuCloudUtil();
            String url=qiniuUtil.upLoadImage(bytes);
            map.put("success",true);
            map.put("url",url);
            return map;
        }catch (Exception e){
            map.put("success",false);
            map.put("msgCode",500);
            map.put("message","文件上传发生异常！");
            return map;
        }

    }
    @GetMapping(value = "/hello")
    public String hello(){
        return "helloasd";
    }
}
