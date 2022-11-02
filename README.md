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
- JSR250规范注解@PostConstruct,@PreDestroy
> 在bean注入完成之后需要进行一些初始化操作，在bean销毁之前做一些操作
> 方法上的注解
- BeanPostProcessor
> bean的后置处理器，在bean初始化前后进行处理工作
> 该接口有两个方法，
> postProcessBeforeInitialization:在任何初始化回调方法执行前工作
> postProcessAfterInitialization:在任何初始化回调方法执行之后工作
- 查看BeanPostProcessor的原理，查看方法的调用栈，从下往上依次执行
>       Object wrappedBean = bean;
        if (mbd == null || !mbd.isSynthetic()) {
        //方法初始化之前执行applyBeanPostProcessorsBeforeInitialization
        wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
        }

		try {
            //执行初始化方法
			invokeInitMethods(beanName, wrappedBean, mbd);
		}
		catch (Throwable ex) {
			throw new BeanCreationException(
					(mbd != null ? mbd.getResourceDescription() : null),
					beanName, "Invocation of init method failed", ex);
		}

		if (mbd == null || !mbd.isSynthetic()) {
        //方法初始化之后执行applyBeanPostProcessorsAfterInitialization
			wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
		}
		return wrappedBean;

- spring底层对接口BeanPostProcessor的使用
> spring底层bean赋值，注入其他组件，@Autowired,生命周期注解，@Async，都是使用BeanPostProcessor来完成的
- @PropertySource()
> //加载外部配置文件，使用${}读取内容
> @PropertySource(value = {"classpath:/person.properties"})
- @Autowired
> 自动装配，依赖注入时，IOC容器对各个组件依赖间的赋值
> 1:默认优先按照类型去容器中找对应组件，找到就赋值
> 2:如果有多个类型的组件,在依赖注入的时候显示按照类型查找，找到有多个则再按照名称查找，匹配则自动装配，不匹配则报错
> 这是对于依赖注入而言，如果再IOC容器中直接按照类型查找bean，则会报错，
> 关于@Autowired与@Resource注解的区别，@Autowired默认按照类型然后再按照名称匹配
> @Resource默认按照名称匹配，找不到则找类型唯一匹配
> 配合@Qualifier指定使用哪一个Bean组件的id,而不是使用属性名==》这个注解等价与@Primary标注的哪个bean表示优先被使用（默认）
> @Qualifier比@Primary优先级更高，同时使用则以@Qualifier为准
> required=false表示这组件不是必须被注入的，如果找到了则注入，找不到则不注入
- @Resource
> JSR250标准注解
- @Inject
> JSR330标准注解,需要导入javax.inject依赖
> AutowiredAnnotationBeanPostProcessor:解析完成自动装配功能
- @Autowired注解使用位置说明
> 可以被标注在构造器，参数，方法，属性上，
> 当在构造器上时，并且当类中只有一个有参构造器时，这个@Autowired其实是可以省略的
> 当在方法上时，配合@Bean+方法参数，参数从容器中获取，默认不写@Autowired,都能自动装配
> 当放在参数位置，类似放在set方法位置上或者构造器方法上
- 自定义组件使用spring容器底层的一些组件（ApplicationContext,BeanFactory,xxx）
> 自定义组件只需要实现xxxAware接口,在创建对象的时候，会调用接口的方法注入相关组件（spring底层的一些组件）
> ApplicationContextAware是使用ApplicationContextAwareProcessor来处理的，相关的aware是有相关的Processor来处理（后置处理器机制）
- @Profile
> 指定组件在哪个环境中被激活，不指定则任何环境都能注册这个组件
> 加了环境标识的bean只有对应的环境才能激活bean,默认@Profile("default")
> 修改启动环境：虚拟机运行参数：-Dspring.profiles.active=test
> 代码的方式详情见testProfile01
> @Profile("xx")写在类上则只有激活的环境该类才生效，没有标注的在所有环境下都会被加载
- spring AOP
> 面向切面编程有一下通知动作：前置@Before 后置@After 返回@AfterReturning 异常@AfterThrowing 环绕@Around
> 1：将业务逻辑组件和切面组类都加入到容器中，告诉spring哪个是切面类（@Aspect）
> 2：在切面类上的每一个通知方法上标注通知注解，告诉spring何时何地运行（切入点表达式）
> 3：开启基于注解的aop模式（@EnableAspectJAutoProxy）
- aop原理
> @EnableAspectJAutoProxy==>@Import({AspectJAutoProxyRegistrar.class})，给容器中导入组件
> 利用AspectJAutoProxyRegistrar自定义给容器中注册bean,这个组件什么时候工作。这个组件功能是什么
> internalAutoProxyCreator= AnnotationAwareAspectJAutoProxyCreator（他是一个后置处理器也是一个Aware实现类（BeanFactoryAware））
> 关注后置处理器（在bean初始化完成前后做事）,自动装配BeanFactoryAware