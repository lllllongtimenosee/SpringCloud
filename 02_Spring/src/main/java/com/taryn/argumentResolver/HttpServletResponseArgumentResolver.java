package com.taryn.argumentResolver;

import com.taryn.annotation.TarynComponent;
import com.taryn.annotation.TarynService;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@TarynComponent
public class HttpServletResponseArgumentResolver implements ArgumentResolver{
    @Override
    public boolean support(Class<?> type, int paramIndex, Method method) {
        return ServletResponse.class.isAssignableFrom(type);
    }

    @Override
    public Object argumentResolver(HttpServletRequest req, HttpServletResponse resp, Class<?> type, int paramIndex, Method method) {
        return resp;
    }
}
