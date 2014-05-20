/**
 * Created by Felipe on 5/15/2014.
 */
public class Hash_Table {

    class Bucket {
        private String data;
        private Bucket next;

        public Bucket() {
            data = null;
            next = null;
        }

        public Bucket(String newData) {
            data = newData;
            next = null;
        }
    }

    private final static String CHAINING_METHOD = "Chaining";
    private final static String PROBING_METHOD = "Probing";
    private static int BUCKET_SIZE = 1000;
    private Bucket[] table;
    private int size;
    private int collision;

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

    public void insert_using_chaining(String newData, Bucket[] t) {
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
            resize(CHAINING_METHOD);
    }

    public void insert_using_probing(String newData, Bucket[] t) {
        int index = hash(newData);
        if (t[index].data == null)
            t[index].data = newData;
        else {
            collision++;
            int duration = 0;
            Bucket curr = t[index];
            while (curr.data != null) {
                if (duration >= BUCKET_SIZE) {
                    resize(PROBING_METHOD);
                }

                index = (index + 1) % BUCKET_SIZE;
                curr = t[index];
                duration++;
            }
            curr.data = newData;
        }
        size++;

        if (size == (BUCKET_SIZE / 2))
            resize(PROBING_METHOD);
    }

    public void resize(final String method) {
        final int oldSize = BUCKET_SIZE;
        BUCKET_SIZE *= 2;
        Bucket[] newTable = new Bucket[BUCKET_SIZE];
        for (int i = 0; i < BUCKET_SIZE; i++) {
            newTable[i] = new Bucket();
        }

        System.out.println("Resizing table. Previous size: " + oldSize +
                ". New size: " + BUCKET_SIZE);
        size = 0;

        switch(method.toLowerCase().charAt(0)) {
            case 'c':
                for (int i = 0; i < oldSize; i++) {
                    if (table[i].data != null) {
                        insert_using_chaining(table[i].data, newTable);
                        if (table[i].next != null) {
                            Bucket curr = table[i];
                            while (curr.next != null) {
                                curr = curr.next;
                                insert_using_chaining(curr.data, newTable);
                            }
                        }
                    }
                }
                break;
            case 'p':
                for (int i = 0; i < oldSize; i++) {
                    if (table[i].data != null) {
                        insert_using_probing(table[i].data, newTable);
                    }
                }
                break;
            default:
                System.out.println("Unrecognized argument to method resize()");
                System.exit(1);
        }
        table = null;
        table = newTable;
    }

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
        Hash_Table hashTable = new Hash_Table();

        for (int i = 0; i < 50000; i++) {
            //hashTable.insert_using_probing(hashTable.getRandString(), hashTable.getTable());
            hashTable.insert_using_chaining(hashTable.getRandString(), hashTable.getTable());
        }
        hashTable.print();
    }
}
