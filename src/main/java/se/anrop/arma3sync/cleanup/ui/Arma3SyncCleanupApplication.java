package se.anrop.arma3sync.cleanup.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import se.anrop.arma3sync.cleanup.viewmodels.ListViewModel;

import java.io.File;
import java.io.IOException;

/**
 * Created by bjorn on 2017-02-05.
 */
public class Arma3SyncCleanupApplication extends Application {

    Button chooseFolderButton;
    Button deleteFoldersButton;
    Label modsPath;
    ListView listView;
    ListViewModel viewModel;

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        this.viewModel = new ListViewModel();

        this.modsPath = new Label();
        this.modsPath.textProperty().bind(this.viewModel.modsFolder());

        this.chooseFolderButton = new Button("Choose mods folder");
        this.chooseFolderButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File(this.viewModel.modsFolder().get()));
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            this.viewModel.modsFolder().set(selectedDirectory.getAbsolutePath());
        });

        this.deleteFoldersButton = new Button("Remove Selected Mods");
        this.deleteFoldersButton.setOnAction(event -> {
            this.viewModel.deleteSelectedFolders();
        });

        this.listView = new ListView<>();
        this.listView.setCellFactory(CheckBoxListCell.forListView(this.viewModel));
        this.listView.setItems(this.viewModel.getFolders());

        HBox topBox = new HBox(this.modsPath, this.chooseFolderButton);
        topBox.setPadding(new Insets(12, 12, 12, 12));
        topBox.setAlignment(Pos.BASELINE_CENTER);
        topBox.setSpacing(12);

        HBox bottomBox = new HBox(this.deleteFoldersButton);
        bottomBox.setPadding(new Insets(12, 12, 12, 12));
        bottomBox.setAlignment(Pos.BASELINE_CENTER);

        BorderPane root = new BorderPane(this.listView);
        root.setTop(topBox);
        root.setBottom(bottomBox);
        Scene scene = new Scene(root, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Arma3Sync Cleanup");
        primaryStage.show();
    }
}
