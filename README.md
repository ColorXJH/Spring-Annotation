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
```
> bean的后置处理器，在bean初始化前后进行处理工作
> 该接口有两个方法，
> postProcessBeforeInitialization:在任何初始化回调方法执行前工作
> postProcessAfterInitialization:在任何初始化回调方法执行之后工作
```
- 查看BeanPostProcessor的原理，查看方法的调用栈，从下往上依次执行
```
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
```
- spring底层对接口BeanPostProcessor的使用
> spring底层bean赋值，注入其他组件，@Autowired,生命周期注解，@Async，都是使用BeanPostProcessor来完成的
- @PropertySource()
> //加载外部配置文件，使用${}读取内容
> @PropertySource(value = {"classpath:/person.properties"})
- @Autowired
```
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
```
- @Autowired注解使用位置说明
```
> 可以被标注在构造器，参数，方法，属性上，
> 当在构造器上时，并且当类中只有一个有参构造器时，这个@Autowired其实是可以省略的
> 当在方法上时，配合@Bean+方法参数，参数从容器中获取，默认不写@Autowired,都能自动装配
> 当放在参数位置，类似放在set方法位置上或者构造器方法上
- 自定义组件使用spring容器底层的一些组件（ApplicationContext,BeanFactory,xxx）
> 自定义组件只需要实现xxxAware接口,在创建对象的时候，会调用接口的方法注入相关组件（spring底层的一些组件）
> ApplicationContextAware是使用ApplicationContextAwareProcessor来处理的，相关的aware是有相关的Processor来处理（后置处理器机制）
```
- @Profile
```
> 指定组件在哪个环境中被激活，不指定则任何环境都能注册这个组件
> 加了环境标识的bean只有对应的环境才能激活bean,默认@Profile("default")
> 修改启动环境：虚拟机运行参数：-Dspring.profiles.active=test
> 代码的方式详情见testProfile01
> @Profile("xx")写在类上则只有激活的环境该类才生效，没有标注的在所有环境下都会被加载
```
- spring AOP
```
> 面向切面编程有一下通知动作：前置@Before 后置@After 返回@AfterReturning 异常@AfterThrowing 环绕@Around
> 1：将业务逻辑组件和切面组类都加入到容器中，告诉spring哪个是切面类（@Aspect）
> 2：在切面类上的每一个通知方法上标注通知注解，告诉spring何时何地运行（切入点表达式）
> 3：开启基于注解的aop模式（@EnableAspectJAutoProxy）
```
- aop原理(创建和注册AnnotationAwareAspectJAutoProxyCreator过程)
```@EnableAspectJAutoProxy==>@Import({AspectJAutoProxyRegistrar.class})，给容器中导入组件
 利用AspectJAutoProxyRegistrar自定义给容器中注册bean,这个组件什么时候工作。这个组件功能是什么
 internalAutoProxyCreator= AnnotationAwareAspectJAutoProxyCreator（他是一个后置处理器也是一个Aware实现类（BeanFactoryAware））
 关注后置处理器（在bean初始化完成前后做事）,自动装配BeanFactoryAware
 流程：
 1：传入配置类，创建IOC容器
 2：注册配置类，调用refresh()，刷新容器
 3：registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建
       3.1）：先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor
       3.2）：给容器中加入别的BeanPostProcessor
       3.3）：优先注册实现了PriorityOrdered接口的BeanPostProcessor
       3.4）：仔给容器中注册实现了Ordered接口的BeanPostProcessor
       3.5）：注册没实现优先级接口的BeanPostProcessor
       3.6）：注册BeanPostProcessor,实际上就是创建BeanPostProcessor对象，保存仔容器中
               创建internalAutoProxyCreator的BeanPostProcessor[AnnotationAwareAspectJAutoProxy]
               1:创建bean的实例
               2:populateBean(BeanName,mbd,instanceWrapper)(给bean的各种属性赋值)
               3:initializeBean 初始化bean
                   1:invokeAwareMethods():处理Aware接口的方法回调
                   2:applyBeanPostProcessorsBeforeInitialization()：应用后置处理器的BeforeInitialization()
                   3:invokeInitMethods():执行自定义的初始化方法
                   4:applyBeanPostProcessorsAfterInitialization():执行后置处理器的PostProcessorsAfterInitialization()
               4：BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功--》aspectJAdvisorsBuilder    
       3.7）：把BeanPostProcessor注册到BeanFactory中：
               beanFactory.addBeanPostProcessor(postProcessor)
```
- AnnotationAwareAspectJAutoProxyCreator执行时机
```
> AnnotationAwareAspectJAutoProxyCreator-》 InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation
>   1:遍历获取容器中所有的bean,依次创建对象
>       getBean->doGetBean->getSingleton()-
>   2:创建bean
>       1:先从缓存中获取当前bean,如果能获取到，说明bean是之前被创建过的,直接使用，否则再创建
>           只要创建好的bean都会被缓存起来
>       2:createBean():创建bean
>           1):resolveBeforeInstantiation():希望后置处理器在此能返回一个代理对象，如果能就使用，如果不能则doCreateBean()
>               真正的创建一个bean实例，和上方3.6流程一模一样
> 
> InstantiationAwareBeanPostProcessor作用
>   1：每一个bean创建之前，调用postProcessBeforeInstantiation(),
>       关心LogAspects和MathCalculator的创建
>       1）:判断当前bean是否在advisedBeans中（保存了所有需要增强的bean）
>       2）:判断当前bean是否是基础类型的Advice,PointCut,Advisor...类型接口,或者是否是切面（@Aspect）
>       3）:是否需要跳过
>           1：获取候选的增强器（切面里的通知方法）
>           2：永远返回false
>   2：创建对象
>       postProcessAfterInitialization
>           return wrapIfNecessary//包装，如果需要的情况下
>           1）：获取当前bean的所有增强器（通知方法）
>                  1：找到候选的增强器（找哪些通知方法是需要切入当前bean方法的）
>                  2：找到能在当前bean使用的增强器 
>                  3：给增强器排序
>           2）：保存当前bean在adviseBeans中
>           3）：如果当前bean需要增强，创建当bean的代理对象
>                  1：获取所有的增强器（通知方法）
>                  2：保存到ProxyFactory
>                  3：创建代理对象，spring自定决定创建jdk还是cglib的动态代理
>           4）：给容器中返回当前组件使用cglib增强了的代理对象
>           5）：以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程
```
- aop原理-获取拦截器链-MethodInterceptor
```
目标方法执行
    容器中保存了组件的代理对象（cglib增强后的对象），这个对象里面保存了相信信息（比如增强器，目标对象，xxx）    
    1:CglibAopProxy.intercept();拦截目标方法的执行
    2：根据ProxyFactory获取将要执行的目标方法的拦截器链
    3：如果没有拦截器链，直接执行目标方法
    4：如果有拦截器链，把需要执行的目标对象，目标方法，拦截器链信息传入创建一个CglibMethodInvocation对象，并调用他的proceed()方法，处理返回值
    5：拦截器的触发过程
        1：如果没有拦截器 执行目标方法，或者拦截器索引和拦截器数组-1
        2：链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回后再执行
            拦截器的机制，保证通知方法与目标方法的执行顺序
            
```
- 总结
```
1:@EnableAspectJAutoProxy开启AOP功能
2:@EnableAspectJAutoProxy注解会给容器注册一个组件：AnnotaitonAwareAspectJAutoProxyCreator
3:AnnotaitonAwareAspectJAutoProxyCreator是一个后置处理器
4:容器的创建流程，创建
    1：registerBeanPostProcessors()注册后置处理器，创建AnnotaitonAwareAspectJAutoProxyCreator对象
    2：finishBeanFactoryInitialization()初始化剩下的单实例bean
        1:创建业务逻辑组件和切面组件
        2:AnnotaitonAwareAspectJAutoProxyCreator会拦截组件的创建过程
        3:组件创建完成后，判断组件是否需要增强
            是：切面的通知方法，包装成增强器（Advisor）,给业务逻辑组件创建一个代理对象
5:执行目标方法
    1：代理对象执行目标方法
    2：CglibAopProxy.intercept()
        1:得到目标方法的拦截器链（增前器包装成拦截器 ）
        2:利用拦截器的链式机制，依次进入每一个拦截器进行执行
        3:效果
            1：前置通知-》目标方法-》后置通知-》返回通知 
            2：前置通知-》目标方法-》后置通知-》异常通知             
            

```
- 事务
```aidl
 * 环境搭建：
 * 1：导入相关依赖（数据源，数据库驱动，spring-jdbc模块）
 * 2：配置数据源，jdbcTemplate 操作数据库
 * 3:给方法上加@Transactional注解,表示当前方法式一个事务方法
 * 4：@EnableTransactionManagement 开启基于注解的事务管理
 * 5:配置事务管理器控制事务
 *
 * 原理；
 * 1：@EnableTransactionManagement
 *      利用TransactionManagementConfigurationSelector给容器中导入组件
 *      导入两个组件
 *      AutoProxyRegistrar
 *      ProxyTransactionManagementConfiguration
 * 2：AutoProxyRegistrar
 *      给容器中注册一个InfrastructureAdvisorAutoProxyCreator
 *      利用后置处理器机制在对象创建后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用
 *
 * 3：ProxyTransactionManagementConfiguration
 *      1：给容器中注入事务增强器
 *          1事务增强器要用事务注解的信息，AnnotationTransactionAttributeSource解析事务注解
 *          2事务拦截器：TransactionInterceptor：保存了事务属性信息，把事务管理器也保存进来，也是一个MethodInterceptor
 *              在目标方法执行的时候，执行这些拦截器链（这里只有一个事务拦截器）-》获取事务相关的属性-》获取事务管理器
 *              最终会在容器中按照类型获取一个TransactionManager
 *          3执行目标方法
 *              1：如果异常，获取事务管理器，回滚
 *              2：如果正常，利用事务管理器提交事务
 *  整个流程和aop基本一致
```
- 扩展原理
```aidl

```
