package com.taryn.handlerAdapter;

import com.taryn.annotation.TarynService;
import com.taryn.argumentResolver.ArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@TarynService("tarynHandlerAdapter")
public class TarynHandlerAdapter implements HandlerAdapter {
    @Override
    public Object[] hand(HttpServletRequest req, HttpServletResponse resp, Method method, Map<String, Object> beans) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] args=new Object[parameterTypes.length];
        Map<String, Object> argumentResolvers = getBeansOfType(beans, ArgumentResolver.class);
        int paramIndex=0;
        int i=0;
        for (Class<?> parameterType : parameterTypes) {
            for (Map.Entry<String, Object> entry : argumentResolvers.entrySet()) {
                ArgumentResolver argumentResolver = (ArgumentResolver)entry.getValue();
                if (argumentResolver.support(parameterType, paramIndex, method)){
                    args[i++]=argumentResolver.argumentResolver(req, resp
                            , parameterType, paramIndex, method);
                }
            }
            paramIndex++;
        }
        return args;
    }

    private Map<String,Object> getBeansOfType(Map<String, Object> beans, Class<?> intfType) {
        Map<String,Object> resultBeans =new HashMap<>();
        for (Map.Entry<String,Object> entry: beans.entrySet()){
            Class<?>[] interfaces = entry.getValue().getClass().getInterfaces();
            if(interfaces!=null && interfaces.length>0){
                for (Class<?> intf:interfaces){
                    if (intf.isAssignableFrom(intfType)){
                        resultBeans.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
        return resultBeans;
    }
}
