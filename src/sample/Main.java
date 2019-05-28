package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Sync.ClientGroup;
import sample.Sync.Pizzeria;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pizzeria pizzeria = new Pizzeria();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();

        for (int i = 0; i < 5; i++) {
            (new ClientGroup(i, pizzeria, controller)).start();
        }

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 640, 640));
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
}
