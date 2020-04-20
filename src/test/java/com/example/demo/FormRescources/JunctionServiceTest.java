package com.example.demo.FormRescources;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.boot.test.context.assertj.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
class JunctionServiceTest {

    private MockMvc mockMvc;

    @Autowired
    private JunctionService junctionService;

    @MockBean
    private JunctionService repository;

    @Autowired
    private WebApplicationContext context;

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    private void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void findAll() {
        /*List <Junction> junctions = new ArrayList<>();
        Junction junction = new Junction("1", new Date(), "David", "INTERN", "/somecontextt","AES", "Liberty Server", false,"Went wrong", new Date(), new Date());
        junctions.add(junction);*/

        when(repository.findAll()).thenReturn(Stream.of(new Junction("1", new Date(), "David", "INTERN", "/somecontextt","AES", "Liberty Server", false,"Went wrong", new Date(), new Date()),
                new Junction("2", new Date(), "Hans", "Offentlig", "/somecontexts","AES", "LDAP", false,"Not wrong", new Date(), new Date())).collect(Collectors.toList()));
        assertEquals(2, junctionService.findAll().size());
    }

    @Test
    void createTest() throws Exception {
        Junction junction = new Junction();
        junction.setId("A1B2D3F4G");
        junction.setCreationDate(new Date());
        junction.setUserCreated("Mads");
        junction.setHost("Erhverv");
        junction.setContext("/somenewjunction");
        junction.setOrdning("ATP Investering");
        junction.setServer("Liberty Server");
        junction.setDatoPilo(new Date());
        junction.setDatoProd(new Date());
        String jsonRequest = objectMapper.writeValueAsString(junction);

        MvcResult result = mockMvc.perform(put("/junctions/create").content(jsonRequest).content(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(result);
        Response response = objectMapper.readValue(resultContent, Response.class);
        Assert.assertTrue(response.isStatus() == Boolean.TRUE);
    }

    @Test
    public void deleteJunctionTest() throws IOException {
        List<Junction> junctions = new ArrayList<>();
        Junction junction = new Junction("1", new Date(), "David", "INTERN", "/somecontextt","AES", "Liberty Server", false,"Went wrong", new Date(), new Date());
        junctions.add(junction);

        //when(junctionService.deleteById()).thenReturn(junctions.remove(junction));
    }


}