package com.taryn.servlet;

import com.taryn.annotation.*;
import com.taryn.controller.TestController;
import com.taryn.handlerAdapter.TarynHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Servlet implementation class DispatcherServlet
 */
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID=1L;

    List<String> classNmaes= new ArrayList<>();

    Map<String,Object> beans=new ConcurrentHashMap<>();

    Map<String,Object> handlerMap=new ConcurrentHashMap<>();

    Properties properties=null;

    private static String HANDLERADAPTER = "tarynHandlerAdapter";

    public DispatcherServlet() {
    }

    @Override
    public void init() throws ServletException {
        //包扫描
        scanPackage("com.taryn");
        //实例化
        instance();
        //依赖注入
        ioc();
        //建立一个path与method的映射
        handlerMapping();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = requestURI.replace(contextPath, "");
        Method method = (Method)handlerMap.get(path);
        TestController instance = (TestController)beans.get("/" + path.split("/")[1]);
        TarynHandlerAdapter tarynHandlerAdapter = (TarynHandlerAdapter) beans.get(HANDLERADAPTER);
        Object[] args = tarynHandlerAdapter.hand(req, resp, method, beans);
        try {
            method.invoke(instance, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private String replaceTo(String basePackage) {
        return basePackage.replaceAll("\\.", "/");
    }

    private void scanPackage(String basePackage) {
        URL resource = this.getClass().getClassLoader().getResource("/" + replaceTo(basePackage));
        String fileResource = resource.getFile();
        File file = new File(fileResource);
        String[] list = file.list();
        for (String path : list) {
            File filePath = new File(fileResource + path);
            if (filePath.isDirectory()){
                scanPackage(basePackage+"."+path);
            }else{
                classNmaes.add(basePackage+"."+filePath.getName());
            }
        }
    }

    private void instance() {
        if (classNmaes.size()<=0){
            System.out.println("包扫描失败或指定包下没有类！");
            return;
        }
        for (String classNmae : classNmaes) {
            String className = classNmae.replace(".class", "");
            try {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(TarynComponent.class) && !clazz.isAnnotation()){
                    String simpleName = clazz.getSimpleName();
                    beans.put(simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1),clazz.newInstance());
                }else if (clazz.isAnnotationPresent(TarynController.class)){
                    TarynController annotationController = clazz.getAnnotation(TarynController.class);
                    TarynRequestMapping annotationRequestMapping = clazz.getAnnotation(TarynRequestMapping.class);
                    String value = annotationRequestMapping.value();
                    beans.put(value,clazz.newInstance());
                }else if (clazz.isAnnotationPresent(TarynService.class)){
                    String simpleName = clazz.getSimpleName();
                    beans.put(simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1),clazz.newInstance());
                }else
                    continue;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private void ioc() {
        if (classNmaes.size()<=0){
            System.out.println("包扫描失败或指定包下没有类！");
            return;
        }
        for (Map.Entry<String,Object> entry: beans.entrySet()){
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            if (clazz.isAnnotationPresent(TarynController.class)){
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(TarynAutowrited.class)){
                        TarynAutowrited annotationAutowrited = field.getAnnotation(TarynAutowrited.class);
                        field.setAccessible(true);
                        try {
                            field.set(instance, beans.get(field.getName()));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }else continue;
                }
            }else continue;
        }
    }

    private void handlerMapping() {
        if (classNmaes.size()<=0){
            System.out.println("包扫描失败或指定包下没有类！");
            return;
        }
        //iter itar itco iten itit itli ittok itve fori foreach
        for (Map.Entry<String,Object> entry: beans.entrySet()){
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            if (clazz.isAnnotationPresent(TarynController.class)){
                TarynRequestMapping annotationRequestMapping = clazz.getAnnotation(TarynRequestMapping.class);
                String classValue = annotationRequestMapping.value();
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(TarynRequestMapping.class)){
                        TarynRequestMapping annotation = method.getAnnotation(TarynRequestMapping.class);
                        String methodValue = annotation.value();
                        handlerMap.put(classValue+methodValue, method);
                    }else continue;
                }
            }
        }
    }
}
