package com.color.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/10/27 16:14
 */
@Component
public class Red implements ApplicationContextAware, BeanNameAware , EmbeddedValueResolverAware {
    private ApplicationContext context;
    @Override
    public void setBeanName(String name) {
        System.out.println("--当前bean的妹子是--"+name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("--传入的ioc容器是--"+applicationContext);
        this.context=applicationContext;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        String stringValue = stringValueResolver.resolveStringValue("你好${os.name},我是#{20-2}");
        System.out.println("--解析的字符串是：--"+stringValue);
    }
}
