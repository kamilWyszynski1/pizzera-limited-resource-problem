package sample.Sync;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pizzeria {
    private Table[] tables = new Table[4];
    private final Lock lockBig = new ReentrantLock();
    private final Lock lockSmall = new ReentrantLock();
    private boolean priority = false;

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
        if (amountOfBigTables == 1) {
            priority = true;
            for (Table table: this.tables) {
                if (table.getSize() >= 3)
                    table.setPriority();
            }
        }
        else if (amountOfBigTables == 0) {
            priority = true;
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
        System.out.println(priority);

    }

    public int[] getSizes(){
        int[] sizes = new int[4];
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = tables[i].getSize();
        }

        return sizes;
    }

    public int find_place(ClientGroup clientGroup) throws InterruptedException {
        int i = 0;
        int place = 0;
        Table[] appropriateTables;
        if (clientGroup.getGroupSize() < 3) {
            appropriateTables = Arrays.stream(tables).filter(
                    x -> x.getSize() >= clientGroup.getGroupSize() && !x.isPriority())
                    .toArray(Table[]::new);

        }
        else{
            appropriateTables = Arrays.stream(tables).filter(
                    x -> x.getSize() >= clientGroup.getGroupSize())
                    .toArray(Table[]::new);
        }

        while(true) {
            Table table = appropriateTables[i];
            if (priority){
                if (clientGroup.getGroupSize() >= 2) {
                    lockBig.lock();
                    if (table.canSit(clientGroup)) {
                        place = table.occupy(clientGroup);
                        lockBig.unlock();
                        return place;
                    }
                    lockBig.unlock();

                }
                else{
                    lockSmall.lock();
                    if (table.canSit(clientGroup)) {
                        place = table.occupy(clientGroup);
                        lockSmall.unlock();
                        return place;
                    }
                    lockSmall.unlock();
                }

            }
            else{
                lockBig.lock();
                if (table.canSit(clientGroup)) {
                    place = table.occupy(clientGroup);
                    lockBig.unlock();
                    return place;
                }
                lockBig.unlock();
            }
            i = (i + 1) % appropriateTables.length;
        }
    }
}
