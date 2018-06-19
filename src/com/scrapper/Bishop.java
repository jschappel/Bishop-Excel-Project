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
    private String lotusCode;


    /**
     * Creates a Bishop Object. While this object can't be null, the parameters can be however this is
     * not recommended it is advised to use empty string for null case.
     * @param sal TODO "His Eminence" if cardinal else "Most Reverend"
     * @param first Bishop first name
     * @param middle Bishop middle name
     * @param last Bishop last name
     * @param suffix Bishop suffix (Ex: SDB, SJ...)
     * @param title Bishops Title
     * @param insideSal  TODO same as Title?
     * @param dioShortName The name of the diocese what the bishop is from (EX: Boston)
     * @param dioceseName The full name of the diocese (EX: Archdiocese of Boston)
     * @param address1 The full address of the diocese
     * @param address2 The full of a second address of the Diocese, or a P.O. Box
     * @param city The city that the diocese is located in
     * @param state The state that the diocese is located in
     * @param zip The Zip-code of the diocese
     */
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
        this.lotusCode = null;
    }

    /**
     * Adds a lotus code to a diocese
     * @param lotusCode A lotusCode that is of type Stirng
     */
    protected void addLotusCode(String lotusCode){
        this.lotusCode = lotusCode;
    }

    /**
     * Returns the state that the Bishops diocese resides in
     * @return of type string
     */
    protected String getState() {
        return this.state;
    }

    /**
     * TODO keep or remove?
     * @return of type string
     */
    protected String getInsideSal() {
        return this.insideSal;
    }

    /**
     * Returns the address of the diocese that the Bishop resides in
     * @return of type string
     */
    protected String getAddress1() {
        return this.address1;
    }

    /**
     * Returns the address of the diocese that the Bishop resides in
     * @return of type string
     */
    protected String getAddress2() {
        return this.address2;
    }

    /**
     * Returns the zip-code of the diocese that the Bishop resides in
     * @return of type String
     */
    protected String getZip() {
        return this.zip;
    }

    /**
     * Returns the name of the diocese that the Bishop resides in.
     * See constructior of format details
     * @return of type String
     */
    protected String getDioceseName(){
        return this.dioceseName;
    }

    /**
     * TODO Get this working
     * @return of type String
     */
    protected String getSal(){
        return this.sal;
    }

    /**
     * Returns the Bishop's title
     * @return of type String
     */
    protected String getTitle(){
        return this.title;
    }

    /**
     * Returns the short name of the diocese that the Bishop resides in
     * @return of type String
     */
    protected String getDioShortName(){
        return this.dioShortName;
    }

    /**
     * Returns the city of the diocese that the Bishop resides in
     * @return of type string
     */
    protected String getCity() {
        return this.city;
    }

    /**
     * Returns the Bishop's last name
     * @return of type String
     */
    protected String getLast() {
        return this.last;
    }

    /**
     * Returns the Bishop's first name
     * @return of type String
     */
    protected String getFirst() {
        return this.first;
    }

    /**
     * Returns the Bishop's middle name
     * @return of type String
     */
    protected String getMiddle() {
        return this.middle;
    }

    /**
     * Returns the Bishop's suffix
     * @return of type String
     */
    protected String getSuffix(){
        return this.suffix;
    }

    /**
     * Returns the Lotus Code that is associated with a Diocese
     * @return of type String
     */
    protected String getLotusCode(){
        return this.lotusCode;
    }


//  Comparators Below:

    public static Comparator<Bishop> COMPARE_BY_BISH_LAST = Comparator.comparing(bishop -> bishop.last.toLowerCase());

    public static Comparator<Bishop> COMPARE_BY_BISH_FIRST = Comparator.comparing(bishop -> bishop.first.toLowerCase());

    public static Comparator<Bishop> COMPARE_BY_BISH_TITLE = Comparator.comparing(bishop -> bishop.title.toLowerCase());

    public static Comparator<Bishop> COMPARE_BY_DIO_SHORT = Comparator.comparing(bishop -> bishop.dioShortName.toLowerCase());

    public static Comparator<Bishop> COMPARE_BY_DIO_FULL = Comparator.comparing(bishop -> bishop.dioceseName.toLowerCase());

    public static Comparator<Bishop> COMPARE_BY_DIO_CITY = Comparator.comparing(bishop -> bishop.city.toLowerCase());

    public static Comparator<Bishop> COMPARE_BY_DIO_STATE = Comparator.comparing(bishop -> bishop.state.toLowerCase());




}