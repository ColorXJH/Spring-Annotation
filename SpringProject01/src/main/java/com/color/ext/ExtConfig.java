package com.color.ext;

import com.color.bean.Blue;
import com.color.service.MyService;
import com.color.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @author ColorXJH
 * @version 1.0
 * @description: spring的扩展原理
 * @date 2022/11/4 11:47
 */
@Configuration
@ComponentScan(value = {"com.color.ext","com.color.service"},excludeFilters ={
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = MyService.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = UserService.class)
})
public class ExtConfig {
    @Bean
    public Blue blue(){
        return new Blue();
    }


}
