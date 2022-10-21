package com.color.config;

import com.color.bean.Person;
import com.color.service.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/10/21 9:35
 */

//配置类==配置文件
@Configuration  //告诉spring 这是一个配置
/*@ComponentScan(value = "com.color",excludeFilters = {//排除@Controller @Service注解
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class, Service.class})
})*/
@ComponentScan(value = "com.color",includeFilters = {//只要@Controller,需要配置 useDefaultFilters = false
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
        //指定特定类型的加载到容器
        ,@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,classes = {MyService.class})
        //自定义过滤类型
        ,@ComponentScan.Filter(type=FilterType.CUSTOM,classes = {MyTypeFilter.class})
},useDefaultFilters = false)
public class MyConfig {

    @Bean(value="person123")
    public Person person1(){
        return new Person("Color",29,"安徽广德");
    }
}
