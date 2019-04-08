package com.ouxuxi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


//负责html路由的controller
@Controller
public class IndexController {

    @RequestMapping(value = "/index")
    public String test(){
        return "index";
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
}
