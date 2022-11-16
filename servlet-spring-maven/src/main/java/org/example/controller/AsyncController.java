package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * @author ColorXJH
 * @version 1.0
 * @description: spring基于servlet的异步处理
 * @date 2022/11/16 9:48
 */
@Controller
public class AsyncController {

    /**
     * Description: 异步请求处理
     * 1:控制返回Callable
     * 2:spring异步处理，将Callable提交到TaskExecutor，使用一个隔离的线程进行执行
     * 3:DispatcherServlet和所有的filter退出web容器的线程，但是response依旧保持打开状态
     * 4:Callable返回结果，springmvc将请求重新派发给容器，回复之前的处理
     * 5:根据Callable返回结果，springmvc继续进行视图渲染流程，等，从收清求-》视图渲染，从头开始进行
     *      异步的拦截器：
     *          1:原生API的AsyncListener
     *          2:springmvc实现AsyncHandlerInterceptor
     * @Author: ColorXJH
     * @Date: 2022/11/16 9:59
     * @param 
     * @Return: java.util.concurrent.Callable<java.lang.String>
     **/

    @RequestMapping("/async01")
    @ResponseBody
    public Callable<String> async01(){
        System.out.println("主线程开始----"+Thread.currentThread().getName()+"----"+System.currentTimeMillis());
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("副线程开始----" + Thread.currentThread().getName() + "----" + System.currentTimeMillis());
                Thread.sleep(3000);
                System.out.println("副线程结束----" + Thread.currentThread().getName() + "----" + System.currentTimeMillis());
                return "async01";
            }
        };
        System.out.println("主线程结束----"+Thread.currentThread().getName()+"----"+System.currentTimeMillis());
        return callable;
    }


    /**
     * Description: 创建订单流程-》线程1创建订单的消息，将消息放在中间件，线程2监听消息中间件，当
     * 有消息声称是则线程2创建订单，并将结果返回给线程1，即线程1不知道任务什么完成，需要看后续线程，参考springmvc异步文档
     * @Author: ColorXJH
     * @Date: 2022/11/16 10:16
     * @param 
     * @Return: org.springframework.web.context.request.async.DeferredResult<java.lang.Object>
     **/
    @RequestMapping("/createOrder")
    @ResponseBody
    public DeferredResult<Object>createOrder(){
        DeferredResult<Object>deferredResult=new DeferredResult<>((long)3000,"createFail");
        //这里相当于保存消息到另外一个线程，让后续来创建订单，这里等待
        DeferredResultQueue.save(deferredResult);
        return deferredResult;
    }

    /**
     * Description: 相当于这里创建订单
     * @Author: ColorXJH
     * @Date: 2022/11/16 10:26
     * @param
     * @Return: java.lang.String
     **/
    @RequestMapping("/create")
    @ResponseBody
    public String create(){
        String s = UUID.randomUUID().toString();
        DeferredResult<Object> deferredResult = DeferredResultQueue.get();
        //这里只要deferredResult执行了setResult，就能监听到，createOrder()方法就能得到返回
        deferredResult.setResult(s);
        return "success==》"+s;
    }
}
