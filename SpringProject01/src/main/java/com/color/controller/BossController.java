package com.color.controller;

import com.color.service.MyService;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/11/1 10:33
 */
//@Controller
public class BossController {

    private MyService myService;
    //注意，当容器中只有一个有参构造器时，这个@Autowired其实是可以省略的
    //可以标注在有参构造器上，或者放在在相应的参数上,都是从容器中获取
    //@Autowired
    public BossController(/*@Autowired*/ MyService myService) {
        this.myService = myService;
    }
    public MyService getMyService() {
        return myService;
    }
    //可以标注在set方法上
    /*@Autowired
    public void setMyService(@Autowired MyService myService) {
        this.myService = myService;
    }*/

    @Override
    public String toString() {
        return "BossController{" +
                "myService=" + myService +
                '}';
    }
}
