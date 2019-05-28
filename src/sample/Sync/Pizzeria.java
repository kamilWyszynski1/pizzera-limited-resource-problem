package sample.Sync;

public class Pizzeria {
    private Table[] tables = new Table[4];

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

    public void find_place(ClientGroup clientGroup){
        int i = 0;
        boolean found = false;
        while(!found) {
            Table table = tables[i];
            if (table.canSit(clientGroup)) {
                try {
                    table.occupy(clientGroup);
                    found = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i = (i+1)%4;
        }
    }
}
