package com.color.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/11/7 11:14
 */
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
    /**
     * Description: 当容器中发布此事件以后，方法触发
     * @Author: ColorXJH
     * @Date: 2022/11/7 11:15
     * @param applicationEvent
     * @Return: void
     **/
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        System.out.println("收到事件-----"+applicationEvent);

    }
}
