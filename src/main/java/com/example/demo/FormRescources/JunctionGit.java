package com.example.demo.FormRescources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class JunctionGit {

    public static void main(String[] args) throws IOException {
        try {
            JSONObject mainObject = new JSONObject();
            JSONObject secondObject = new JSONObject();
            JSONObject thirdObject = new JSONObject();
            JSONObject fourthObject = new JSONObject();

            mainObject.put("/ATPIndbetalinger", secondObject);
            thirdObject.put("/fri", fourthObject);
            fourthObject.put("acl", "fri");
            secondObject.put("acl", "atp_myndighed_acl");
            secondObject.put("sub", thirdObject);


            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(mainObject);

            System.out.println(json);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

       }

}
