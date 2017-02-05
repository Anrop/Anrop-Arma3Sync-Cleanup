package se.anrop.arma3sync.cleanup.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjorn on 2017-02-05.
 */
public class Arma3Folder {
    public static List<String> getFolders(File arma3Folder) {
        List<String> folders = new ArrayList<>();
        for (final File fileEntry : arma3Folder.listFiles()) {
            if (fileEntry.isDirectory() && fileEntry.getName().startsWith("@")) {
                folders.add(fileEntry.getName());
            }
        }
        return folders;
    }
}
