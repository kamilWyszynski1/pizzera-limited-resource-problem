package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import sample.Sync.ClientGroup;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller's class to handle:
 * 1. Initialization of Tables and Hboxes
 *    which contains ClientGroups graphical representation
 * 2. Graphical represenation of ClientGroups as Circles with
 *    diffrenet colors.
 * 3. Movement of ClientGroups - moving towards table and removing
 *    Group from the table.
 **/
public class Controller {
    private int amountOfBoxes;

    public Button button;

    private Pane[] containers = new Pane[4];

    private HBox[] hboxes;
    public AnchorPane pane;
    public Controller() {
    }

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    /***/
    @FXML
    private void initialize(){
        // Create 5 HBoxes for first group's positions

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Pane container = new Pane();
                Rectangle rectangle = new Rectangle();
                // container for table
                container.setLayoutX(50+(j*300));
                container.setLayoutY(50+(i*150));
                container.setPrefHeight(200);
                container.setPrefWidth(200);

                rectangle.setWidth(100);
                rectangle.setHeight(100);
                rectangle.setLayoutY(30);
                rectangle.setLayoutX(100);
                rectangle.setStrokeWidth(1.0);
                rectangle.setStroke(Color.BLACK);
                rectangle.setFill(Color.DARKCYAN);

                container.getChildren().add(rectangle);
                pane.getChildren().add(container);
                containers[2*i+j] = container;
            }
        }

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
    public void moveGroup(ClientGroup clientGroup, int place){

        hboxes[Integer.parseInt(clientGroup.getName())]
                .setLayoutX(containers[clientGroup.getTable().getId()].getLayoutX());


        hboxes[Integer.parseInt(clientGroup.getName())]
                .setLayoutY(containers[clientGroup.getTable().getId()].getLayoutY()+20*place);
    }

    /**
     * Called right after group Thread perform its critical section.
     * Set group's HBox to the default position.*/
    public void removeGroup(ClientGroup clientGroup){
        hboxes[Integer.parseInt(clientGroup.getName())]
                .setLayoutX(20+60*Integer.parseInt(clientGroup.getName()));

        hboxes[Integer.parseInt(clientGroup.getName())]
                .setLayoutY(520+40*(clientGroup.getId()%2));

        hboxes[Integer.parseInt(clientGroup.getName())].getChildren().removeIf(n -> n instanceof Circle);

    }

    /**
     * Method sets sizes of each table.*/
    public void setSizes(int[] sizes) {
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
            containers[i].setAccessibleText(String.valueOf(0));
        }

    }

    /**
     * Sets amount of HBoxes which contain our groups.
     * And initialize them in some kind of grid.*/
    public void setAmountOfBoxes(int amountOfBoxes) {
        this.amountOfBoxes = amountOfBoxes;

        this.hboxes = new HBox[this.amountOfBoxes];
        for (int i = 0; i < hboxes.length; i++) {
            HBox box = new HBox();
            box.setLayoutX(20+60*i);
            box.setLayoutY(520+40*(i%2));
            box.setPrefSize(20, 60);

            Label label = new Label();
            label.setPrefWidth(20);
            label.setPrefHeight(10);
            label.setText(String.valueOf(i));
            box.getChildren().add(label);
            hboxes[i] = box;
        }
        pane.getChildren().addAll(hboxes);
    }
}
