package com.color.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/10/31 15:21
 */
@Component
public class Pig  implements BeanPostProcessor {
    //初始化之前进行一些后置处理操作（在任何初始化回调之前工作）
    /**
     * Description: 
     * @Author: ColorXJH
     * @Date: 2022/10/31 15:32
     * @param bean 刚创建的实例
     * @param beanName 实例在容器中的码字
     * @Return: java.lang.Object 可以返回源对象，也可以返回包装对象
     **/
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("--postProcessBeforeInitialization--");
        System.out.println(bean+beanName);
        return bean;
    }

    //初始化之后进行一些后置处理操作（在自定义的初始化方法调用之后工作）
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("--postProcessAfterInitialization--");
        System.out.println(bean+beanName);
        return bean;
    }
}
