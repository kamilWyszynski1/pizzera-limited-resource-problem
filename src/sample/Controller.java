package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
//    public Rectangle[] tables = new Rectangle[]{table1, table2, table3, table4};
    public int[] sizes = new int[4];

    public Button button;

    private Pane[] containers = new Pane[4];
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
        // Create 5 HBoxes for first group's positions
        for (int i = 0; i < hboxes.length; i++) {
            HBox box = new HBox();
            box.setLayoutX(100+95*i);
            box.setLayoutY(520);
            box.setPrefSize(20, 60);
            hboxes[i] = box;
        }
        pane.getChildren().addAll(hboxes);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Pane container = new Pane();
                Rectangle rectangle = new Rectangle();
                // container for table
                container.setLayoutX(50+(j*300));
                container.setLayoutY(50+(i*150));
                container.setPrefHeight(200);
                container.setPrefWidth(200);

                rectangle.setWidth(120);
                rectangle.setHeight(80);
                rectangle.setLayoutX(50);
                rectangle.setLayoutY(55);
                rectangle.setStrokeWidth(1.0);
                rectangle.setStroke(Color.BLACK);
                rectangle.setFill(Color.DARKCYAN);

                container.getChildren().add(rectangle);
                pane.getChildren().add(container);
                containers[2*i+j] = container;
            }
        }

    }

    @FXML
    private void change(){
        button.setText("elo");
    }

    /**
     * Method that shows groups, represents them as circles in
     * separate HBoxes with diffrenet colors.
     *
     * Hboxes are used as containers for groups.
     * */
    public void showGroup(ClientGroup clientGroup){
        for (int i = 0; i < clientGroup.getGroupSize();i++) {
            Circle circle = new Circle();
            circle.setFill(clientGroup.getColor());
//            circle.setFill(Color.rgb(100, 100, 100));
            circle.setRadius(10);
            hboxes[Integer.parseInt(clientGroup.getName())].getChildren().add(circle);
        }
    }

    /**
     * Called right after cachier gives table to the group.
     * Hbox is moved to the given table. Table's index is written
     * in clientGroup.Table.id variable.
     * */
    public void moveGroup(ClientGroup clientGroup){
//        if (containers[clientGroup.getTable().getId()].getChildren() != null) {
//
//            hboxes[Integer.parseInt(clientGroup.getName())]
//                    .setLayoutX(containers[clientGroup.getTable().getId()].getLayoutX());
//
//
//            hboxes[Integer.parseInt(clientGroup.getName())]
//                    .setLayoutY(containers[clientGroup.getTable().getId()].getLayoutY() + containersChildrenSize*20);
//        }
//        else{
        int containersChildrenSize = containers[clientGroup.getTable().getId()].getChildren().size();
        System.out.println(containersChildrenSize);
        hboxes[Integer.parseInt(clientGroup.getName())]
                .setLayoutX(containers[clientGroup.getTable().getId()].getLayoutX());


        hboxes[Integer.parseInt(clientGroup.getName())]
                .setLayoutY(containers[clientGroup.getTable().getId()].getLayoutY()+
                        containersChildrenSize*20);
//        }
    }

    /**
     * Called right after group Thread perform its critical section.
     * Set group's HBox to the default position.*/
    public void removeGroup(ClientGroup clientGroup){
        hboxes[Integer.parseInt(clientGroup.getName())]
                .setLayoutX(100+95*Integer.parseInt(clientGroup.getName()));

        hboxes[Integer.parseInt(clientGroup.getName())]
                .setLayoutY(520);

        hboxes[Integer.parseInt(clientGroup.getName())].getChildren().clear();
    }

    public void setSizes(int[] sizes) {
        this.sizes = sizes;

        for (int i = 0; i < sizes.length ; i++) {
            Rectangle[] chairs = new Rectangle[sizes[i]];
            for (int j = 0; j < sizes[i]; j++) {
                Rectangle chair = new Rectangle();
                chair.setLayoutX(10*j);
                chair.setWidth(8);
                chair.setHeight(10);
                chair.setFill(Color.PINK);
                chairs[j] = chair;
            }
            containers[i].getChildren().addAll(chairs);
        }

    }
}
