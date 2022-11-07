package com.color.ext;

import com.color.bean.Blue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ColorXJH
 * @version 1.0
 * @description: spring的扩展原理
 * @date 2022/11/4 11:47
 */
@Configuration
@ComponentScan("com.color.ext")
public class ExtConfig {
    @Bean
    public Blue blue(){
        return new Blue();
    }


}
