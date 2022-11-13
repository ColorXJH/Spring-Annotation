package com.example.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Description:  该listener监听项目启动或停止
 * @Author: ColorXJH
 * @CreateDate: 2022/11/12 16:07
 * @Version: 1.0
 */
public class UserListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("--contextInitialized--");
        //这里也能拿到servletContext对象，只有在这两处才能注册组件
        ServletContext servletContext = sce.getServletContext();
        ServletContextListener.super.contextInitialized(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("--contextDestroyed--");
        ServletContextListener.super.contextDestroyed(sce);
    }
}
