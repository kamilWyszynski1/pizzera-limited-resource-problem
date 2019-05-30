package sample.Sync;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pizzeria {
    private Table[] tables = new Table[4];
    private final Lock lock = new ReentrantLock();

    /**
     * Tables initialization. Table sizes are random Integers
     * between 1 and 4. We need to handle situation when
     * there are only tables with sizes 1 or 2. Additionally if
     * there's lack of big tables we need to set priority for bigger groups.*/
    public Pizzeria() {
        // initialize table's array
        for (int i = 0; i < this.tables.length ; i++) {
            this.tables[i] = new Table(i);
        }

        // check their sizes
        int amountOfBigTables = 0;
        for (Table table: this.tables) {
            if (table.getSize() > 2)
                amountOfBigTables++;
        }

        // we need to provide at least one table with 3 seats
        if (amountOfBigTables == 0) {
            this.tables[0].setSize(3);
            this.tables[0].setPriority();
        } else if (amountOfBigTables < 2) {
            for (Table table: this.tables) {
                if (table.getSize() == 3 || table.getSize() == 4) {
                    table.setPriority();
                    return;
                }
            }
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
        Table[] appropriateTables = Arrays.stream(tables).filter(x -> x.getSize() >= clientGroup.getGroupSize())
                                            .toArray(Table[]::new);
        System.out.println("!!!!!!!!!"+ Arrays.toString(appropriateTables));
        while(true) {
            lock.lock();
            Table table = appropriateTables[i];
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
            i = (i+1)%appropriateTables.length;
        }
    }
}
