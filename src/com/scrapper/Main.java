package com.scrapper;

import java.io.IOException;

public class Main {

    public static void main (String[] args) throws IOException {
        String webpage = "http://www.usccb.org/about/bishops-and-dioceses/all-dioceses.cfm";
        Sort sort = new Sort(webpage);
        sort.findAttributes();
        sort.returnDioceseObjectList();

        // Create a spreadsheet

    }
}
