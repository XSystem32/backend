package com.example.demo.FormRescources;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

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
        junction.setContext("/somecontext");

        assertEquals("/somecontext", junction.getContext());
    }

    @Test
    void getOrdning() {
        junction.setOrdning("Privat");

        assertEquals("Privat", junction.getOrdning());
    }

    @Test
    void getServer() {
        junction.setServer("aub-privat");

        assertEquals("aub-privat", junction.getServer());
    }

    @Test
    void getDatoPilo() {
        Date date = new Date();

        junction.setDatoPilo(date);


        assertEquals(date, junction.getDatoPilo());
    }

    @Test
    void getDatoProd() {
        Date date = new Date();

        junction.setDatoProd(date);

        assertEquals(date, junction.getDatoProd());
    }

    @Test
    void getCreationDate() {
        Date date = new Date();

        junction.setCreationDate(date);

        assertEquals(date, junction.getCreationDate());
    }

    @Test
    void getUserCreated() {
        junction.setUserCreated("Jabba");

        assertEquals("Jabba", junction.getUserCreated());
    }
}