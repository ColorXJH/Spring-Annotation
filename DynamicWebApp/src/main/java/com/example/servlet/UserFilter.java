package com.example.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Description:
 * @Author: ColorXJH
 * @CreateDate: 2022/11/12 16:05
 * @Version: 1.0
 */
public class UserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //过滤请求
        System.out.println("user-filter 执行了doFilter方法----");
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
