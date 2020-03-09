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
        addToGit(junction.getUserCreated() + " har oprettet " + junction.getContext() + " til " + junction.getHost());
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

    public void addToGit(String message) throws IOException {
        System.out.println("Start af addToGit");

        //Initialize property file
        Properties prop = readPropertiesFile("c:\\/Users/yaz/Test/src/main/resources/gitinfo.properties");

        try (Git git = Git.open(new File(prop.getProperty("localrepo")))) {

            //To add remote repository
            RemoteAddCommand remoteAddCommand = git.remoteAdd();

            //Set a name for repo
            remoteAddCommand.setName("origin");

            //Giving the url of the repo
            remoteAddCommand.setUri(new URIish(prop.getProperty("remoterepo")));

            //Initializing the git push method
            PushCommand pushCommand = git.push();

            String username = prop.getProperty("username");
            String password = prop.getProperty("password");

            //User and password of you github
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username,password));

            //Call "git add .", "git commit "message" ", "git remote add origin" and "git push" respectively
            git.add().addFilepattern(".").call();
            git.commit().setMessage(message).call();
            remoteAddCommand.call();
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
