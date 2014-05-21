import java.util.Scanner;

/**
 * Created by Felipe on 5/15/2014.
 */
public abstract class Hash_Table {

    class Bucket {
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

    protected static int BUCKET_SIZE = 1000;
    protected Bucket[] table;
    protected int size;
    protected int collision;

    public Hash_Table() {
        collision = size = 0;
        table = new Bucket[BUCKET_SIZE];
        for (int i = 0; i < BUCKET_SIZE; i++) {
            table[i] = new Bucket();
        }
    }

    public int hash(String string) {
        long hash = 5381;
        for (int i = 0; i < string.length(); i++)
            hash = ((hash << 5) + hash) + string.charAt(i);
        return (int) (hash % BUCKET_SIZE);
    }

    public abstract void insertNewData(String newData, Bucket[] t);
    public abstract void resizeTable();

    public void print() {
        System.out.println("Printing contents of hash table:");
        for (int i = 0; i < BUCKET_SIZE; i++) {
            if (table[i].data != null) System.out.println(table[i].data);
            if (table[i].next != null) {
                Bucket curr = table[i];
                while (curr.next != null) {
                    curr = curr.next;
                    System.out.println(curr.data);
                }
            }
        }
        System.out.println("Number of collisions: " + collision);
    }

    public Bucket[] getTable() {
        return table;
    }

    public String getRandString() {
        final String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String ret = "";
        for (int i = 0; i < 10; i++) {
            int randIndex = (int) (Math.random() * alphabet.length());
            ret += alphabet.charAt(randIndex);
        }
        return ret;
    }

    public static void main(String[] args) {
        Hash_Table hashTable1 = new Hash_Table_Using_Chaining();
        //Hash_Table hashTable2 = new Hash_Table_Using_Probing();

        for (int i = 0; i < 50000; i++) {
            hashTable1.insertNewData(hashTable1.getRandString(), hashTable1.getTable());
        }
        hashTable1.print();
    }
}
