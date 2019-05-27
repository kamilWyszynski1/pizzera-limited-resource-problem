package sample.Sync;

import java.util.Random;

public class ClientGroup extends Thread {
    private int groupSize;
    private int phase = 0;
    private Random generator;
    private static final Pizzeria pizzeria = new Pizzeria();

    public void setTable(Table table) {
        this.table = table;
    }

    private Table table;

    public ClientGroup(){
        this.generator = new Random();
        this.groupSize = generator.nextInt(3)+1;
        System.out.println("grupa o licznosci: " + this.groupSize);
    }

    public void run() {
        System.out.println("przychodzi do pizzeri");
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

    public int getGroupSize() {
        return groupSize;
    }

}
