package sample.Sync;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import sample.Controller;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ClientGroup extends Thread {
    private int groupSize;
    private int phase = 0;
    private Random generator;
    private static Pizzeria pizzeria;
    private Controller controller;
    private Table table;
    private Color color;

    public Paint getColor() {
        return color;
    }


    public ClientGroup(int id, Pizzeria pizz, Controller controller){
        super(String.valueOf(id));
        pizzeria = pizz;
        this.controller = controller;
        this.generator = new Random();
        this.randomize();
        System.out.println("grupa o licznosci: " + this.groupSize);
    }

    public void run() {
        final CountDownLatch doneLatch = new CountDownLatch(1);
        while(true) {
            Platform.runLater(() -> controller.showGroup(this));
            System.out.println("przychodzi do pizzeri" + getName());
            pizzeria.find_place(this);

            Platform.runLater(() -> {
                try {
                    controller.moveGroup(this);
                } finally {
                    doneLatch.countDown();
                }
            });
            System.out.println("znajduje miejsce");


            // group leaves
            try {
                doneLatch.await();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                try {
                    controller.removeGroup(this);
                } finally {
                    doneLatch.countDown();
                }
            });

            try {
                doneLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.table.release(this);
            this.table = null;
            this.randomize();
        }

    }

    private void randomize(){
        this.groupSize = generator.nextInt(3)+1;
        this.color = Color.rgb(generator.nextInt(255), generator.nextInt(255), generator.nextInt(255));
    }

    public int getGroupSize() {
        return groupSize;
    }

    public Table getTable() {
        return this.table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

}
