package com.taryn.handWriting.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TarynComponent {
    String value() default "";
}
