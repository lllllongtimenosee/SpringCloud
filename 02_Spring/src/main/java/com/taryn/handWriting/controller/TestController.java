package com.taryn.handWriting.controller;

import com.taryn.handWriting.annotation.TarynAutowrited;
import com.taryn.handWriting.annotation.TarynController;
import com.taryn.handWriting.annotation.TarynRequestMapping;
import com.taryn.handWriting.annotation.TarynRequestParam;
import com.taryn.handWriting.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@TarynController
@TarynRequestMapping("/test")
public class TestController {

    @TarynAutowrited()
    private TestService testServiceImpl;

    //http://localhost:8070/test/query?name=Spring&age=17
    @TarynRequestMapping("/query")
    public void query(HttpServletRequest req, HttpServletResponse res,
                      @TarynRequestParam("name") String name, @TarynRequestParam("age") String age){
        try {
            PrintWriter writer = res.getWriter();
            String query = testServiceImpl.query(name, age);
            writer.write(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @TarynRequestMapping("/insert")
    public void insert(HttpServletRequest req, HttpServletResponse res,String param){
        try {
            PrintWriter writer = res.getWriter();
            String insert = testServiceImpl.insert(param);
            writer.write(insert);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @TarynRequestMapping("/update")
    public void update(HttpServletRequest req, HttpServletResponse res,String param){
        try {
            PrintWriter writer = res.getWriter();
            String update = testServiceImpl.update(param);
            writer.write(update);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
