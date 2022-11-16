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
```
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
```
1:BeanPostProcessor:bean后置处理器，bean创建对象初始化前后进行拦截工作了
2:BeanFactoryPostProcessor:BeanFactory的后置处理器，
    在BeanFactory标准初始化之后调用，所有的Bean定义已经保存加载到beanFactory中
    但是bean的实例还未创建
    时机：
        1：ioc容器创建对象，
        2；invokeBeanFactoryPostProcessors(beanFactory),执行beanFactoryPostProcessor
            如何找到所有的BeanFactoryPostProcessor并执行其他的方法
                1：直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并执行他们的方法
                2：在初始化创建其他组件前面执行
                
3：BeanDefinationRegistryPostProcessor
    是BeanFactoryPostProcessor的子接口
    有方法postProcessBeanDefinationRegistry()：在所有bean定义信息将要被加载但是bean实例还未创建的时候执行
    由此得出上述方法在BeanFactoryPostProcessor类执行之前执行,可以利用该类给容器中再额外添加组件      
    原理：
    1：ioc创建对象
    2：refresh()->invokeBeanFactoryPostProcessors(beanFactory)
    3:从容器中获取所有的BeanDefinitionRegistryPostProcessor组件
        1：依次触发postProcessBeanDefinitionRegistry
        2：再来触发postProcessBeanFactory()方法
    4：再来从容器中找到BeanFactoryPostProcessor组件，然后依次触发postProcessBeanFactory()方法

4：ApplicationListener:应用程序监听器
    监听容器中发布的事件，完成事件驱动模型的开发
    public interface ApplicationListener<E extends ApplicationEvent> extends EventListener
        监听ApplicationEvent及其下面的子事件
    步骤：
        1：写一个监听器来监听某个事件(ApplicationEvent及其子类)
            或者使用@EventListener注解，让任意方法都可以监听事件
            使用EventListenerMethodProcessor处理器来解析方法上的@EventListener注解
            SmartInitializingSingleton原理
                1：ioc容器创建对象，refresh()
                2：finishBeanFactoryInitialization(beanFactory);初始化剩下的单实例bean
                    1：先创建所有的单实例bean，getBena()
                    2：获取创建好的单实例bean,判断是否是SmartInitializingSingleton类型，如果是，则调用afterSingletonsInstantiated()方法
        2：把监听器放在容器中
        3；只要容器中有相关事件的发布，我们就能监听到该事件
            ContextRefreshedEEvent:容器刷新完成事件（所有bean都完全创建），会发布整个事件（spring发布）
            。。。
        4：如何发布事件
            application.publishEvent(要发布的事件)
        5：发布与监听机制原理，以ContextRefreshedEEvent为例
            1：容器创建对象，refresh()
            2：sihishRefresh()；容器刷新完成
       事件发布流程
            3：publishEvent(new ContextRefreshEvent(this))
                    1：获取事件的多播器（派发器）：getApplicationEventMulticaster()
                    2：MulticastEvent派发事件
                    3：获取到所有的AppplicationListener
                        for 循环遍历
                        1：如果有Executor可以支持使用Executor异步派发：Executor executor=getTaskExecutor();
                        2：否则，同步的方式直接执行listener方法：invokeListener(listener,event)
                            拿到listener,回调onApplicationEvent方法
                            
       获取事件的多播器（派发器）
            refresh()->initApplicationEventMulticaster(),初始化  ApplicationEventMulticaster，然后讲listener注册到其中       
    
              
