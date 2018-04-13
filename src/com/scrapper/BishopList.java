package com.scrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class BishopList implements Iterable<Bishop>{
    private ArrayList<Bishop> bishopList;

    public BishopList() {
        bishopList = new ArrayList<>();
    }

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

    protected Bishop get(int index){
        return bishopList.get(index);
    }

    protected int size(){

        return bishopList.size();
    }

    public String toString() {
        String s = "";
        for(Bishop item: this.bishopList){
            s =  s + "\n" + item.getLast();
        }
        return s;
    }

    @Override
    public Iterator iterator() {
        return bishopList.iterator();
    }
}