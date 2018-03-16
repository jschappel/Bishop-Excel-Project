package com.scrapper;

public class Main {

    private static void createAndShowGUI() throws Exception {
        new View();
    }

    public static void main (String[] args) {

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