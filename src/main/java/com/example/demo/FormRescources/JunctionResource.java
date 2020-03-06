package com.example.demo.FormRescources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<Junction> getAllForms() throws IOException {
        return junctionService.findAll();
    }

    @RequestMapping("/junctions/{id}")
    public Junction getForm(@PathVariable long id) {
        return junctionService.findById(id);
    }

    @DeleteMapping("/junctions/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable long id) {
        Junction junction = junctionService.deleteById(id);

        if(junction != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/junctions/{id}")
    public ResponseEntity<Junction> updateJunction(@PathVariable long id, @RequestBody Junction junction) {
        Junction junctionUpdated = junctionService.save(junction);
        //junctionService.toJson(junction);
        return new ResponseEntity<Junction>(junction, HttpStatus.OK);
    }

    @PostMapping("/junctions")
    public ResponseEntity<Void> updateForm(@RequestBody Junction junction) {
        Junction createdJunction = junctionService.save(junction);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdJunction.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
