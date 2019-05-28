package sample.Sync;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Table {
    private int id;

    private int size;
    private Semaphore tableSem;
    private int occupation = 0;
    private ArrayList<ClientGroup> clientGroups;
    public Table(int id) {
        this.id = id;
        this.size = new Random().nextInt(4)+1;
        this.tableSem = new Semaphore(size);
        this.clientGroups = new ArrayList<ClientGroup>();
        System.out.println("Stolik z " + this.size + " miejscami");
    }

    public int getSize() {
        return size;
    }

    public boolean canSit(ClientGroup clientGroup){
        if(size-occupation < clientGroup.getGroupSize())
            return false;

        for (ClientGroup group : this.clientGroups) {
            if (clientGroup.getGroupSize() != group.getGroupSize())
                return false;
        }
        return true;
    }

    public synchronized void occupy(ClientGroup clientGroup) throws InterruptedException {
        while (this.size - this.occupation < clientGroup.getGroupSize()) {
                wait();
            }
        clientGroup.setTable(this);
        this.occupation += clientGroup.getGroupSize();
        this.clientGroups.add(clientGroup);
        }

    public synchronized void release(ClientGroup clientGroup){
       this.occupation -= clientGroup.getGroupSize();
       this.clientGroups.remove(clientGroup);
       notify();
    }

    public int getId() {
        return id;
    }
}

