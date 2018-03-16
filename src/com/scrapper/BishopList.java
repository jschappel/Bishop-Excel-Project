package com.scrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BishopList {
    private ArrayList<Bishop> bishopList;


    public BishopList() {

        bishopList = new ArrayList<>();

    }

    protected void add(Bishop bishop) {
        if(this.size() > 0) {
            Comparator<Bishop> comp = new Comparator<Bishop>() {
                @Override
                public int compare(Bishop o1, Bishop o2) {
                    return o1.getBishopLastName().compareTo(o2.getBishopLastName());
                }
            };

            int index = Collections.binarySearch(bishopList, new Bishop(null, null, null, bishop.getBishopLastName(), null, null, null, null, null, null, null, null, null), comp);
            bishopList.add(Math.abs(index) -1, bishop);
        } else{
            bishopList.add(bishop);
        }
    }

    protected int size(){

        return bishopList.size();
    }

    public String toString() {
        String s = "";
        for(Bishop item: this.bishopList){
            s =  s + "\n" + item.getBishopLastName();
        }
        return s;
    }
}