package com.color.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author ColorXJH
 * @version 1.0
 * @description: BeanFactory的后置处理器
 * @date 2022/11/7 9:31
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    /**
     * Description: bean定义以加载，但是还未创建初始化实例的时候调用
     * @Author: ColorXJH
     * @Date: 2022/11/7 9:32
     * @param beanFactory
     * @Return: void
     **/
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor执行了postProcessBeanFactory方法");
        int count = beanFactory.getBeanDefinitionCount();
        String[] names = beanFactory.getBeanDefinitionNames();
        System.out.println("当前beanFactory中有"+count+"个bean:");
        System.out.println(Arrays.asList(names));
    }
}
