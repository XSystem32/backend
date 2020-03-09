package com.example.demo.FormRescources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class JunctionResource {

    @Autowired
    private JunctionService junctionService;

    @GetMapping("/junctions")
    public List<Junction> getAllJunctions() {
        return junctionService.findAll();
    }

    @RequestMapping("/junctions/{id}")
    public Junction getJunction(@PathVariable String id) {
        return junctionService.findById(id);
    }

    @DeleteMapping("/junctions/{id}")
    public void deleteJunction(@PathVariable String id) {
        junctionService.deleteById(id);
    }

    @PutMapping("/junctions")
    public Junction createJunction( @RequestBody Junction junction) {
        return junctionService.create(junction);
    }

    @PostMapping("/junctions/{id}")
    public Junction updateJunction(@PathVariable String id, @RequestBody Junction junction) {
        return junctionService.update(junction);
    }

}
