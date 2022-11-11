package com.color;

import com.color.bean.Person;
import com.color.config.MyConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/10/21 9:31
 */
public class MainTest {

    public static void main(String[] args) {
        /*ApplicationContext ApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Person person = (Person)ApplicationContext.getBean("person");
        System.out.println(person);*/

        ApplicationContext ApplicationContext=new AnnotationConfigApplicationContext(MyConfig.class);
        Person person =ApplicationContext.getBean(Person.class);
        String[] beanNamesForType = ApplicationContext.getBeanNamesForType(Person.class);
        System.out.println(person);
        for (String name:beanNamesForType) {
            System.out.println(name);
        }
    }
}
