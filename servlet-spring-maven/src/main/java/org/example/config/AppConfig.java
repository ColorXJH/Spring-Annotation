package org.example.config;

import org.example.intercepter.MyIntercepter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author ColorXJH
 */
@ComponentScan(value = {"org.example.controller"},includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
        //springmvc只扫描@Controller,子容器
        //只扫描生效==》需要配置下面这个
},useDefaultFilters = false)
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

    //这个适配器过时了，可以参考最新的，思想是帮我们实现了，有需要自己复写

    //定制

    /**
     * Description: 视图解析器
     * @Author: ColorXJH
     * @Date: 2022/11/15 15:04
     * @param registry
     * @Return: void
     **/
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //registry.jsp();
        registry.jsp("/WEB-INF/views/",".jsp");
    }

    /**
     * Description: 静态资源访问
     * @Author: ColorXJH
     * @Date: 2022/11/15 16:02
     * @param configurer
     * @Return: void
     **/
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    /**
     * Description: 添加拦截器
     * @Author: ColorXJH
     * @Date: 2022/11/15 16:37
     * @param registry
     * @Return: void
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截任意请求
        registry.addInterceptor(new MyIntercepter()).addPathPatterns("/**");
    }
}
