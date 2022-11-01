import com.color.bean.Person;
import com.color.config.*;
import com.color.controller.BossController;
import com.color.controller.MyController;
import com.color.repository.MyRepository;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
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
        MyRepository bean2 = (MyRepository)application.getBean("myRepository2");
        Object dog = application.getBean("dog");
        Object mySchool = application.getBean("mySchool");
        //无论如何这一行是肯定要报错的，
        //MyRepository bean3 = application.getBean(MyRepository.class);
        System.out.println(bean);
        System.out.println(bean2);
        System.out.println(dog);
        System.out.println(mySchool);

    }
    @Test
    public void testAutowired2(){
        AnnotationConfigApplicationContext application = new AnnotationConfigApplicationContext(MainConfigAutowired.class);
        BossController bean = application.getBean(BossController.class);
        System.out.println(bean);

    }

    @Test
    public void testProfile(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigProfile.class);
        String[] names = context.getBeanNamesForType(DataSource.class);
        for (String name:names
             ) {
            System.out.println(name);
        }
    }

    @Test
    public void testProfile01(){
        //创建applicationContext
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //设置需要激活的环境
        context.getEnvironment().setActiveProfiles("test","dev");
        //注册主配置类
        context.register(MainConfigProfile.class);
        //启动刷新容器
        context.refresh();
        String[] names = context.getBeanNamesForType(DataSource.class);
        for (String name:names
        ) {
            System.out.println(name);
        }
        Object yellow = context.getBean("yellow");
        System.out.println(yellow);
    }
}
