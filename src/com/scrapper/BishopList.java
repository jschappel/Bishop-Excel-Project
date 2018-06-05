package com.scrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class BishopList implements Iterable<Bishop>{
    private ArrayList<Bishop> bishopList;

    /**
     * Constructor used for creating a BishopList
     */
    public BishopList() {
        bishopList = new ArrayList<>();
    }

    /**
     * Custom add function that uses Binary search the place the Bishops in  alphabetical order ( A-Z) by
     * Bishop's last name.
     * @param bishop a Bishop object
     */
    protected void add(Bishop bishop) {
        if(this.size() > 0) {
            Comparator<Bishop> comp = new Comparator<Bishop>() {
                @Override
                public int compare(Bishop o1, Bishop o2) {
                    return o1.getLast().compareTo(o2.getLast());
                }
            };

            int index = Collections.binarySearch(bishopList, new Bishop(null, null, null, bishop.getLast(), null, null, null, null, null, null, null, null, null, null), comp);
            bishopList.add(Math.abs(index) -1, bishop);
        } else{
            bishopList.add(bishop);
        }
    }

    /**
     * Returns the size of the bishopList
     * @return of type Integer
     */
    protected int size(){

        return bishopList.size();
    }

    /**
     * Overrides the existing toString method
     * @return of type String
     */
    public String toString() {
        String s = "";
        for(Bishop item: this.bishopList){
            s =  s + "\n" + item.getLast();
        }
        return s;
    }

    /**
     * A custom iterator for BishopList
     * @return a bishopList Iterator
     */
    @Override
    public Iterator iterator() {
        return bishopList.iterator();
    }
}