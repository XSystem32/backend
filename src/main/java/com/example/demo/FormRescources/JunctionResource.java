package com.example.demo.FormRescources;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class JunctionResource {

    private static final Logger LOGGER = Logger.getLogger(JunctionResource.class.getName());

    @Autowired
    private JunctionService junctionService;

    @GetMapping("/junctions")
    public List<Junction> getAllJunctions() throws IOException {
        return junctionService.findAll();
    }

    @RequestMapping("/junctions/{id}")
    public Junction getJunction(@PathVariable String id) throws IOException {
        return junctionService.findById(id);
    }

    @DeleteMapping("/junctions/{id}")
    public void deleteJunction(@PathVariable String id) throws IOException {
        junctionService.deleteById(id);
    }

    @PutMapping("/junctions/create")
    public Junction createJunction(@RequestBody Junction junction) throws IOException, GitAPIException, URISyntaxException {
        return junctionService.create(junction);
    }

}
