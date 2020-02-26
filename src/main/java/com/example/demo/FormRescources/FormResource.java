package com.example.demo.FormRescources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
public class FormResource {

    @Autowired
    private FormService formService;

    @GetMapping("/users/forms")
    public List<Form> getAllForms() {
        return formService.findAll();
    }

    @RequestMapping("/users/forms/{id}")
    public Form getForm(@PathVariable long id) {
        return formService.findById(id);
    }

    @DeleteMapping("/users/forms/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable long id) {
        Form form = formService.deleteById(id);

        if(form!= null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/users/forms/{id}")
    public ResponseEntity<Form> updateForm(@PathVariable long id, @RequestBody Form form) {
        Form formUpdated = formService.save(form);
        formService.toJson(form);
        return new ResponseEntity<Form>(form, HttpStatus.OK);
    }

    @PostMapping("/users/forms")
    public ResponseEntity<Void> updateForm(@RequestBody Form form) {
        Form createdForm = formService.save(form);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdForm.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
