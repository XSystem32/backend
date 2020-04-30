package com.example.demo.FormRescources;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class JunctionTestBean {

    private Junction junction;

    @BeforeEach
    public void setUp() {
        junction = new Junction();
    }

    @Test
    public void getId() {

        String idValue = "DD74H6F90H";

        junction.setId(idValue);

        assertEquals(idValue, junction.getId());
    }

    @Test
    void getHost() {
        junction.setHost("AES");

        assertEquals("AES", junction.getHost());
    }

    @Test
    void getContext() {
        junction.setHost("AES");

        assertEquals("AES", junction.getHost());
    }

    @Test
    void getOrdning() {
        junction.setHost("AES");

        assertEquals("AES", junction.getHost());
    }

    @Test
    void getServer() {
        junction.setHost("AES");

        assertEquals("AES", junction.getHost());
    }

    @Test
    void getDatoPilo() {
        junction.setHost("AES");

        assertEquals("AES", junction.getHost());
    }

    @Test
    void getDatoProd() {
        junction.setHost("AES");

        assertEquals("AES", junction.getHost());
    }

    @Test
    void getCreationDate() {
        junction.setHost("AES");

        assertEquals("AES", junction.getHost());
    }

    @Test
    void getUserCreated() {
        junction.setHost("AES");

        assertEquals("AES", junction.getHost());
    }
}