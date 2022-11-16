package com.example.dynamicwebapp;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ColorXJH
 */
@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    @Override
    public void init() {
        message = "Hello World123!";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println(Thread.currentThread()+"start----");
        try {
            sayHello();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
        System.out.println(Thread.currentThread()+"end----");
    }

    @Override
    public void destroy() {
    }


    public void sayHello() throws InterruptedException {
        System.out.println(Thread.currentThread()+"doing----");
        Thread.sleep(3000);
    }
}