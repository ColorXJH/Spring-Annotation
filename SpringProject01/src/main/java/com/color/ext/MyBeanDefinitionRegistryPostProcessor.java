package com.color.ext;

import com.color.bean.Color;
import com.color.bean.Yellow;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/11/7 10:08
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    /**
     * Description: 
     * @Author: ColorXJH
     * @Date: 2022/11/7 10:30
     * @param registry:bean定义信息的保存中心，以后beanFactory就是按照BeanDefinitionRegistry里面保存的每一个bean信息，创建bean实例
     * @Return: void
     **/
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("-xjh-postProcessBeanDefinitionRegistry---bean的数量"+registry.getBeanDefinitionCount());
        //自定义一个bean类型
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Yellow.class);
        registry.registerBeanDefinition("hello",rootBeanDefinition);
        //也可以创建一个构建器builder,与上方的两行代码效果是一样的
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Color.class).getBeanDefinition();
        registry.registerBeanDefinition("yellow",beanDefinition);
    }
    /**
     * Description: 
     * @Author: ColorXJH
     * @Date: 2022/11/7 10:29
     * @param beanFactory bean工厂信息
     * @Return: void
     **/
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("-xjh-postProcessBeanFactory----bean的数量"+beanFactory.getBeanDefinitionCount());
    }
}
