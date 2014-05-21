import java.util.Scanner;

/**
 * Created by Felipe on 5/15/2014.
 */
public abstract class Hash_Table {

    protected class Bucket {
        protected String data;
        protected Bucket next;

        public Bucket() {
            data = null;
            next = null;
        }

        public Bucket(String newData) {
            data = newData;
            next = null;
        }
    }

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    protected int BUCKET_SIZE = 1000;
    protected Bucket[] table;
    protected int size;
    protected int collision;

    public abstract void insertNewData(String newData, Bucket[] t);
    public abstract void resizeTable();

    public Hash_Table() {
        collision = size = 0;
        table = new Bucket[BUCKET_SIZE];
        for (int i = 0; i < BUCKET_SIZE; i++) {
            table[i] = createNewBucket();
        }
    }

    /* Functions invoked by both subclasses */
    protected Bucket getNextBucket(Bucket curr) { curr = curr.next; return curr; }
    protected void createNewBucket(Bucket curr, String newData) { curr.next = new Bucket(newData); }
    protected Bucket createNewBucket() { return new Bucket(); }
    protected void incrementTableSize() { size++; }
    protected boolean isTableTooFull() { return size == (BUCKET_SIZE / 2); }

    protected Bucket[] createNewTable() {
        BUCKET_SIZE *= 2;
        Bucket[] newTable = new Bucket[BUCKET_SIZE];
        for (int i = 0; i < BUCKET_SIZE; i++) {
            newTable[i] = new Bucket();
        }
        return newTable;
    }

    protected int generateIndexByHashing(String string) {
        long hash = 5381;
        for (int i = 0; i < string.length(); i++)
            hash = ((hash << 5) + hash) + string.charAt(i);
        return (int) (hash % BUCKET_SIZE);
    }

    public void print() {
        System.out.println("Printing contents of hash table:");
        for (int i = 0; i < BUCKET_SIZE; i++) {
            if (table[i].data != null) System.out.println(table[i].data);
            if (table[i].next != null) {
                Bucket curr = table[i];
                while (curr.next != null) {
                    curr = getNextBucket(curr);
                    System.out.println(curr.data);
                }
            }
        }
        System.out.println("Number of collisions: " + collision);
    }

    public Bucket[] getTable() {
        return table;
    }

    public String genRandString() {
        String randomString = "";
        for (int i = 0; i < 10; i++) {
            int randIndex = (int) (Math.random() * ALPHABET.length());
            randomString += ALPHABET.charAt(randIndex);
        }
        return randomString;
    }

    public static void main(String... args) {
        Hash_Table hashTable1 = new Hash_Table_Using_Chaining();
        Hash_Table hashTable2 = new Hash_Table_Using_Probing();

        for (int i = 0; i < 50000; i++) {
            hashTable1.insertNewData(hashTable1.genRandString(), hashTable1.getTable());
            hashTable2.insertNewData(hashTable2.genRandString(), hashTable2.getTable());
        }
        hashTable1.print();

        System.out.println("\nPress ENTER to continue.");
        Scanner waitForInput = new Scanner(System.in);
        waitForInput.nextLine();

        hashTable2.print();
    }
}
