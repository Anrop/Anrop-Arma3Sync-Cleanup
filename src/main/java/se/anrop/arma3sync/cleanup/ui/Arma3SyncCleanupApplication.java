package se.anrop.arma3sync.cleanup.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import se.anrop.arma3sync.cleanup.viewmodels.ListViewModel;

import java.io.IOException;

/**
 * Created by bjorn on 2017-02-05.
 */
public class Arma3SyncCleanupApplication extends Application {

    ListView listView;
    ListViewModel viewModel;

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        this.viewModel = new ListViewModel();

        this.listView = new ListView<>();
        this.listView.setCellFactory(CheckBoxListCell.forListView(this.viewModel));
        this.listView.setItems(this.viewModel.getFolders());

        BorderPane root = new BorderPane(this.listView);
        Scene scene = new Scene(root, 250, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
