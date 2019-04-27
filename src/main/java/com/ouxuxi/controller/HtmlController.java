package com.ouxuxi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


//负责html路由的controller
@Controller
public class HtmlController {

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/list")
    public String list(){
        return "list";
    }

    @RequestMapping(value = "/testcode")
    public String testcode(){
        return "testcode";
    }

    @RequestMapping(value = "/register")
    public String register(){
        return "user/register";
    }
    @RequestMapping(value = "/login")
    public String login(){
        return "user/login";
    }
    @RequestMapping(value = "/info")
    public String info(){
        return "user/info";
    }

    @RequestMapping(value = "/cms/cmsindex")
    public String cmsIndex(){
        return "cms/cmsindex";
    }

    @RequestMapping(value = "/cms/cmslogin")
    public String cmsLogin(){
        return "cms/cmslogin";
    }

    @RequestMapping(value = "/cms/news/pagelist")
    public String newsPageList(){
        return "cms/news/pagelist";
    }

    @RequestMapping(value = "/cms/news/newsinfo")
    public String newsInfo(){
        return "cms/news/newsinfo";
    }
}
