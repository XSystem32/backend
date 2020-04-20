package com.example.demo.FormRescources;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JunctionBeanTest {

    private Junction junctionBean;

    public void setContext() {
        junctionBean.setContext("/blabla");
    }

}
