package sample.Sync;

import javafx.application.Platform;
import sample.Controller;

import java.util.Random;

public class ClientGroup extends Thread {
    private int groupSize;
    private int phase = 0;
    private Random generator;
    private static Pizzeria pizzeria;
    private Controller controller;

    public void setTable(Table table) {
        this.table = table;
    }

    private Table table;

    public ClientGroup(int id, Pizzeria pizz, Controller controller){
        super(String.valueOf(id));
        pizzeria = pizz;
        this.controller = controller;
        this.generator = new Random();
        this.groupSize = generator.nextInt(3)+1;
        System.out.println("grupa o licznosci: " + this.groupSize);
    }

    public void run() {
        Platform.runLater(()-> controller.showGroup(this));
        System.out.println("przychodzi do pizzeri" + getName());
        pizzeria.find_place(this);
        Platform.runLater(()->controller.moveGroup(this));
        System.out.println("znajduje miejsce");

        try {
            Thread.sleep(generator.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // group leaves
        this.table.release(this);
        this.table = null;

    }

    public int getGroupSize() {
        return groupSize;
    }

}
