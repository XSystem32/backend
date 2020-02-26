package com.example.demo.Test;

public class TestBean {

    private String message;

    public TestBean(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("TestBean [message=%s]", message);
    }
}
