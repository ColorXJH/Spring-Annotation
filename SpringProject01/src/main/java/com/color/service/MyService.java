package com.color.service;

import com.color.bean.Blue;
import com.color.bean.Dog;
import com.color.bean.MySchool;
import com.color.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/10/21 9:56
 */
@Service
public class MyService {
    @Autowired
    //@Qualifier("myRepository2")//指定使用组件的id,而不是默认使用属性名==>@Primary标注的bean
    //@Qualifier比@Primary优先级更高，同时使用则以@Qualifier为准
    private MyRepository myRepository;

    @Autowired(required = false)//自动注入bean,如果没有required = false，则汇报该bean组件没有被定义，这个属性表示这个组件不是必须被注入的
    //有则注入，无则不注入
    private Blue blue;

    @Resource
    private Dog dog;

    @Inject
    private MySchool mySchool;

    @Override
    public String toString() {
        return "MyService{" +
                "myRepository=" + myRepository +
                '}';
    }
}
