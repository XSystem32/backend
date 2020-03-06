package com.example.demo.FormRescources;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class JunctionService {



    public List<Junction> findAll() {
        InputStream is = null;
        try {
            is = new FileInputStream("c:\\/Users/yaz/Test/src/main//resources/forms.json");
            Reader r = new InputStreamReader(is, "UTF-8");
            Gson gson = new GsonBuilder().create();
            List <Junction> localJunctions = gson.fromJson(r, new TypeToken<List<Junction>>() {}.getType());
            return localJunctions;
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
          return new ArrayList();
        }

    }



    public Junction create(Junction junction) {

          junction.setId(UUID.randomUUID().toString());
        List<Junction> currentAllJunctions = findAll();
        currentAllJunctions.add(junction);

        saveToDisk(currentAllJunctions);

        return junction;
    }

    private void saveToDisk(List<Junction> currentAllJunctions) {
        Gson gson = new Gson();

        // create a writer
        Writer writer = null;
        try {
            writer = Files.newBufferedWriter(Paths.get("c:\\/Users/yaz/Test/src/main//resources/forms.json"));
            // convert user object to JSON file
            gson.toJson(currentAllJunctions, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(String id) {
        List<Junction> all = findAll();

        List<Junction> collect = all.stream().filter(junction -> !junction.getId().equals(id)).collect(Collectors.toList());
        saveToDisk(collect);
    }

    public Junction findById(String id) {
        List<Junction> junctions = findAll();
        Optional<Junction> first = junctions.stream().filter(junction -> junction.getId().equals(id)).findFirst();
        return first.orElse(null);
    }


    public Junction update(Junction junction) {
        List<Junction> junctions = findAll();
        List<Junction> updatedList = junctions.stream().map(oldValue -> oldValue.getId().equals(junction.getId()) ? junction : oldValue).collect(Collectors.toList());
        saveToDisk(updatedList);
        return junction;
    }
}
