package com.taryn.handWriting.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TarynRequestMapping {
    String value() default "";
}
