package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/11/14 15:55
 */
@Controller
public class HelloController {
    @Autowired
    HelloService helloService;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        String hello=helloService.sayHello("ColorXJH");
        return hello;
    }


    @RequestMapping("/app/hello2")
    @ResponseBody
    public String hello2(){
        String hello=helloService.sayHello("ColorXJH2");
        return hello;
    }
}
