package com.example.demo.Test;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class DemoController {

    @GetMapping(path = "test-bean")
    public TestBean testBean() {
        //throw new RuntimeException("Error has occurred, contact support");
        return new TestBean("Hello there - changed");
    }

    @GetMapping(path = "/test-bean/path-variable/{name}")
    public TestBean testBeanVariable(@PathVariable String name) {
        return new TestBean(String.format("Hello there, %s", name));
    }


}
