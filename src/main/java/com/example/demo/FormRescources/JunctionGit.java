package com.example.demo.FormRescources;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.IOException;

public class JunctionGit {


    public void addToGit() throws IOException {
        String localPath = "c:\\/Users/yaz/Test";
        Repository localRepo = new FileRepository(localPath);
        Git git = new Git(localRepo);

        // add remote repo:
        RemoteAddCommand remoteAddCommand = git.remoteAdd();

        remoteAddCommand.setName("https://github.com/XSystem32/backend.git");
        remoteAddCommand.setUri(new URIish());

        // push to remote:
        PushCommand pushCommand = git.push();
        pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider("XSystem32", "uvq53egf"));
    }
}
