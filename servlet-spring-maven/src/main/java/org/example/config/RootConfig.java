package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/11/14 15:47
 */
@ComponentScan(value = "org.example.controller",excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
        //spring的容器不扫描@Controller，父容器
})
public class RootConfig {
}