```
- spring源码流程
```
1:spring容器的refresh()创建刷新过程
    1：prepareRefresh();刷新预处理
        1：initPropertySources()：初始化属性设置，子类自定义实现，个性化的属性设置
        2：this.getEnvironment().validateRequiredProperties();属性校验
        3：this.earlyApplicationEvents = new LinkedHashSet();保存容器中的一些事件
    2：ConfigurableListableBeanFactory beanFactory = this.obtainFreshBeanFactory();获取beanFactory
        1:this.refreshBeanFactory();刷新（创建）beanFactory
            创建了一个this.beanFactory=new DefaultListableBeanFactory(),设置id
        2：this.getBeanFactory();返回GenericApplicationContext创建的beanFactory对象
        3：讲创建的BeanFactory返回-》DefaultListableBeanFactory返回
    3：this.prepareBeanFactory(beanFactory);beanFactory的预准备工作
        1：设置BeanFactory的类加载器，支持的表达式解析器。。。。
        2：添加部分BeanPostProcessor[ApplicationContextAwareProcessor]
        3：设置忽略的自动装配接口：EnvironmentAware.class
            --这些接口的实现类，不能通过接口类型自动注入
        4：注册可以解析的自动配装备：（我们能直接再任何组件中自动注入，例如BeanFactory，ResourceLoader，ApplicationEventPublisher，ApplicationContext）
        5：添加beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));（后置处理器）
        6：添加编译时的AspectJ支持
        7：给beanFactory中注入一些能用的组件，例如：enviroment[ConfigurableEnviroment] systemProperties[Map<String,Object>] systemEnviroment[Map<String,Object>]
    4：postProcessBeanFactory(beanFactory);beanFactory准备工作完成后的后置处理工作
        1：子类通过重写这个方法来在BeanFactory创建并预准备完成后做进一步的设置
