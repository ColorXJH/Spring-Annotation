package com.color.config;

import com.color.bean.Color;
import com.color.bean.Person;
import com.color.bean.Red;
import com.color.condition.MyImportBeanDefinitionRegistrar;
import com.color.condition.MyImportSelector;
import com.color.condition.MyLinuxCondition;
import com.color.condition.MyWindowsCondition;
import org.springframework.context.annotation.*;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/10/26 9:49
 */
@Configuration
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})//bean id默认是组件的全类名
public class MyConfig2 {
    //默认单实例
    @Bean("person")
    @Lazy
    //@Scope("prototype")
    //默认单实例,创建上下文环境是自动创建获取bean，以后每次获取都是从容器中拿，prototype是在创建上下文时不创建bean
    //在获取bean时，才重新创建bean对象，该注解用于调整作用域
    public Person person(){
        System.out.println("是否懒加载----");
        return new Person("color",26,"广德");
    }
    @Conditional({MyWindowsCondition.class})
    @Bean("bill")
    public Person person02(){
        return new Person("bill gates",66,"san");
    }
    @Conditional({MyLinuxCondition.class})
    @Bean("linux")
    public Person person03(){
        return new Person("linux",50,"san");
    }



}
