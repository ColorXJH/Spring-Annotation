package com.example;

import com.example.config.AppConfig;
import com.example.config.RootConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author ColorXJH
 * @version 1.0
 * @description: web容器启动的时候创建对象，调用方法来初始化容器以及前端控制器
 * @date 2022/11/14 15:40
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Description: 获取根容器的配置类（spring 的配置文件），父容器
     * @Author: ColorXJH
     * @Date: 2022/11/14 15:42
     * @param
     * @Return: java.lang.Class<?>[]
     **/
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }

    /**
     * Description: 获取web容器的配置类（springmvc配置文件）子容器
     * @Author: ColorXJH
     * @Date: 2022/11/14 15:42
     * @param
     * @Return: java.lang.Class<?>[]
     **/
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { AppConfig.class };
    }
    /**
     * Description: 获取DispatcherServlet前端控制器的映射信息
     *  / 拦截所有请求包括静态资源，但是不包括*.jsp
     *  /* 拦截所有请求，包括*.jsp也拦截,  jsp页面是tomcat引擎解析的
     * @Author: ColorXJH
     * @Date: 2022/11/14 15:43
     * @param
     * @Return: java.lang.String[]
     **/
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
