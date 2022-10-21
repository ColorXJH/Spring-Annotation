package com.color.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:自定义过滤规则
 * @date 2022/10/21 15:43
 */
public class MyTypeFilter implements TypeFilter {
    /**
     * Description: 
     * @Author: ColorXJH
     * @Date: 2022/10/21 15:45
     * @param metadataReader 读取到当前正在扫描的类的信息
     * @param metadataReaderFactory 可以获取到其他任何类的信息
     * @Return: boolean
     **/
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取当前类注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前正在扫描的类的信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取当前类资源
        Resource resource = metadataReader.getResource();
        //获取类名
        String className = classMetadata.getClassName();
        if(className.contains("er")){
            return true;
        }
        System.out.println("---->"+className);
        return false;
    }
}
