package com.example.demo.FormRescources;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import jdk.internal.org.objectweb.asm.TypeReference;
import org.springframework.stereotype.Service;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;


@Service
public class JunctionService {

    private static List <Junction> junctions = new ArrayList<>();

    private static int idCounter = 0;

    public List<Junction> findAll() {
        InputStream is = null;
        try {
            is = new FileInputStream("c:\\/Users/yaz/Test/src/main//resources/forms.json");
            Reader r = new InputStreamReader(is, "UTF-8");
            Gson gson = new GsonBuilder().create();
            List <Junction> localJunctions = gson.fromJson(r, new TypeToken<List<Junction>>() {}.getType());
            localJunctions.addAll(junctions);
            return localJunctions;
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
          return new ArrayList();
        }

    }

    public void toJson(Junction junction) {
        try (BufferedWriter file = new BufferedWriter(new FileWriter("/Users/yaz/Test/src/main//resources/forms.json", true))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(junction);
            System.out.println(json);

            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

            writer.writeValue(file, junction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Junction save(Junction junction) {
        try (BufferedWriter file = new BufferedWriter(new FileWriter("/Users/yaz/Test/src/main//resources/forms.json", true))) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

            if (junction.getId() == -1 || junction.getId() == 0) {
                junction.setId(++idCounter);
                writer.writeValue(file, junction);
                junctions.add(junction);
            } else {
                deleteById(junction.getId());
                junctions.add(junction);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return junction;
    }

    public Junction deleteById(long id) {
        Junction junction = findById(id);

        if(junction == null) return null;

        junctions.remove(junction);

        return junction;
    }

    public Junction findById(long id) {
        for(Junction junction : junctions) {
            if(junction.getId() == id) {
                return junction;
            }
        }
        return null;
    }

}
