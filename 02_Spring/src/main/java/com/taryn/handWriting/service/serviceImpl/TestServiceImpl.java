package com.taryn.handWriting.service.serviceImpl;

import com.taryn.handWriting.annotation.TarynService;
import com.taryn.handWriting.service.TestService;

@TarynService
public class TestServiceImpl implements TestService {
    @Override
    public String query(String name, String age) {
        return "{name="+name+",age="+age+"}";
    }

    @Override
    public String insert(String param) {
        return "insert successful....";
    }

    @Override
    public String update(String param) {
        return "update successful....";
    }
}
