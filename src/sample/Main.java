package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Sync.ClientGroup;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent) loader.load();
        Controller controller = loader.getController();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 640, 640));
        primaryStage.show();


        for (int i = 0; i < 5; i++) {
            Platform.runLater(new ClientGroup(i, controller));
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
