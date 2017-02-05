package se.anrop.arma3sync.cleanup;

import fr.soe.a3s.domain.repository.SyncTreeDirectory;
import se.anrop.arma3sync.cleanup.utils.Arma3Folder;
import se.anrop.arma3sync.cleanup.utils.Arma3Registry;
import se.anrop.arma3sync.cleanup.utils.Arma3Sync;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final String ANROP_ARMA3SYNC_REPOSITORY_PATH = "http://arma3sync.anrop.se";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String syncPath = ANROP_ARMA3SYNC_REPOSITORY_PATH + "/" + "/.a3s/sync";
        URL url = new URL(syncPath);
        SyncTreeDirectory sync = Arma3Sync.readSyncFile(url);
        List<String> remoteFolders = sync.getList().stream().map(node->node.getName().toLowerCase()).collect(Collectors.toList());
        List<String> localFolders = Arma3Folder.getFolders(new File(Arma3Registry.getPath())).stream().map(node->node.toLowerCase()).collect(Collectors.toList());


        HashSet<String> folders = new HashSet<>();
        folders.addAll(localFolders);
        folders.removeAll(remoteFolders);

        System.out.println(folders.stream().sorted().collect(Collectors.joining("\n")));
    }
}
