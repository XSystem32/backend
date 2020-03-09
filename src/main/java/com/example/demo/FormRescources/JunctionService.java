package com.example.demo.FormRescources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.URISyntaxException;
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
            return gson.fromJson(r, new TypeToken<List<Junction>>() {}.getType());
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
          return new ArrayList();
        }

    }

    public Junction create(Junction junction) throws IOException, GitAPIException, URISyntaxException {

        junction.setId(UUID.randomUUID().toString());
        List<Junction> currentAllJunctions = findAll();
        currentAllJunctions.add(junction);

        saveToDisk(currentAllJunctions);
        addToGit();
        return junction;
    }

    private void saveToDisk(List<Junction> currentAllJunctions) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Writer writer = null;
        try {
            writer = Files.newBufferedWriter(Paths.get("c:\\/Users/yaz/Test/src/main//resources/forms.json"));
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

    public void addToGit() {
        System.out.println("Start af addToGit");
        try (Git git = Git.open(new File("c:\\/Users/yaz/Test"))) {

            Properties prop = readPropertiesFile("c:\\/Users/yaz/Test/src/main//resources/gitinfo.properties");

            // add remote repo:
            RemoteAddCommand remoteAddCommand = git.remoteAdd();

            git.add().addFilepattern(".").call();

            remoteAddCommand.setName("origin");
            remoteAddCommand.setUri(new URIish("https://github.com/XSystem32/backend.git"));
            remoteAddCommand.call();
            git.commit().setMessage("Test").call();
            // push to remote:
            PushCommand pushCommand = git.push();
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(prop.getProperty("username"), prop.getProperty("password")));
            pushCommand.call();
        } catch (URISyntaxException | GitAPIException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("End af addToGit");
    }

    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch(IOException fnfe) {
            fnfe.printStackTrace();
        } finally {
            assert fis != null;
            fis.close();
        }
        return prop;
    }

}
