package com.color.transction;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;


/**
 * @author ColorXJH
 * @version 1.0
 * @description: 声明式事务
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
 *
 * @date 2022/11/4 9:37
 */
@Configuration
@ComponentScan({"com.color.service","com.color.repository"})
@EnableTransactionManagement
public class TransactionConfig {
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource= new ComboPooledDataSource();
        dataSource.setUser("ColorXJH");
        dataSource.setPassword("2012WananXJH");
        dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://49.235.243.26:3306/test");
        return dataSource;
    }
    /**
     1:直接在参数位置写bean名称，ioc容器可以拿到相应bean
     2:调用bean方法
     */
    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
        //spring对@Configuration类会特殊处理，给容器中加组件的方法，多次调用都只是从容器中找组件而已
        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    //注册事务管理器
    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }

}
