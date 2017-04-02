package se.anrop.arma3sync.cleanup.viewmodels;

import fr.soe.a3s.domain.repository.SyncTreeDirectory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import se.anrop.arma3sync.cleanup.Constants;
import se.anrop.arma3sync.cleanup.utils.Arma3Folder;
import se.anrop.arma3sync.cleanup.utils.Arma3Registry;
import se.anrop.arma3sync.cleanup.utils.Arma3Sync;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bjorn on 2017-02-05.
 */
public class ListViewModel implements Callback<String, ObservableValue<Boolean>> {

    private ObservableList<String> folders = FXCollections.observableArrayList();

    public ListViewModel() {
        refresh();
    }

    public void refresh() {
        try {
            loadData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getFolders() {
        return folders;
    }

    private void loadData() throws ClassNotFoundException, IOException {
        URL url = new URL(Constants.ANROP_ARMA3SYNC_REPOSITORY_SYNC_PATH);
        SyncTreeDirectory sync = Arma3Sync.readSyncFile(url);
        List<String> remoteFolders = sync.getList().stream().map(node -> node.getName().toLowerCase()).collect(Collectors.toList());
        List<String> localFolders = Arma3Folder.getFolders(new File(Arma3Registry.getPath())).stream().map(node -> node.toLowerCase()).collect(Collectors.toList());

        HashSet<String> folders = new HashSet<>();
        folders.addAll(localFolders);
        folders.removeAll(remoteFolders);

        this.folders.setAll(folders.stream().sorted().collect(Collectors.toList()));
    }

    @Override
    public ObservableValue<Boolean> call(String item) {
        BooleanProperty observable = new SimpleBooleanProperty();
        observable.addListener((obs, wasSelected, isNowSelected) ->
                System.out.println("Check box for " + item + " changed from " + wasSelected + " to " + isNowSelected)
        );
        return observable;
    }
}
