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
    private int groupsCount = 0;
    private int chairs[];

    public Table(int id) {
        this.id = id;
        this.size = new Random().nextInt(4)+1;
        this.tableSem = new Semaphore(size);
        this.clientGroups = new ArrayList<ClientGroup>();

        // 0 - chair is empty
        // 1 - chair is occupied
        this.chairs = new int[this.size];
        for (int i = 0; i < size ; i++) {
            chairs[i] = 0;
        }

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
    public synchronized int occupy(ClientGroup clientGroup) {
        clientGroup.setTable(this);
        occupation += clientGroup.getGroupSize();
        clientGroups.add(clientGroup);
        groupsCount++;

        for (int i = 0; i < size; i += clientGroup.getGroupSize()) {
            if (chairs[i] == 0){
                for (int j = i; j < clientGroup.getGroupSize(); j++) {
                    chairs[j] = 1;
                }
                return i;
            }
        }
        return 1;
    }

    public synchronized void release(ClientGroup clientGroup, int place) throws InterruptedException {
       occupation -= clientGroup.getGroupSize();
       clientGroups.remove(clientGroup);
       groupsCount--;
        for (int i = place; i < clientGroup.getGroupSize(); i++) {
            chairs[i] = 0;
        }
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

    public int getGroupsCount(){ return this.groupsCount; }

    public boolean isPriority() {
        return priority;
    }
}

