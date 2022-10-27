package com.color.condition;

import com.color.bean.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/10/27 16:36
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     * Description: 把需要添加到容器中的bean,调用BeanDefinitionRegistry.registerBeanDefinition的方法手工注册进来
     * @Author: ColorXJH
     * @Date: 2022/10/27 16:37
     * @param annotationMetadata:当前类的注解信息
     * @param beanDefinitionRegistry：BeanDefinition注册类
     * @Return: void
     **/
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        boolean red = beanDefinitionRegistry.containsBeanDefinition("com.color.bean.Color");
        if(red){
            //指定bean名称，bean定义信息
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(RainBow.class);
            beanDefinitionRegistry.registerBeanDefinition("rainbow",rootBeanDefinition);
        }
    }
}
