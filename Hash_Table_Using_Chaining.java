/**
 * Created by fmontei1 on 5/20/2014.
 */
public class Hash_Table_Using_Chaining extends Hash_Table {

    public Hash_Table_Using_Chaining() {
        super();
    }

    public void insertNewData(String newData, Bucket[] table) {
        int index = generateIndexByHashing(newData);
        if (table[index].data == null)
            table[index].data = newData;
        else {
            collision++;
            Bucket curr = table[index];
            while (curr.next != null) {
                curr = getNextBucket(curr);
            }

            createNewBucket(curr, newData);
        }
        incrementTableSize();

        if (isTableTooFull())
            resizeTable();
    }

    public void resizeTable() {
        final int oldSize = BUCKET_SIZE;
        Bucket[] newTable = createNewTable(); // modifies BUCKET_SIZE
        System.out.println("Resizing table. Previous size: " + oldSize +
                ". New size: " + BUCKET_SIZE);
        size = 0; // restore size to zero b/c calling
                  // calling insertNewData recomputes size
        for (int i = 0; i < oldSize; i++) {
            if (table[i].data != null) {
                insertNewData(table[i].data, newTable);
                if (table[i].next != null) {
                    Bucket curr = table[i];
                    while (curr.next != null) {
                        curr = getNextBucket(curr);
                        insertNewData(curr.data, newTable);
                    }
                }
            }
        }

        table = newTable;
    }
}
