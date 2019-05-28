package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import sample.Sync.ClientGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    public Rectangle table1;
    public Rectangle table2;
    public Rectangle table3;
    public Rectangle table4;
    public Button button;

    private HBox[] hboxes = new HBox[5];
    public AnchorPane pane;

    public Controller() {
    }

    @FXML
    private URL location;
    @FXML
    private ResourceBundle resources;

    @FXML
    private void initialize(){
        for (int i = 0; i < hboxes.length; i++) {
            HBox box = new HBox();
            box.setLayoutX(100+95*i);
            box.setLayoutY(520);
            box.setPrefSize(20, 60);
            hboxes[i] = box;
        }
        pane.getChildren().addAll(hboxes);

    }

    @FXML
    private void change(){
        button.setText("elo");
    }

    public void showGroup(ClientGroup clientGroup){
        for (int i = 0; i < clientGroup.getGroupSize();i++) {
            Circle circle = new Circle();
            circle.setFill(Color.RED);
            circle.setRadius(10);
            hboxes[Integer.parseInt(clientGroup.getName())].getChildren().add(circle);
        }
    }

    public void moveGroup(ClientGroup clientGroup){

    }

}
