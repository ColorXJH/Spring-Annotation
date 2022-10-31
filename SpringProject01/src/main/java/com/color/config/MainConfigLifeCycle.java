package com.color.config;

import com.color.bean.Cai;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ColorXJH
 * @version 1.0
 * @description: bean的声明周期，创建-》初始化=》销毁 的过程，现在是容器帮我们管理bean的声明周期
 * 我们可以自定义初始化和销毁方法，容器在bean进行到当前生命周期时，来调用我们自定义的方法
 * 1：指定初始化，销毁方法
 *      指定init-method和destroy-method,xml文件中
 * 2：
 * @date 2022/10/31 8:47
 */
@Configuration
@ComponentScan("com.color.bean")
public class MainConfigLifeCycle {
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Cai cai(){
        return new Cai();
    }

}

