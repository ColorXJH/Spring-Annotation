package org.example.controller;

import org.springframework.web.context.request.async.DeferredResult;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author ColorXJH
 * @version 1.0
 * @description: 消息处理队列
 * @date 2022/11/16 10:22
 */
public class DeferredResultQueue {
    private static Queue<DeferredResult<Object>>queue=new ConcurrentLinkedQueue<>();
    public static void save(DeferredResult<Object> deferredResult){
        queue.add(deferredResult);
    }

    public static DeferredResult<Object>get(){
        return queue.poll();
    }
}
