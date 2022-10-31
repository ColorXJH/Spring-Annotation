package com.color.controller;

import com.color.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/10/20 16:53
 */
@Controller
public class MyController {
    @Autowired
    private MyService myService;

    @Override
    public String toString() {
        return "MyController{" +
                "myService=" + myService +
                '}';
    }
}
