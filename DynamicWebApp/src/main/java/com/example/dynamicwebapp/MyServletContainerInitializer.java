package com.example.dynamicwebapp;

import com.example.service.HelloService;
import com.example.servlet.UserFilter;
import com.example.servlet.UserListener;
import com.example.servlet.UserServlet;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.Set;

/**
 * @Description:
 * @Author: ColorXJH
 * @CreateDate: 2022/11/12 14:28
 * @Version: 1.0
 */
//容器启动的时候，会将@HandlesTypes指定的类型下面的子类，实现类，子接口 传递过来
@HandlesTypes(value = {HelloService.class})//传入感兴趣的类型
public class MyServletContainerInitializer implements ServletContainerInitializer {
    /**
     * Description: 应用启动的时候会运行该方法
     * 1：使用servletContext注册web组件（servlet,filter,listener）
     *      必须是在项目启动的时候
     *          1：ServletContainerInitializer得到的servletContext
     *          2：ServletContextEvent事件对象（监听初始的时候）
     *              ServletContextListener的contextInitialized(ServletContextEvent sce)方法
     *              //这里也能拿到servletContext对象，只有在这两处才能注册组件
     *              ServletContext servletContext = sce.getServletContext();
     * 2：在运行时给ServletContext注册组件是不行的，处于安全考虑
     * @Author: ColorXJH
     * @Date: 2022/11/12 14:32
     * @param set 传入感兴趣的类型的所有子类型
     * @param servletContext 当前web应用的servletContext对象，一个web应用对应一个servletContext
     * @Return: void
     **/
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {

        System.out.println("感兴趣的内容为++++");
        for (Class<?> c:set
             ) {
            //还可以在这里使用反射为感兴趣的类型创建对象
            System.out.println(c);
        }
        //注册组件
        ServletRegistration.Dynamic userServlet = servletContext.addServlet("userServlet", new UserServlet());
        userServlet.addMapping("/user");

        servletContext.addListener(UserListener.class);

        FilterRegistration.Dynamic userFilter=servletContext.addFilter("userFilter", UserFilter.class);
        //拦截所有信息
        userFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");
    }
}