--------------------beanFactory创建以及预准备工作
    5：invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessors的方法
        beanFactory的后置处理器，在beanFactory标准初始化之后执行的（就是上方的前4步）
        两个接口
        BeanFactoryPostProcessor
        BeanDefinitionRegistryPostProcessor
        1:执行invokeBeanFactoryPostProcessors的方法
            1：获取所有的BeanDefinitionRegistryPostProcessor
            2：先执行实现了PriorityOrdered.class优先级接口的BeanDefinitionRegistryPostProcessor
                postProcessor.postProcessBeanDefinitionRegistry(registry);
            3：再执行实现了Ordered.class顺序接口的BeanDefinitionRegistryPostProcessor
                postProcessor.postProcessBeanDefinitionRegistry(registry);
            4：最后执行没有实现优先级，顺序接口的BeanDefinitionRegistryPostProcessor    
                postProcessor.postProcessBeanDefinitionRegistry(registry);
            5:再执行BeanFactoryPostProcessor的方法
                1：获取所有的BeanFactoryPostprocessor
                2：先执行实现了PriorityOrdered.class优先级接口的BeanFactoryPostprocessor
                    postProcessor.postProcessBeanFactory;
                3：再执行实现了Ordered.class顺序接口的BeanFactoryPostprocessor
                    postProcessor.postProcessBeanFactory;
                4：最后执行没有实现优先级，顺序接口的BeanFactoryPostprocessor    
                    postProcessor.postProcessBeanFactory;
    6：registerBeanPostProcessors(beanFactory);注册BeanPostProcessors（bean的后置处理器）
        用来拦截bean的创建过程
        1：获取所有的BeanPostProcessor，后置处理器都默认可以通过了PriorityOrdered,Ordered接口来指定优先级
            不同接口类型的BeanPostProcessor，在bean创建前后执行的时机是不一样的
            BeanPostProcessor
            DestructionAwareBeanPostProcessor
            InstantiationAwareBeanPostProcessor
            SmartInstantiationAwareBeanPostProcessor
            MergedBeanDefinitionPostProcessor【internalPostProcessors】
        2：先注册PriorityOrdered优先级接口的BeanPostProcessor
            把每一个BeanPostProcessor添加到BeanFactory中
            beanFactory.addBeanPostProcessor
        3：再注册实现Ordered接口的
        4：最后注册没有实现任何优先级接口的
        5：最终注册MergedBeanDefinitionPostProcessor
        6：注册一个ApplicationListenerDetector，来在bean创建完成后检查是否是ApplicationListener,
            如果是：则：beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(applicationContext));    
    7：initMessageSource();初始化MessageSource组件(国际化功能，消息绑定，消息解析)
        1：获取beanFactory:ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        2：看容器中是否有id为messageSource的组件，   
            如果有，赋值给messageSource属性，如果没有则创建DelegatingMessageSource   
                MessageSource：取出国际化配置文件中的某个key的值，能按照区域信息获取
        3：把创建好的MessageSource注册到容器中，以后获取国际化配置文件的值时，可以自动注入MessageSource
           beanFactory.registerSingleton(MESSAGE_SOURCE_BEAN_NAME, this.messageSource);
           messageSource.getMessage()                        
    8：initApplicationEventMulticaster();初始化事件派发器
        1：获取BeanFactory
        2：从BeanFactory中获取名称为applicationEventMulticaster的ApplicationEventMulticaster
        3：如果上一步没有配置，则创建一个SimpleApplicationEventMulticaster
        4：将创建的SimpleApplicationEventMulticaster添加到BeanFactory中，以后其他组件直接自动注入
    9：onRefresh(); 
        1：留给子容器（子类），子类可以重写该方法，在容器刷新的时候可以自定义逻辑
    10：registerListeners();给容器中将所有项目里面的ApplicationListener注册进来
        1：从容器中拿到所有的ApplicationListener
        2：将每个监听器添加到事件派发器中
            getApplicationEventMulticaster().addApplicationListener(listener);
        3：派发之前步骤产生的事件
    11：finishBeanFactoryInitialization(beanFactory);初始化所有剩下的单实例bean
        1:beanFactory.preInstantiateSingletons();初始化后面剩下的单实例bean
            1:DefaultListableBeanFactory.preInstantiateSingletons() 
                1:获取容器中的所有的剩余的no-lazy Bean,依次进行初始化和创建对象
                2：获取bean的定义信息：RootBeanDefinition
                3：bean不是抽象的，是单实例的，不是懒加载的       
                    1：判断是否是FactoryBean(是否是实现FactoryBean接口的Bean)-->(spring的工厂模式)
                       会调用工厂bean的getObject放啊创建bean对象
                    2：不是工厂bean，则调用getBean()创建对象
                        1:getBean(beanName)->ioc。getBean()
                        2:doGetBean()    
                        3:先获取缓存中的单实例bean,如果能获取到，则说明这个bean之前被创建过（所欲创建过的单实例bean都会被缓存起来）
                            private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(256);
                        4:缓存中拿不到则开始bean的创建对象流程
                        5：标记当前bean已经被创建
                        6：获取bean的定义信息
                        7：获取当前bean依赖的其他bean，如果有按照getBean()把依赖的bean先创建出来
                        8：启动单实例bean的创建流程
                            1：createBean(beanName, mbd, args);
                            2：Object bean = resolveBeforeInstantiation(beanName, mbdToUse);让beanPostProcessor先拦截返回代理对象
                                  InstantiationAwareBeanPostProcessor，提前执行，触发postProcessBeforeInstanttiation()
                                  如果前一个方法有返回值，则触发postProcessAfterInitialization();
                            3：如果前面的InstantiationAwareBeanPostProcessor没有返回代理对象，则调用
                                Object beanInstance = doCreateBean(beanName, mbdToUse, args);创建bean
                                    1：instanceWrapper = createBeanInstance(beanName, mbd, args);创建bean实例，利用工厂方法或对象构造器创建bean实例
                                    2：applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
                                        1：bdp.postProcessMergedBeanDefinition(mbd, beanType, beanName);               
                                        2：populateBean(beanName,mbd,instanceWrapper)bean属性赋值
                                          赋值之前，
                                            1：拿到InstantiationAwareBeanPostProcessor类型的后置处理器，执行postProcessAfterInitialization();
                                            2：拿到InstantiationAwareBeanPostProcessor后置处理器，执行postProcessPropertyValues()
                                          赋值
                                            3：applyPropertyValues(beanName,mbd,bw,pvs)应用bean属性的值，为属性使用setter方法等进行赋值  
                                        3：bean初始化：initializeBean(beanName,exposedObject,mbd)
                                            1:invokeAwareMethods(beanName,bean);执行xxxAware接口方法 
                                                BeanNameAware,BeanClassLoaderAware,BeanFactoryAware
                                            2：wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);执行后置处理器初始化之前的方法
                                                BeanPostProcessor.postprocessBeforeInitialization
                                            3：invokeInitMethods(beanName,wrappedBean,mbd):执行初始化方法
                                                1：是否是InitializingBean接口的实现，执行接口规定的初始化
                                                2：是否自定义初始化方法                                
                                            4： 执行后置处理器初始化之后 applyBeanPostProcessorsAfterInitialization()
                                                    BeanPostProcessor.postProcessAfterInitialization()
                                            5：注册bean的销毁方法
                                4:将创建的bean添加到缓存中singletonObjects
                                ioc容器就是这些很多的Map，保存了单实例bean,环境信息     
                            4:所有bean都利用getBean创建完成后：
                                检查所有的bean是否是SmartInitializingSingleton接口的，如果是就执行afterSingletonsInstantited();                  
    12：finishRefresh();完成BeanFactory的初始化创建工作，IOC容器就创建完成了
        1：this.initLifecycleProcessor();初始化生命周期有关的后置处理器：LifecycleProcessor，默认从生命周期找，如果没有则new一个，并且加入到r用其中
            允许我们写一个LifecycleProcessor的实现类，可以在BeanFactory的onRefresh()，onClose()时候初始化生命周期
        2：this.getLifecycleProcessor().onRefresh();拿到前面定义的生命周期处理器（BeanFactory），回调onRefresh()
        3：this.publishEvent((ApplicationEvent)(new ContextRefreshedEvent(this)));发布容器刷新完成事件，
        4：LiveBeansView.registerApplicationContext(this);
