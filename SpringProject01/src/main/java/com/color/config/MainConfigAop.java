package com.color.config;

import com.color.aop.LogAspects;
import com.color.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author ColorXJH
 * @version 1.0
 * @description: aop:面向切面编程，在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程
 * aop的底层是动态代理
 * 1：导入aop模块 spring-aspects
 * 2：定义一个业务逻辑类（MathCalculator），在其运行的时候将日志进行打印（方法之前，运行结束，出现异常）
 * 3：定义一个日志切面类（LogAspects）,切面类里的方法需要动态该感知MathCalculator.div运行到哪里了，然后执行
 *      通知方法：前置，后置，返回，异常，环绕（动态代理，手动推进目标方法运行：joinPoint.proccced()）
 * 4：给切面类的目标方法标注何时何地运行（通知注解）
 * 5：将切面类和业务逻辑类（目标方法所在的类）都加入到容器中
 * 6：告诉spring哪个类是切面类（给切面类上加一个注解@Aspect）
 * 7: 给配置类加@EnableAspectJAutoProxy【开启基于注解的aop模式】
 * @date 2022/11/1 16:01
 */
@Configuration
@EnableAspectJAutoProxy//开启注解aop模式
public class MainConfigAop {
    //业务逻辑类加入容器中
    @Bean
    public MathCalculator mathCalculator(){
        return new MathCalculator();
    }

    //切面类加入容器中
    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
