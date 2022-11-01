package com.color.config;

import com.color.controller.BossController;
import com.color.repository.MyRepository;
import com.color.service.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author ColorXJH
 * @version 1.0
 * @description: 自动装配，DI,完成对IOC容器中各个组件的依赖关系赋值
 * @date 2022/10/31 16:44
 */
@Configuration
@ComponentScan({"com.color.controller","com.color.service","com.color.repository","com.color.bean"})
public class MainConfigAutowired {
    @Bean("myRepository2")
    @Primary//自定默认装配哪一个，高优先级
    public MyRepository myRepository(){
        MyRepository myRepository=   new MyRepository();
        myRepository.setLabel("label2");
        return myRepository;
    }
    @Bean
    //也可以使用这个自动注入,方法参数中的@Autowired也可以不写，默认参数从容器中获取
    public BossController bossController(/*@Autowired*/MyService service){
        BossController bossController=new BossController(service);
        return bossController;
    }
}
