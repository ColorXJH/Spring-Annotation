package org.example.config;

import org.example.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * Hello world!
 *
 */
@ComponentScan(value = "org.example.config",includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
        //springmvc只扫描@Controller,子容器
        //只扫描生效==》需要配置下面这个
},useDefaultFilters = false)
public class App 
{
    @Bean
    public Person person(){
        return new Person("张三","安徽");
    }
}
