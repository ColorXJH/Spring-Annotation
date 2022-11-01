package com.color.config;

import org.springframework.context.annotation.Configuration;

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
 * @date 2022/11/1 16:01
 */
@Configuration
public class MainConfigAop {
}
