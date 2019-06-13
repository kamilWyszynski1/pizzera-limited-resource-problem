package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController {
    public TextField threadCount;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public HomeController() {}

    @FXML
    private void initialize(){}

    @FXML
    private void submit() throws IOException {
        int threads = Integer.parseInt(threadCount.getText());
        System.out.println(threads);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/sample.fxml"));
        loader.setController(new Controller());

        Parent root = loader.load();
        Scene scene = new Scene(root, 640, 640);

        scene.getRoot().requestFocus();

    }
}
