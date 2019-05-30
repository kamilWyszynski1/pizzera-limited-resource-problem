package sample.Sync;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Table {
    private int id;

    private int size;

    private Semaphore tableSem;
    private int occupation = 0;
    private ArrayList<ClientGroup> clientGroups;
    private boolean priority = false;

    public Table(int id) {
        this.id = id;
        this.size = new Random().nextInt(4)+1;
        this.tableSem = new Semaphore(size);
        this.clientGroups = new ArrayList<ClientGroup>();
        System.out.println("Stolik z " + this.size + " miejscami");
    }
    /**
     * Methods determines if table can be occupied by group.
     * We need to use Iterator to iterate through
     * clientGroups that already sit to avoid
     * ConcurrentModificationException.*/
    public synchronized boolean canSit(ClientGroup clientGroup) throws InterruptedException {
        if (!this.priority) {
            if(size-occupation < clientGroup.getGroupSize()) {
                return false;
            }

            Iterator<ClientGroup> iter = this.clientGroups.iterator();
            while (iter.hasNext()) {
                ClientGroup group = iter.next();

                if(group.getGroupSize() != clientGroup.getGroupSize()) {
                    return false;
                }
            }
            return true;
        }
        else{
            while(this.getOccupation() != 0)
                wait();
            return true;
        }
    }

    public synchronized void occupy(ClientGroup clientGroup) throws InterruptedException {
        clientGroup.setTable(this);
        this.occupation += clientGroup.getGroupSize();
        this.clientGroups.add(clientGroup);
        }

    public synchronized void release(ClientGroup clientGroup) throws InterruptedException {

       this.occupation -= clientGroup.getGroupSize();
       this.clientGroups.remove(clientGroup);
       notifyAll();
    }

    public int getId() {
        return id;
    }

    public int getOccupation(){
        return this.occupation;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setPriority(){ this.priority = true;}

    @Override
    public String toString() {
        return "Table{" +
                "size=" + size +
                '}';
    }
}

