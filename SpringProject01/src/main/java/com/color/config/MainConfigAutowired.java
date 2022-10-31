package com.color.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ColorXJH
 * @version 1.0
 * @description: 自动装配，DI,完成对IOC容器中各个组件的依赖关系赋值
 * @date 2022/10/31 16:44
 */
@Configuration
@ComponentScan({"com.color.controller","com.color.service","com.color.repository"})
public class MainConfigAutowired {
}
