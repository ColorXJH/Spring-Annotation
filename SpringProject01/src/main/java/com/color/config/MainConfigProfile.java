package com.color.config;

import com.color.bean.Yellow;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.*;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @author ColorXJH
 * @version 1.0
 * @description: @Profile环境搭建，spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能
 * 例如 开发环境 测试环境 生产环境
 * 数据源：A B C  数据源以c3p0为例
 * @date 2022/11/1 13:49
 */
@Configuration
@PropertySource("classpath:/dbconfig.properties")// 斜杠代表从类路径的根路径开始查找文件位置
public class MainConfigProfile implements EmbeddedValueResolverAware {
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;
    @Value("${db.url}")
    private String url;
    //@Value("${db.driverClass}")//方法1，实现接口是方法2
    private String driverClass;

    private StringValueResolver valueResolver;
    @Bean
    public Yellow yellow(){
        return new Yellow();
    }

    @Bean
    @Profile("test")
    public DataSource dataSourceTest() throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);
        comboPooledDataSource.setJdbcUrl(url+"/test");
        comboPooledDataSource.setDriverClass(driverClass);
        return comboPooledDataSource;
    }
    @Bean
    @Profile("dev")
    public DataSource dataSourceDev() throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);
        comboPooledDataSource.setJdbcUrl(url+"/dev");
        comboPooledDataSource.setDriverClass(driverClass);
        return comboPooledDataSource;
    }
    @Bean
    @Profile("prod")
    public DataSource dataSourceProd() throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);
        comboPooledDataSource.setJdbcUrl(url+"/prod");
        comboPooledDataSource.setDriverClass(driverClass);
        return comboPooledDataSource;
    }
    //默认环境
    @Bean
    @Profile("default")
    public DataSource dataSourceDefault() throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);
        comboPooledDataSource.setJdbcUrl(url+"/dev");
        comboPooledDataSource.setDriverClass(driverClass);
        return comboPooledDataSource;
    }

    //字符串值解析器，用来解析配置文件的数据等
    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        this.valueResolver=stringValueResolver;
        driverClass=valueResolver.resolveStringValue("${db.driverClass}");
    }
}
