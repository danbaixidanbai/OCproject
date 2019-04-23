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
}
