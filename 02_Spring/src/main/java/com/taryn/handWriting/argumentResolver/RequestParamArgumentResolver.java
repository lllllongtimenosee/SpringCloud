package com.taryn.handWriting.argumentResolver;

import com.taryn.handWriting.annotation.TarynComponent;
import com.taryn.handWriting.annotation.TarynRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@TarynComponent
public class RequestParamArgumentResolver implements ArgumentResolver{
    @Override
    public boolean support(Class<?> type, int paramIndex, Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        Annotation[] parameterAnnotation = parameterAnnotations[paramIndex];
        for (Annotation annotation : parameterAnnotation) {
            if (TarynRequestParam.class.isAssignableFrom(annotation.getClass())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Object argumentResolver(HttpServletRequest req, HttpServletResponse resp, Class<?> type, int paramIndex, Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Annotation[] parameterAnnotation = parameterAnnotations[paramIndex];
        for (Annotation annotation : parameterAnnotation) {
            if (TarynRequestParam.class.isAssignableFrom(annotation.getClass())){
                TarynRequestParam tParam=(TarynRequestParam)annotation;
                String value = tParam.value();
                return req.getParameter(value);
            }
        }
        return null;
    }
}
