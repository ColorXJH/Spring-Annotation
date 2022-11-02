package com.color.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/11/1 16:37
 */
@Aspect//告诉spring当前类是一个切面类
public class LogAspects {
    //抽取公共的切入点表达式
    //1：本类引用：直接在方法上引用该方法名称
    //2：其他的切面类引用：引用方法名称

    //该切入点表达式的写法参考spring官网文档
    @Pointcut("execution(public int com.color.aop.MathCalculator.*(..))")
    public void pointCut(){}

    //在目标方法之前切入，切入点表达式（指定在哪个方法切入）
    //@Before("public int com.color.aop.MathCalculator.div(int,int)")
    //@Before("public int com.color.aop.MathCalculator.*(..)")  任意方法*  任意参数..

    //1：本类引用：直接在方法上引用该方法名称
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName()+"方法运行"+"--@Before--除法运行了--"+",参数列表：{"+ Arrays.asList(joinPoint.getArgs())+"}");
    }
    //2：其他的切面类引用（外部类也可以这样引用）：
    @After("com.color.aop.LogAspects.pointCut()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName()+"方法运行"+"--@After--除法运行了--"+",参数列表：{"+ Arrays.asList(joinPoint.getArgs())+"}");

    }
    @AfterReturning(value = "pointCut()",returning = "result")
    public void logReturn(Object result){
        System.out.println("--@AfterReturning--除法正常返回了--，运行结果{"+result+"}");
    }
    //JoinPoint joinPoint一定要出现在参数表的第一位
    @AfterThrowing(value = "pointCut()",throwing = "e")
    public void logException(JoinPoint joinPoint,Exception e){
        System.out.println(joinPoint.getSignature().getName()+"方法运行"+"-- @AfterThrowing--运行异常--"+e);

    }
}
