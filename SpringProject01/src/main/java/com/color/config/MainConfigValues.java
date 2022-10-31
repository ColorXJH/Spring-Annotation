package com.color.config;

import com.color.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ColorXJH
 * @version 1.0
 * @description: spring对外部配置文件的支持
 * @date 2022/10/31 16:27
 */
@Configuration
//加载外部配置文件，使用${}读取内容
@PropertySource(value = {"classpath:/person.properties"})
public class MainConfigValues {
    @Bean
    public Person person(){
        return new Person();
    }
}
