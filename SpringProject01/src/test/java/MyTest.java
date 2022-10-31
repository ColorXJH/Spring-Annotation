import com.color.bean.Person;
import com.color.config.*;
import com.color.controller.MyController;
import com.color.repository.MyRepository;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/10/20 16:53
 */
public class MyTest {
    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig.class);
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name:names){
            System.out.println(name);
        }
    }

    @Test
    public void test02(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig2.class);
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name:names){
            System.out.println(name);
        }
        Object person=applicationContext.getBean("person");
        Object person2=applicationContext.getBean("person");
        System.out.println(person==person2);

    }


    @Test
    public void test03(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig2.class);
        Map<String, Person> beansOfType = applicationContext.getBeansOfType(Person.class);
        System.out.println(beansOfType);
    }

    @Test
    public void testImport(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyConfig2.class);
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        for (String name:beanDefinitionNames
             ) {
            System.out.println(name);
        }
    }

    @Test
    public void testFactoryBean(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyConfig2.class);
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        for (String name:beanDefinitionNames
        ) {
            System.out.println(name);
        }
        //工厂bean获取的是调用getObject方法创建的对象
        Object colorFactoryBean = annotationConfigApplicationContext.getBean("colorFactoryBean");
        Object colorFactoryBean1 = annotationConfigApplicationContext.getBean("colorFactoryBean");

        System.out.println(colorFactoryBean.getClass());
        System.out.println(colorFactoryBean==colorFactoryBean1);
        //获取工厂bean本身
        Object FactoryBeanOriginal = annotationConfigApplicationContext.getBean("&colorFactoryBean");
        System.out.println(FactoryBeanOriginal.getClass());
    }
    @Test
    public void testLifeCycle(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfigLifeCycle.class);
        //关闭容器
        annotationConfigApplicationContext.close();
    }
    @Test
    public void testPropertyValues(){
        AnnotationConfigApplicationContext application = new AnnotationConfigApplicationContext(MainConfigValues.class);
        String[] beanDefinitionNames = application.getBeanDefinitionNames();
        String namess=application.getEnvironment().getProperty("person.addr");
        System.out.println("--也可以从环境变量中获取配置文件的值--"+namess);
        System.out.println(application.getBean("person"));
        for (String name:beanDefinitionNames
        ) {
            System.out.println(name);
        }
    }
    @Test
    public void testAutowired(){
        AnnotationConfigApplicationContext application = new AnnotationConfigApplicationContext(MainConfigAutowired.class);
        MyController bean = application.getBean(MyController.class);
        MyRepository bean1 = application.getBean(MyRepository.class);
        System.out.println(bean);
        System.out.println(bean1);
    }
}
