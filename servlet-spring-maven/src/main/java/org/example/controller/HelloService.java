package org.example.controller;


import org.springframework.stereotype.Service;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/11/14 15:57
 */
@Service
public class HelloService {

    public String sayHello(String name){
        return "hello"+name;
    }
}
