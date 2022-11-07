package com.color.service;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/11/7 13:46
 */
@Service
public class EventListenerService {
    /**
     * Description: classes要监听的事件类型
     * @Author: ColorXJH
     * @Date: 2022/11/7 13:48
     * @param event  事件发生以后需要拿到整个事件，
     * @Return: void
     **/
    @EventListener(classes = {ApplicationEvent.class})
    public void listen(ApplicationEvent event){
        System.out.println("--我的事件监听--event123--");
    }
}
