import com.color.aop.MathCalculator;
import com.color.bean.Person;
import com.color.config.*;
import com.color.controller.BossController;
import com.color.controller.MyController;
import com.color.ext.ExtConfig;
import com.color.repository.MyRepository;
import com.color.service.UserService;
import com.color.transction.TransactionConfig;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
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
        //??????bean??????????????????getObject?????????????????????
        Object colorFactoryBean = annotationConfigApplicationContext.getBean("colorFactoryBean");
        Object colorFactoryBean1 = annotationConfigApplicationContext.getBean("colorFactoryBean");

        System.out.println(colorFactoryBean.getClass());
        System.out.println(colorFactoryBean==colorFactoryBean1);
        //????????????bean??????
        Object FactoryBeanOriginal = annotationConfigApplicationContext.getBean("&colorFactoryBean");
        System.out.println(FactoryBeanOriginal.getClass());
    }
    @Test
    public void testLifeCycle(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfigLifeCycle.class);
        //????????????
        annotationConfigApplicationContext.close();
    }
    @Test
    public void testPropertyValues(){
        AnnotationConfigApplicationContext application = new AnnotationConfigApplicationContext(MainConfigValues.class);
        String[] beanDefinitionNames = application.getBeanDefinitionNames();
        String namess=application.getEnvironment().getProperty("person.addr");
        System.out.println("--???????????????????????????????????????????????????--"+namess);
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
        //?????????????????????????????????????????????
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
        //??????applicationContext
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //???????????????????????????
        context.getEnvironment().setActiveProfiles("test","dev");
        //??????????????????
        context.register(MainConfigProfile.class);
        //??????????????????
        context.refresh();
        String[] names = context.getBeanNamesForType(DataSource.class);
        for (String name:names
        ) {
            System.out.println(name);
        }
        Object yellow = context.getBean("yellow");
        System.out.println(yellow);
    }

    @Test
    public void testAop(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigAop.class);
        MathCalculator bean = context.getBean(MathCalculator.class);
        bean.div(1,1);
    }
    @Test
    public void testTx(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TransactionConfig.class);
        UserService bean = applicationContext.getBean(UserService.class);
        bean.insertUser();
        applicationContext.close();
    }
    @Test
    public void testExt(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);

        applicationContext.close();
    }

    @Test
    public void testExtPublishEvent(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);
        //????????????
        applicationContext.publishEvent(new ApplicationEvent(new String("---??????????????????--color---xjh--")) {
            @Override
            public String toString() {
                return super.toString();
            }
        });
        applicationContext.close();
    }

}
