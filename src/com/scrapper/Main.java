package com.scrapper;

public class Main {

    private static void createAndShowGUI() throws Exception {
        new View();
    }

    public static void main (String[] args) {

      /*
        DiocesesList dioList = new DiocesesList();
        Dioceses d1 = new Dioceses(null,null,null,null,null,null,null,"Florida",null,null,null,null,null);
        Dioceses d2 = new Dioceses(null,null,null,null,null,null,null,"New Jersey",null,null,null,null,null);
        Dioceses d3 = new Dioceses(null,null,null,null,null,null,null,"Apple",null,null,null,null,null);
        dioList.add(d1);
        dioList.add(d3);
        System.out.println(dioList.toString());
        dioList.add(d2);
        System.out.println(dioList.toString());
      */

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}