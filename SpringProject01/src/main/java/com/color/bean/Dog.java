package com.color.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/10/31 14:38
 */
@Component
public class Dog implements ApplicationContextAware {
    private ApplicationContext context;
    public Dog(){
        System.out.println("--dog construct--");
    }
    //对象创建并赋值之后掉用
    @PostConstruct
    public void method1(){
        System.out.println("--post construct--");
    }

    //容器移除对象之前
    @PreDestroy
    public void method2(){
        System.out.println("--pre destroy--");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
     this.context=applicationContext;//保存ioc容器，其他方法就能使用到ioc容器
    }
}
