package com.color.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author ColorXJH
 * @version 1.0
 * @description: 使用spring的工厂bean创建bean类型对象
 * @date 2022/10/31 8:31
 */
public class ColorFactoryBean implements FactoryBean<Color> {
    //返回一个Color对象，该对象会添加到容器
    @Override
    public Color getObject() throws Exception {
        System.out.println("--生产了一个color对象实例--");
        return new Color();
    }
    //返回bean类型
    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }
    //是否单例
    @Override
    public boolean isSingleton() {
        return true;
    }
}
