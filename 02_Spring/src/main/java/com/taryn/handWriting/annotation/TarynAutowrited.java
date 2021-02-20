package com.taryn.handWriting.annotation;

import java.lang.annotation.*;

@Target({ElementType.CONSTRUCTOR,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TarynAutowrited {
    boolean required() default true;
}
