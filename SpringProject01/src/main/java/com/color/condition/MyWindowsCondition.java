package com.color.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:自定义装配bean所需要的条件
 * @date 2022/10/27 12:28
 */
public class MyWindowsCondition implements Condition {
    /**
     * Description:
     * @Author: ColorXJH
     * @Date: 2022/10/27 12:30
     * @param conditionContext 判断条件能使用的上下文环境
     * @param annotatedTypeMetadata 注释信息
     * @Return: boolean
     **/
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //1：能获取到ioc使用的beanFactory
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        //2:获取到类加载器
        ClassLoader classLoader = conditionContext.getClassLoader();
        //3:获取当前环境信息
        Environment environment = conditionContext.getEnvironment();
        //4:获取到bean定义的注册类
        BeanDefinitionRegistry registry = conditionContext.getRegistry();

        String property = environment.getProperty("os.name");
        if(property.contains("Windows")){
            return true;
        }else{
            return false;
        }
    }
}
