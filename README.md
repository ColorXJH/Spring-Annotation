# Spring-Annotation
>description the spring annotation for learning

- spring在注解驱动开发
    - 1：容器
      - AnnotationConfigApplicationContext
      - 组件添加
      - 组件赋值
      - aop
      - 声明式事务
    - 2：扩展原理
      - BeanFactoryPostProcessor
      - BeanDefinitionRegistryPostProcessor
      - ApplicationListener
      - Spring容器创建过程
    - 3：web
      - servlet3.0
      - 异步请求


- @Configuration
> 告诉spring这是一个配置类
- @Bean
> 给容器中注册一个bean，类型为返回值的类型，id默认为方法名,也可以指定bean的value值为bean的id
- @ComponentScan
> 包扫描，任意@Controller ,@Service,@Repository,@Component注解都会被扫描到并加载到容器中，
> 该注解可以指定扫描包路径，例如：(value = "com.color")