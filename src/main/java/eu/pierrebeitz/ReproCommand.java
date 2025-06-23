package eu.pierrebeitz;

import io.quarkus.logging.Log;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import picocli.CommandLine.Command;

@Command(name = "greeting", mixinStandardHelpOptions = true)
public class ReproCommand implements Runnable {

    @Override
    public void run() {
      try {
        var ls = Git.lsRemoteRepository()
                .setTags(true)
                .setHeads(true)
                .setRemote("https://github.com/quarkiverse/quarkus-jgit.git")
                .call();
        Log.infof("Got %d refs", ls.size());
      } catch (GitAPIException e) {
        throw new RuntimeException(e);
      }
    }
}
