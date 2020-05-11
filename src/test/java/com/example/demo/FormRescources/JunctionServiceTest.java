package com.example.demo.FormRescources;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.assertj.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
class JunctionServiceTest {

    private MockMvc mockMvc;

    Gson gson = new Gson();

    JunctionResource junctionResource;

    @Autowired
    private JunctionService junctionService;

    @MockBean
    private JunctionService repository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void findAll() throws Exception {
        //Given
        List <Junction> junctionsMockList = new ArrayList<>();
        Junction junction = new Junction("1", "David", "INTERN", "/somecontextt","AES", "Liberty Server");
        Junction junction2 = new Junction("2", "Hans", "Offentlig", "/somecontexts","AES", "LDAP");
        junctionsMockList.add(junction);
        junctionsMockList.add(junction2);

        /*when(repository.findAll()).thenReturn(Stream.of(new Junction("1", new Date(), "David", "INTERN", "/somecontextt","AES", "Liberty Server", false,"Went wrong", new Date(), new Date()),
                new Junction("2", new Date(), "Hans", "Offentlig", "/somecontexts","AES", "LDAP", false,"Not wrong", new Date(), new Date())).collect(Collectors.toList()));*/
        //When
        when(junctionService.findAll()).thenReturn(junctionsMockList);
        mockMvc.perform(MockMvcRequestBuilders.get("/junctions").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        //Then
        assertEquals(2, junctionService.findAll().size());
    }

    @Test
    void createTest() throws Exception {
        Junction junction = new Junction();
        junction.setId("A1B2D3F4G");
        junction.setUserCreated("Mads");
        junction.setHost("Erhverv");
        junction.setContext("/somenewjunction");
        junction.setOrdning("ATP Investering");
        junction.setServer("Liberty Server");

        String jsonJunctionString = "{\"id\":\"A1B2D3F4G\"," +
                "\"userCreated\":\"Mads\"," +
                "\"host\":\"Erhverv\"," +
                "\"context\":\"/somenewjunction\"," +
                "\"ordning\":\"ATP Investering\"," +
                "\"server\":\"Liberty Server\"}";

        Junction actualObject = gson.fromJson(jsonJunctionString, Junction.class);

        when(junctionService.create(any(Junction.class))).thenReturn(junction);

        assertEquals(String.valueOf(junction),String.valueOf(actualObject));
        mockMvc.perform(MockMvcRequestBuilders.put("/junctions/create").content(jsonJunctionString).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void deleteJunctionTest() throws Exception {
        List<Junction> junctions = new ArrayList<>();
        Junction junction = new Junction("1", "David", "INTERN", "/somecontextt","AES", "Liberty Server");
        junctions.add(junction);

        mockMvc.perform(MockMvcRequestBuilders.delete("/junctions/" + junctions.remove(junction)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        assertThat(junctions.isEmpty());
    }


}