package sample.Sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pizzeria {
    private Table[] tables = new Table[4];
    private final Lock lock = new ReentrantLock();

    public Pizzeria() {
        for (int i = 0; i < tables.length ; i++) {
            tables[i] = new Table(i);
        }
    }

    public int[] getSizes(){
        int[] sizes = new int[4];
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = tables[i].getSize();
        }

        return sizes;
    }

    public void find_place(ClientGroup clientGroup) throws InterruptedException {
        int i = 0;
        while(true) {
            lock.lock();
            Table table = tables[i];
            if (table.canSit(clientGroup)) {
                try {
                    lock.unlock();
                    table.occupy(clientGroup);
                    return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.unlock();
            i = (i+1)%4;
        }
    }
}
