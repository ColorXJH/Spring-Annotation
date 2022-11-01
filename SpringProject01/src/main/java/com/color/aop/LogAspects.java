package com.color.aop;

import org.aspectj.lang.annotation.Before;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/11/1 16:37
 */
public class LogAspects {
    //在目标方法之前切入，切入点表达式（指定在哪个方法切入）
    @Before("public int com.color.aop.MathCalculator.div(int,int)")
    //@Before("public int com.color.aop.MathCalculator.*(..)")  任意方法*  任意参数..
    public void logStart(){
        System.out.println("--除法运行了--");
    }

    public void logEnd(){
        System.out.println("--除法结束了--");
    }

    public void logReturn(){
        System.out.println("--除法正常返回了--，运行结果{}");
    }

    public void logException(){
        System.out.println("--除法异常--，异常信息{}");
    }
}
