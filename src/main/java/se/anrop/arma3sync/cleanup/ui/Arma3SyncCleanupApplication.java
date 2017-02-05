package se.anrop.arma3sync.cleanup.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
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
        this.listView = new ListView<>();
        this.viewModel = new ListViewModel(listView);

        BorderPane root = new BorderPane(this.listView);
        Scene scene = new Scene(root, 250, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
