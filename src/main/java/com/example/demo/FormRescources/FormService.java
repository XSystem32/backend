package com.example.demo.FormRescources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FormService {


    private static List <Form> forms = new ArrayList<>();

    private static int idCounter = 0;

    public List<Form> findAll() {
        if (forms != null) {
            return forms;
        }
        return null;
    }

    public void toJson(Form form) {
        try (BufferedWriter file = new BufferedWriter(new FileWriter("/Users/yaz/Documents/Jsons/forms.json", true))) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            String json = gson.toJson(form);

            System.out.println(json);

            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            writer.writeValue(file, form);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Form save(Form form) {
        if (form.getId() == -1 || form.getId() == 0) {
            form.setId(++idCounter);
            forms.add(form);
        } else {
            deleteById(form.getId());
            forms.add(form);
        }
        return form;
    }

    public Form deleteById(long id) {
        Form form = findById(id);

        if(form == null) return null;

        forms.remove(form);

        return form;
    }

    public Form findById(long id) {
        for(Form form:forms) {
            if(form.getId() == id) {
                return form;
            }
        }
        return null;
    }

}
