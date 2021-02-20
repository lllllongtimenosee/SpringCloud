package com.taryn.handlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

public interface HandlerAdapter {

 Object[] hand(HttpServletRequest req, HttpServletResponse resp
         , Method method, Map<String,Object> beans);
}
