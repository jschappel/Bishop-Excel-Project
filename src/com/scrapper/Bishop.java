package com.scrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by joshuaschappel on 12/8/17.
 */
public class Bishop {
    private String sal;
    private String first;
    private String middle;
    private String last;
    private String suffix;
    private String title;
    private String insideSal;
    private String dioceseName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;


    protected Bishop(String sal, String first, String middle, String last, String suffix, String title, String insideSal, String dioceseName, String address1, String address2, String city, String state, String zip){
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

    protected String getBishopLastName() {
        return this.last;
    }
}