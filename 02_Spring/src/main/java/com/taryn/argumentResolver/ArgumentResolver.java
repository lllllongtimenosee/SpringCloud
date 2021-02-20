package com.taryn.argumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public interface ArgumentResolver {

    boolean support(Class<?> type, int paramIndex, Method method);

    Object argumentResolver(HttpServletRequest req, HttpServletResponse resp,
                            Class<?> type,int paramIndex,Method method);
}