==总结==
1：spring容器在启动的时候，先会保存所有注册进来的bean的定义信息
    1：xml注册bean <bean>
    2：注解bean @Servece,@Component,@Bean...
2：spring容器会在合适的时机创建这些Bean
    1：用到这个Bean的时候：利用getBean方法创建这个Bean,创建好之后保存在容器中
    2：同意创建剩下所有bean的时候：finishBeanFactoryInitialization(beanFactory);初始化所有剩下的单实例bean
3：后置处理器：
    1：每一个bean创建完成，都会使用后置处理器进行处理，增强bean的功能          
        例如：AutowiredAnnotationBeanPostProcessor:处理自动注入功能
        AnnotationAwareAspectJAutoProxyCreator:来做aop功能
4：事件驱动模型
    ApplicationListener:事件监听
    ApplicationEventMulticaster事件派发                                                                   
```         
- Servlet3.0
```
原来的servlet,filter,listener 定义在web.xml
包括DispatcherServlet 整合Spring也是定义在web.xml中
servlet3.0属于JSR315系列规范，需要tomcat7及以上容器支持，如果需要下载servlet规范文档，参考
https://www.jcp.org/en/home/index ==>查找servlet下载

现在可以使用注解@WebServlet(name = "helloServlet", value = "/hello-servlet")
包括@WebListener,@WebFilter
```
- Shared library(共享库) / runtimes pluggability (运行时插件)
```
1:servlet容器启动，会扫描当前应用里面每一个jar包的ServletContainerInitializer的实现
2：提供ServletContainerInitializer的实现类
    实现类必须绑定在 META-INF/services/javax.servlet.ServletContainerInitializer
    文件的内容就是ServletContainerInitializer的实现类的全类名

总结：容器在启动应用的时候，会扫描当前应用每一个jar包里面META-INF/services/javax.servlet.ServletContainerInitializer
指定的实现类，启动并运行这个实现类的方法 ,穿入感兴趣的类型
       ServletContainerInitializer接口     
       @HandleTypes注解

```
- 定制SpringMVC
```
> 1:@EnableWebMvc注解：开启springmvc定制配置功能 ==><mvc:annotation-driven>
> 2:配置组件（视图解析器，视图映射，静态资源映射，拦截器。。。。）
>  extends WebMvcConfigurerAdapter
```
- servlet3.0异步请求处理
```
在servlet3.0之前，servlet采用Thread-Per-Request的方式处理请求：即每一次http请求都由某一个线程从头到尾负责
如果一个请求需要进行IO操作，比如访问数据库，调用第三方接口服务，那么其所对应的线程将同步的等待IO操作完成，而IO操作是非常慢的
所以此时的线程并不能及时的释放回线程池以供后续使用，在并发量越来越大的情况下，将带来严重的性能问题，即使是spring,structs这样的高层框架
ye脱离不了这个桎梏，因为他们都是建立在servlet之上的，为了解决这个问题，servlet3.0引入了异步处理，然后在servlet3.1中又引入了非阻塞IO来进一步增强异步处理的性能
```
