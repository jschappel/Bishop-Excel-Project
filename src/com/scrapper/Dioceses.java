package com.scrapper;

import java.util.ArrayList;

/**
 * Created by joshuaschappel on 12/8/17.
 */
public class Dioceses {
    private String sal;
    private ArrayList<String> first;
    private ArrayList<String> middle;
    private ArrayList<String> last;
    private ArrayList<String> suffix;
    private ArrayList<String> title;
    private String insideSal;
    private String dioceseName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;


    protected Dioceses(String sal, ArrayList<String> first, ArrayList<String> middle, ArrayList<String> last, ArrayList<String> suffix, ArrayList<String> title, String insideSal, String dioceseName, String address1, String address2, String city, String state, String zip){
        this.sal = sal;
        this.first = first;
        this.middle = middle;
        this.last = last;
        this.suffix = suffix;
        this.title = title;
        this.insideSal = insideSal;
        this.dioceseName = dioceseName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    protected String getDioceseName(){
        return this.dioceseName;
    }

    protected String returnFirst(){
        String names = "";
        System.out.println(first.size());
        for(int i = 0; i < first.size(); i++) {
            names = names + " " + title.get(i) + " " + first.get(i) + " " + middle.get(i) + " " + last.get(i) + " " + suffix.get(i) + " " + dioceseName + " " + address1 +  " " + address2 + " " + city + " " + state +  " " + zip + "\n";
        }
        return names.trim();
    }

    protected int size1() {
        return first.size();
    }
}