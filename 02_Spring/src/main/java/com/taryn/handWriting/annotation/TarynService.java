package com.taryn.handWriting.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TarynComponent
public @interface TarynService {
    String value() default "";
}
