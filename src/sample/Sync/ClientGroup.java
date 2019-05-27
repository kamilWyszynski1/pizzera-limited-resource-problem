package sample.Sync;

import sample.Controller;

import java.util.Random;

public class ClientGroup extends Thread {
    private int groupSize;
    private int phase = 0;
    private Random generator;
    private static final Pizzeria pizzeria = new Pizzeria();
    private Controller controller;

    public void setTable(Table table) {
        this.table = table;
    }

    private Table table;

    public ClientGroup(int id, int size){
        super(String.valueOf(id));
        this.generator = new Random();
        this.groupSize = size;
        System.out.println("grupa o licznosci: " + this.groupSize);

    }

    public ClientGroup(int id, Controller controller){
        super(String.valueOf(id));
        this.controller = controller;
        this.generator = new Random();
        this.groupSize = generator.nextInt(3)+1;
        System.out.println("grupa o licznosci: " + this.groupSize);
    }

    public void run() {
        while (true) {
            controller.show(this);
            System.out.println("przychodzi do pizzeri" + getName());
            pizzeria.find_place(this);
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
    }

    public int getGroupSize() {
        return groupSize;
    }

}
