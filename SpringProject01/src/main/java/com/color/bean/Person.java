package com.color.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/10/21 9:12
 */

public class Person {

    public Person(){}
    public Person(String name, Integer age, String addr) {
        this.name = name;
        this.age = age;
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", addr='" + addr + '\'' +
                '}';
    }
    //基本数值
    //SpEl #{}
    //${},取出配置文件中的值（在运行的环境变量里面的值）
    @Value("张三")
    String name;
    @Value("#{20-2}")
    Integer age;
    @Value("${person.addr}")
    String addr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
