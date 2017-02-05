package se.anrop.arma3sync.cleanup.utils;

import fr.soe.a3s.domain.repository.SyncTreeDirectory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class Arma3Sync {
    public static SyncTreeDirectory readSyncFile(URL url) throws ClassNotFoundException, IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new GZIPInputStream(url.openConnection().getInputStream()));
        return (SyncTreeDirectory) objectInputStream.readObject();
    }
}
