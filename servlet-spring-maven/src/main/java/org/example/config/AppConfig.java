package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @author ColorXJH
 */
@ComponentScan(value = "org.example.controller",includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
        //springmvc只扫描@Controller,子容器
        //只扫描生效==》需要配置下面这个
},useDefaultFilters = false)
public class AppConfig {


}
