/**
 * Created by fmontei1 on 5/20/2014.
 */
public class Hash_Table_Using_Chaining extends Hash_Table {

    public Hash_Table_Using_Chaining() {
        super();
    }

    public void insertNewData(String newData, Bucket[] t) {
        int index = hash(newData);
        if (t[index].data == null)
            t[index].data = newData;
        else {
            collision++;
            Bucket curr = t[index];
            while (curr.next != null) {
                curr = curr.next;
            }

            curr.next = new Bucket(newData);
        }
        size++;

        if (size == (BUCKET_SIZE / 2))
            resizeTable();
    }

    public void resizeTable() {
        final int oldSize = BUCKET_SIZE;
        BUCKET_SIZE *= 2;
        Bucket[] newTable = new Bucket[BUCKET_SIZE];
        for (int i = 0; i < BUCKET_SIZE; i++) {
            newTable[i] = new Bucket();
        }

        System.out.println("Resizing table. Previous size: " + oldSize +
                ". New size: " + BUCKET_SIZE);
        size = 0;

        for (int i = 0; i < oldSize; i++) {
            if (table[i].data != null) {
                insertNewData(table[i].data, newTable);
                if (table[i].next != null) {
                    Bucket curr = table[i];
                    while (curr.next != null) {
                        curr = curr.next;
                        insertNewData(curr.data, newTable);
                    }
                }
            }
        }

        table = newTable;
    }
}
