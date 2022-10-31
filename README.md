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
- @Scope
> 用于调整作用域，默认单实例，容器初始化时加载bean，prototype时容器初始化时不加载，只有在调用该bean的时候才会加载，
> 并且单实例和多实例的对象不一致
- @Lazy
> 单实例bean默认在容器启动时创建对象，然后放在容器中，我们可以让其懒加载，
> 懒加载：容器启动时不创建对象，第一次使用bean时创建对象，并进行初始化操作
- @Conditional
> 按照一定的条件进行判断，满足条件则给容器中注册bean,放在类上表示满足条件则所有bean生效，反之亦然
- @Import
> 给容器中快速导入一个组件，传统方式时自己在类上写可以被ioc容器识别的bean注解以及高级注解，如果类是第三方包，但是又没有注解
> 这时我们可以使用该方式：@Bean[导入第三方包中的组件]
> @Import[快速给容器中导入一个组件]，例如类Color,直接在类上导入该组件，无需创建bean方法，生成bean之后
> 该bean的id默认是组件的全类名：com.color.bean.Color
- ImportSelector(在@Import中使用)
> 导入的选择器，返回需要导入组件的全类名数组
- ImportBeanDefinitionRegistrar(在@Import中使用)
> 导入bean定义注册器，通过这个注册器去注册一个beanDefinition(bean的类型)
- FactoryBean
> 使用spring提供的FactoryBean(工厂bean)
- @Bean(init-method/destroy-method)
> 对象在初始化时和销毁时调用该类上的方法，注意多实例的时候，初始化并不是在创建容器时，而是在调用时
> 同时多实例bean在容器关闭时也不会销毁实例，需要我们自己搜东销毁（即容器不会管理多实例bean）
- bean实现InitializingBean/DisposableBean
> 通过实现这两个接口同样可以实现类似初始化以及销毁时方法，因为改接口方法调用时机已经明确