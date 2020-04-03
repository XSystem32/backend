package com.example.demo.FormRescources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class JunctionService {

    public List<Junction> findAll() throws IOException {
        InputStream is = null;
        try {
            //pullFromGit();
            is = new FileInputStream("c:\\/Users/eldre/IdeaProjects/backend/src/main/resources/forms.json");
            Reader r = new InputStreamReader(is, "UTF-8");
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(r, new TypeToken<List<Junction>>() {}.getType());
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
          return new ArrayList();
        }

    }

    public Junction create(Junction junction) {

        try {
            if (!findContext(junction.getContext())) {
                junction = null;
            }

            assert junction != null;
            junction.setId(UUID.randomUUID().toString());
            List<Junction> currentAllJunctions = findAll();
            currentAllJunctions.add(junction);
            saveToDisk(currentAllJunctions);
            writeToAcl(junction.getContext());
            writeToJunctions(junction.getContext());
            //addToGit(junction.getUserCreated() + " har oprettet junction " + junction.getContext() + " til " + junction.getHost());
            return junction;
        } catch (IOException | NullPointerException e) {
            e.getMessage();
        }
        return null;
    }

    private void saveToDisk(List<Junction> currentAllJunctions) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Writer writer = null;
        try {
            writer = Files.newBufferedWriter(Paths.get("c:\\/Users/eldre/IdeaProjects/backend/src/main/resources/forms.json"));
            gson.toJson(currentAllJunctions, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(String id) throws IOException {
        List<Junction> all = findAll();
        List<Junction> collect = all.stream().filter(junction -> !junction.getId().equals(id)).collect(Collectors.toList());
        saveToDisk(collect);
        //addToGit("Deleted the junction");
    }

    public Junction findById(String id) throws IOException {
        List<Junction> junctions = findAll();
        Optional<Junction> first = junctions.stream().filter(junction -> junction.getId().equals(id)).findFirst();
        return first.orElse(null);
    }

    public void addToGit(String message) throws IOException {
        System.out.println("Start af addToGit");

        //Initialize property file
        Properties prop = readPropertiesFile("c:\\/Users/eldre/IdeaProjects/backend/src/main/resources/gitinfo.properties");

        try (Git git = Git.open(new File(prop.getProperty("localrepo")))) {

            //To add remote repository
            RemoteAddCommand remoteAddCommand = git.remoteAdd();

            //Set a name for repo
            remoteAddCommand.setName("origin");

            //Giving the url of the repo
            remoteAddCommand.setUri(new URIish(prop.getProperty("remoterepo")));

            System.out.println(prop.getProperty("remoterepo"));
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

    public void pullFromGit() throws IOException, GitAPIException {
        final File localPath;
        try (Repository repository = cloneRepository()) {
            localPath = repository.getWorkTree();

            try (Git git = new Git(repository)) {
                PullResult call = git.pull().call();

                System.out.println("Pulled from the remote repository:   " + call   );
                localPath.deleteOnExit();
            }
        }
        //FileUtils.deleteDirectory(localPath);
    }

    private static Repository cloneRepository() throws IOException, GitAPIException {
        // prepare a new folder for the cloned repository
        Properties prop = readPropertiesFile("c:\\/Users/eldre/IdeaProjects/backend/src/main/resources/gitinfo.properties");
        File localPath = File.createTempFile("TestGitRepository", "");
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }

        // then clone
        System.out.println("Cloning from " + prop.getProperty("remoterepo") + " to " + localPath);
        try (Git result = Git.cloneRepository()
                .setURI(prop.getProperty("remoterepo"))
                .setDirectory(localPath)
                .call()) {
            // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
            return result.getRepository();
        }
    }

    public boolean findContext(String context) throws IOException {
        List<Junction> junctions = findAll();

        if(junctions.stream().anyMatch(junction -> junction.getContext().toLowerCase().contains(context))) {
            System.out.println("Context exists, could not create new");
            return false;

        } else {
            System.out.println("No match found");
            return true;
        }
    }

    public void writeToAcl(String context) {

        JSONObject mainObject = new JSONObject();
        JSONObject secondObject = new JSONObject();
        JSONObject thirdObject = new JSONObject();
        JSONObject fourthObject = new JSONObject();

        mainObject.put(context, secondObject);
        secondObject.put("acl", "atp_myndighed_acl");
        secondObject.put("sub", thirdObject);
        thirdObject.put("/fri", fourthObject);
        fourthObject.put("acl", "fri");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (BufferedWriter file = new BufferedWriter(new FileWriter("/Users/eldre/IdeaProjects/backend/src/main/resources/acl-tree.json", true))) {

            gson.toJson(mainObject, file);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToJunctions(String junction) {

        JSONObject mainObject = new JSONObject();
        JSONObject secondObject = new JSONObject();
        mainObject.put(junction, secondObject);
        secondObject.put("junction_type", "ssl");
        secondObject.put("basic_auth_mode", "supply");
        secondObject.put("http_header_ident", "all");
        secondObject.put("remote_http_header", "all");
        secondObject.put("request_encoding", "Local Code Page, Binary");
        secondObject.put("stateful_junction", "yes");
        secondObject.put("transparent_path_junction", "yes");
        secondObject.put("mutual_auth", "yes");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Type mapType = new TypeToken<Map<String, String>>() {}.getType();

        try (BufferedWriter file = new BufferedWriter(new FileWriter("/Users/eldre/IdeaProjects/backend/src/main/resources/junctions.json", true))) {

            gson.toJson(mainObject, mapType, file);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
