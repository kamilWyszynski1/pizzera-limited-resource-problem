package sample.Sync;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import sample.controllers.Controller;

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
    private int place = 0;


    public Paint getColor() {
        return color;
    }

    public ClientGroup(int size, int id, Pizzeria pizz, Controller controller){
        super(String.valueOf(id));
        pizzeria = pizz;
        this.groupSize = size;
        this.controller = controller;
        this.generator = new Random();
        this.randomize();
        System.out.println("grupa o licznosci: " + this.groupSize);
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
        boolean run = true;

        while(run) {
            Platform.runLater(() -> controller.showGroup(this));

            try {
                Thread.sleep(3000);
                place = pizzeria.find_place(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName()+"."+this.getGroupSize()+" -> "+this.table.getId()+"."+this.table.getSize());
            Platform.runLater(() -> {
                try {
                    controller.moveGroup(this, place);
                } finally {
                    doneLatch.countDown();
                }
            });


            // group leaves
            try {
                doneLatch.await();
                Thread.sleep(this.generator.nextInt(8000));
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
                System.out.println(getName()+" <- "+this.table.getId()+"."+this.table.getSize());

                this.table.release(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.table = null;
            this.randomize();
//            run = false;
        }

    }

    private void randomize(){
        this.groupSize = generator.nextInt(3)+1;
        this.color = Color.rgb(generator.nextInt(255), generator.nextInt(255), generator.nextInt(255));
        this.place = 0;
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
