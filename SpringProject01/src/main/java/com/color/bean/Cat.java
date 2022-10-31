package com.color.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/10/31 14:19
 */
@Component
public class Cat implements InitializingBean, DisposableBean {
    //销毁方法
    @Override
    public void destroy() throws Exception {
        System.out.println("--cat destroy--");
    }
    //初始化方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("--cat afterPropertiesSet--");
    }
}
