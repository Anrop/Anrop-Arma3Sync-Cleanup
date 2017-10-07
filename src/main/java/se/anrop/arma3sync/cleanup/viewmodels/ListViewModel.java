package se.anrop.arma3sync.cleanup.viewmodels;

import fr.soe.a3s.domain.repository.SyncTreeDirectory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.util.Callback;
import org.apache.commons.io.FileUtils;
import se.anrop.arma3sync.cleanup.Constants;
import se.anrop.arma3sync.cleanup.models.ModFolder;
import se.anrop.arma3sync.cleanup.utils.Arma3Folder;
import se.anrop.arma3sync.cleanup.utils.Arma3Registry;
import se.anrop.arma3sync.cleanup.utils.Arma3Sync;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bjorn on 2017-02-05.
 */
public class ListViewModel implements Callback<ModFolder, ObservableValue<Boolean>> {

    private ObservableList<ModFolder> folders = FXCollections.observableArrayList();
    private ObservableSet<ModFolder> selectedFolders = FXCollections.observableSet();
    private File armaFolder;

    public ListViewModel() {
        refresh();
    }

    public void refresh() {
        try {
            this.armaFolder = new File(Arma3Registry.getPath());
            loadData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ModFolder> getFolders() {
        return folders;
    }

    public ObservableSet<ModFolder> getSelectedFolders() {
        return selectedFolders;
    }

    public void deleteSelectedFolders() {
        for (ModFolder mod : getSelectedFolders()) {
            File modFolder = Paths.get(armaFolder.getAbsolutePath(), mod.getName()).toFile();
            if (modFolder.exists()) {
                try {
                    FileUtils.deleteDirectory(modFolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        refresh();
    }

    private void loadData() throws ClassNotFoundException, IOException {
        URL url = new URL(Constants.ANROP_ARMA3SYNC_REPOSITORY_SYNC_PATH);
        SyncTreeDirectory sync = Arma3Sync.readSyncFile(url);
        List<String> remoteFolders = sync.getList().stream().map(node -> node.getName().toLowerCase()).collect(Collectors.toList());
        List<String> localFolders = Arma3Folder.getFolders(armaFolder).stream().map(node -> node.toLowerCase()).collect(Collectors.toList());

        HashSet<String> folders = new HashSet<>();
        folders.addAll(localFolders);
        folders.removeAll(remoteFolders);

        List<ModFolder> modFolders = folders.stream().sorted().map(folder -> {
            return new ModFolder(folder);
        }).collect(Collectors.toList());

        modFolders.stream().forEach(mod -> {
            File modFolder = Paths.get(armaFolder.getAbsolutePath(), mod.getName()).toFile();
            mod.setSize(FileUtils.sizeOf(modFolder));
        });

        this.folders.setAll(modFolders);
    }

    @Override
    public ObservableValue<Boolean> call(ModFolder item) {
        BooleanProperty observable = new SimpleBooleanProperty();
        observable.set(this.selectedFolders.contains(item));
        observable.addListener((obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                        this.selectedFolders.add(item);
                    } else {
                        this.selectedFolders.remove(item);
                    }
                }
        );
        return observable;
    }
}
