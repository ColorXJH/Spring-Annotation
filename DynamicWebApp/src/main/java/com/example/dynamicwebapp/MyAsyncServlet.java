package com.example.dynamicwebapp;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/11/16 9:20
 */
@WebServlet(value = "/async",asyncSupported = true)//异步支持
public class MyAsyncServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //支持异步处理：asyncSupported = true
        //开启异步
        System.out.println("主线程开始---"+Thread.currentThread().getName()+"时间"+System.currentTimeMillis());
        AsyncContext asyncContext = req.startAsync();
        //业务逻辑异步处理，开始异步处理
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("副线程开始---"+Thread.currentThread().getName()+"时间"+System.currentTimeMillis());
                    sayHello();
                    //获取到异步的上下文，也可以直接使用上面的
                    AsyncContext context = req.getAsyncContext();
                    //获取响应
                    ServletResponse response = context.getResponse();
                    response.getWriter().write("hello-async");
                    asyncContext.complete();
                    System.out.println("副线程结束---"+Thread.currentThread().getName()+"时间"+System.currentTimeMillis());
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("主线程结束---"+Thread.currentThread().getName()+"时间"+System.currentTimeMillis());
    }

    public void sayHello() throws InterruptedException {
        System.out.println(Thread.currentThread()+"doing----");
        Thread.sleep(3000);
    }

}
