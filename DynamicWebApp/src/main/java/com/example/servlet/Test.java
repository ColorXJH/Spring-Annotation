package com.example.servlet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/12/1 9:07
 */
public class Test {
    public static void main(String[] args) {

    }
    public void test(){
        //pecs=producer extends consumer super:频繁往外读取内容的时候extends上界描述符
        //频繁往内插入的适合super下界描述符
        //<? extends Person>一般作为返回类型限定--》get
        //<? super Person>一般作为参数限定--add
        //? extends T 一般用来定义范型类和方法，，擦除后类型为T
        //? super T  用于声明方法形参，接收T和其父类型
        List<? extends Person> list1=getLisi();
        //list1.add(new Lisi());//不能add只能get
        Person p=list1.get(0);
        Human h=list1.get(0);
        List<? super Person>list2=new ArrayList<>();
        list2.add(new Lisi());
        list2.add(new Person());
        Object o=list2.get(0);
    }
    public List<Lisi> getLisi(){
        List<Lisi> list=new ArrayList<>();
        list.add(new Lisi());
        return list;
    }
    class Human{

    }
    class Person extends Human{

    }
    class Lisi extends Person{

    }
}
