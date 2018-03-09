package com.scrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DiocesesList {
    ArrayList<Dioceses> diocesesList;


    public DiocesesList() {

        diocesesList = new ArrayList<Dioceses>();

    }

    protected void add(Dioceses dioceses) {
        if(this.size() > 0) {
            Comparator<Dioceses> comp = new Comparator<Dioceses>() {
                @Override
                public int compare(Dioceses o1, Dioceses o2) {
                    return o1.getDioceseName().compareTo(o2.getDioceseName());
                }
            };

            int index = Collections.binarySearch(diocesesList, new Dioceses(null, null, null, null, null, null, null, dioceses.getDioceseName(), null, null, null, null, null), comp);
            System.out.println(index);
            diocesesList.add(Math.abs(index) -1, dioceses);
        } else{
            diocesesList.add(dioceses);
        }
    }


    protected int size(){

        return diocesesList.size();
    }

    public String toString() {
        String s = "";
        for(Dioceses item: this.diocesesList){
            s =  s + "\n" + item.getDioceseName();
        }
        return s;
    }
}
