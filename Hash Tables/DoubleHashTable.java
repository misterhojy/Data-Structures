package cse214hw4;

import java.util.function.Function;

public class DoubleHashTable implements OpenAddressTable {
    private final int TABLESIZE;
    private final Function<Integer, Integer> h1;
    private final Function<Integer, Integer> h2;
    private final Integer [] table;
    private int entries = 0;

    public DoubleHashTable(int m,Function<Integer, Integer> h1, Function<Integer, Integer> h2){
        this.TABLESIZE = m;
        this.h1 = h1;
        this.h2 = h2;
        this.table = new Integer [m];
    }

    @Override
    public double loadFactor() {
        return (double) entries / TABLESIZE;
    }

    @Override
    public void insert(int value) {
        if (loadFactor() == 1){
            System.out.println("Table is full, " + value + " can't be inserted.");
        }
        else {
            if (value < 0){
                throw new NullPointerException(value + "Can't be inserted");
            }
            int index = h1.apply(value) % TABLESIZE;
            if (table[index] == null) {
                table[index] = value;
                entries++;
            } else {
                if(isDupe(index, value)){
                    table[index] = value;
                }else {
                    int position = hash(index, value);
                    if(table[position] == null){
                        table[position] = value;
                        entries++;
                    }else {
                        System.out.println(value + " can't be inserted.");
                    }

                }
            }
        }
    }

    private boolean isDupe(int key, int value){
        return table[key] == value;
    }

    @Override
    public int find(int value) {
        int index = h1.apply(value) % TABLESIZE;
        if (table[index] == null){
            return -1;
        } else if (table[index] == value){
            return index;
        } else{
            int position = hash(index,value);
            if (table[position] != null && table[position] == value){
                return position;
            }else {
                return -1;
            }
        }
    }

    @Override
    public int hash(int key, int probenumber) {
        int i = 1;
        while (table[key] != null && table[key] != probenumber && i <= entries){
            key = (h1.apply(probenumber) + i * h2.apply(probenumber)) % TABLESIZE;
            i++;
        }
        return key;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < TABLESIZE; i++){
            if(table[i] != null) {
                result.append(i).append(" -> ").append(table[i]).append(", ");
            }
        }
        return result.toString();
    }

    public static void main(String[] args){
        int tableSize = 5;
        Function<Integer, Integer> h1 = integer -> (Integer.parseInt(Integer.toString(integer).substring(0,1)) % tableSize);
        Function<Integer, Integer> h2 = integer -> (Integer.parseInt(Integer.toString(integer).substring(Integer.toString(integer).length()-1)) % tableSize);
        DoubleHashTable table = new DoubleHashTable(tableSize, h1, h2);
        table.insert(4);
        table.insert(5);
        table.insert(21);
        table.insert(2);
        System.out.println(table);
    }
}
