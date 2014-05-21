/**
 * Created by fmontei1 on 5/20/2014.
 */
public class Hash_Table_Using_Probing extends Hash_Table {

    public Hash_Table_Using_Probing() {
        super();
    }

    @Override
    public void insertNewData(String newData, Bucket[] t) {
        int index = generateIndexByHashing(newData);
        if (t[index].data == null)
            t[index].data = newData;
        else {
            collision++;
            int duration = 0;
            Bucket curr = t[index];
            while (curr.data != null) {
                if (tooMuchTimeElapsed(duration)) {
                    resizeTable();
                }

                index = (index + 1) % BUCKET_SIZE;
                curr = t[index];
                duration++;
            }
            curr.data = newData;
        }
        incrementTableSize();

        if (isTableTooFull())
            resizeTable();
    }

    private boolean tooMuchTimeElapsed(int duration) { return duration >= BUCKET_SIZE; }

    @Override
    public void resizeTable() {
        final int oldSize = BUCKET_SIZE;
        Bucket[] newTable = createNewTable();

        System.out.println("Resizing table. Previous size: " + oldSize +
                ". New size: " + BUCKET_SIZE);
        size = 0;

        for (int i = 0; i < oldSize; i++) {
            if (table[i].data != null) {
                insertNewData(table[i].data, newTable);
            }
        }

        table = newTable;
    }
}
