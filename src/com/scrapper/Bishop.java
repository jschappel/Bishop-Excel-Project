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
    private String dioShortName;


    protected Bishop(String sal, String first, String middle, String last, String suffix, String title, String insideSal, String dioShortName, String dioceseName, String address1, String address2, String city, String state, String zip){
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
        this.dioShortName = dioShortName;
    }

    protected String getState() {
        return this.state;
    }
    protected String getInsideSal() {
        return this.insideSal;
    }
    protected String getAddress1() {
        return this.address1;
    }
    protected String getAddress2() {
        return this.address2;
    }
    protected String getZip() {
        return this.zip;
    }
    protected String getDioceseName(){
        return this.dioceseName;
    }
    protected String getSal(){
        return this.sal;
    }
    protected String getTitle(){
        return this.title;
    }
    protected String getDioShortName(){
        return this.dioShortName;
    }
    protected String getCity() {
        return this.city;
    }
    protected String getLast() {
        return this.last;
    }
    protected String getFirst() {
        return this.first;
    }
    protected String getMiddle() {
        return this.middle;
    }
    protected String getSuffix(){
        return this.suffix;
    }

}