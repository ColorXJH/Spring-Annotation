package com.color.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:我的导入选择器
 * @date 2022/10/27 16:17
 */
public class MyImportSelector implements ImportSelector {
    /**
     * Description: 返回需要导入的组件数组
     * @Author: ColorXJH
     * @Date: 2022/10/27 16:18
     * @param annotationMetadata:当前标注@Import注解的类上方的所有注解信息
     * @Return: java.lang.String[]
     **/
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //annotationMetadata.get 可以得到类，注解的信息
        return new String[]{"com.color.bean.Blue","com.color.bean.Yellow"};
        //return new String[0];//可以返回一个空数组，但是不要返回null,这样则会报空指针异常
    }
}
