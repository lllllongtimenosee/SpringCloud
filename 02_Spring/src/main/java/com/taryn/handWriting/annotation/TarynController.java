package com.taryn.handWriting.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)            //作用范围：用在接口或者类上
@Retention(RetentionPolicy.RUNTIME)  //注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented                          //表明该注解被包含在javadoc中  @Inherited：说明子类可以继承父类中的该注解
@TarynComponent
public @interface TarynController {
    String value() default "";
}
