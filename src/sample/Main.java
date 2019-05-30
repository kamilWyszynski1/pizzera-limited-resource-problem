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
        int threadsAmount  = 10;
        Pizzeria pizzeria = new Pizzeria();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setSizes(pizzeria.getSizes());
        controller.setAmountOfBoxes(threadsAmount);


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 640, 640));
        primaryStage.show();

        for (int i = 0; i < threadsAmount; i++) {
            (new ClientGroup(2, i,  pizzeria, controller)).start();
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
