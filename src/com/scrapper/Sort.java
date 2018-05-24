package com.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by joshuaschappel on 12/8/17.
 */

public class Sort {

    private static ArrayList<String> dioceseArray = new ArrayList<String>();
    private static ArrayList<String> firstNameList = new ArrayList<>();
    private static ArrayList<String> middleNameList = new ArrayList<>();
    private static ArrayList<String> lastNameList = new ArrayList<>();
    private static ArrayList<String> titleList = new ArrayList<>();
    private static ArrayList<String> stateList = new ArrayList<>();
    private static ArrayList<String> suffixList = new ArrayList<>();
    private static ArrayList<String> dioShortNameList = new ArrayList<>();

    private static BishopList bishopList = new BishopList();

    private static String state;
    private static String zipCode;
    private static String city;
    private static String address1;
    private static String address2;
    //TODO
    private static String website = "http://www.usccb.org/about/bishops-and-dioceses/all-dioceses.cfm";
    private int i = 0;
    private static ArrayList suffixs = new ArrayList();
    private static Document document;


    /**
     * Adds all of the diocese names to an array list
     * @param elements Location of where there the Bishops are in the webpage
     * @param list An empty arraylist to add all of the Diocese names to
     * @param shortList An empty list to add the shortened Diocese names to
     * @return a list will all of the bishop names
     */
    private static ArrayList<String> dioceseList(Elements elements, ArrayList<String> list, ArrayList<String> shortList) {
        Elements dioceseElement = elements.select("td[colspan=3][style= width:160px!important; max-width:160px!important; min-width:160px!important;font:9pt arial; padding-bottom:0px;  letter-spacing:-0.3pt; color:#000;font:bold 11pt arial!important; padding-top:6px!important; padding-left:30px!important;margin-bottom:0px!important;]");
        for (Element ele : dioceseElement) {
            String s = ele.text();

            if (ele.text().trim().contains("Diocese of")){
                s = s.replace("Diocese of", "");
                shortList.add(s.trim());
            }

            else if (ele.text().trim().contains("Archdiocese of")){
                s = s.replace("Archdiocese of ", "");
                shortList.add(s.trim());
            }
            else {
                shortList.add(ele.text().trim());
            }
            list.add(ele.text().trim());

        }

        return list;
    }

    /**
     * Sorts though the webpage and adds all of the address elements to their respected arrays.
     * @param element The location of where the address data values are located
     */
    private static void addressSort(Element element) {
        String html = element.html();
        html = html.replaceAll("<br>", " |");
        int indexOfUrl = html.indexOf("&nbsp");
        int indexOfZipHolder = html.indexOf("&nbsp;");

        // Get the zipcode
        if (indexOfZipHolder >= 0)
            zipCode = html.substring(indexOfZipHolder + 6, html.indexOf("<a") - 3);
        else {
            zipCode = null;
        }
     //   System.out.println(zipCode);

        String newString = html;
        if (indexOfUrl >= 0)
            newString = html.substring(0, indexOfUrl);

        // Extract data from string
        int occurrences = countOccurrences(newString, '|', 0);
        if (occurrences == 1) {
            int cityIndex = newString.lastIndexOf("|");
            int stateComma = newString.lastIndexOf(",");
            address1 = newString.substring(0, cityIndex);
            address2 = null;
            city = newString.substring(cityIndex + 1, stateComma);
        } else {
            int lastCityIndex = newString.lastIndexOf("|");
            int stateComma = newString.lastIndexOf(",");
            int firstCityIndex = newString.indexOf("|");
            city = newString.substring(lastCityIndex + 1, stateComma);

            // Some of the addresses have the building name first
            // If this is the case then we will make that address2
            if(!isNumberic(newString.substring(0, 1))){
                address2 = newString.substring(0, firstCityIndex);
                address1 = newString.substring(firstCityIndex + 1, lastCityIndex);
            } else {
                address1 = newString.substring(0, firstCityIndex);
                address2 = newString.substring(firstCityIndex + 1, lastCityIndex);
            }
        }
        Elements site = element.select("a.subtle");
        website = site.text().trim();
    }

    /**
     * Sorts the name string into first, last, middle ect...
     * @param element JSoupElement that contains the name String
     */
    private static void bishopNames(Element element){
        suffixs.addAll(Arrays.asList("OMI","CM","C.Ss.R","SJ","OSBM","SF","CSC","SSJ","SM","OFM Conv","CPPS","MSpS","CMF","OSB","SVD","OMF Cap","SDB","BSD","SDV","MLM","CSsR","ORA","CSB","C.P.","C.O."));
        ArrayList<String> list1 = new ArrayList<>();
        list1.addAll(Arrays.asList("II","III","IV","VI"));

        String html = element.html();
        html = html.replaceAll("<strong>", "");
        html = html.replaceAll("</strong>", "&");
        html = html.replaceAll("<br><br>", "&");
        html = html.replaceAll("<br>", "");


        String splitString[] = html.split("&");
        int count = 0;

        for (int i = 0; i < splitString.length; i++) {
            if (count % 2 == 0) { //even
                if (splitString[i].contains("Bishop") || splitString[i].contains("Archbishop") || splitString[i].contains("Cardinal")) {
                    splitString[i] = splitString[i].replace("Bishop", "");
                    splitString[i] = splitString[i].replace("Archbishop", "");
                    splitString[i] = splitString[i].replace("Cardinal", "");

                    //Split the name
                    splitString[i] = splitString[i].trim();
                    String[] nameString = splitString[i].split(" ");

                    if (nameString.length == 4) {

                        if(suffixs.contains(nameString[nameString.length -1])) {
                            firstNameList.add(nameString[0].trim());
                            middleNameList.add(nameString[1].trim());
                            lastNameList.add(nameString[2].trim());
                            suffixList.add(nameString[3].trim());
                        } else if (list1.contains(nameString[2])){
                            firstNameList.add(nameString[0].trim());
                            middleNameList.add("");
                            lastNameList.add(nameString[1].trim());
                            suffixList.add(nameString[2].trim());
                        } else if (list1.contains(nameString[3])) {
                            firstNameList.add(nameString[0].trim());
                            middleNameList.add(nameString[1]);
                            lastNameList.add(nameString[2].trim());
                            suffixList.add(nameString[3].trim());
                        }
                        else {
                            firstNameList.add(nameString[0].trim());
                            middleNameList.add(nameString[1].trim());
                            lastNameList.add(nameString[2].trim() + " " + nameString[3].trim());
                            suffixList.add("");
                        }

                    } else if (nameString.length == 3) {
                        if(suffixs.contains(nameString[nameString.length -1])) {
                            firstNameList.add(nameString[0].trim());
                            middleNameList.add("");
                            lastNameList.add(nameString[1].trim());
                            suffixList.add(nameString[2].trim());

                        } else if (list1.contains(nameString[2])){
                            firstNameList.add(nameString[0].trim());
                            middleNameList.add("");
                            lastNameList.add(nameString[1].trim());
                            suffixList.add(nameString[2].trim());
                        }
                        else {
                            firstNameList.add(nameString[0].trim());
                            middleNameList.add(nameString[1].trim());
                            lastNameList.add(nameString[2].trim());
                            suffixList.add(null);
                        }
                    } else if (nameString.length == 2) {
                        firstNameList.add(nameString[0].trim());
                        middleNameList.add("");
                        lastNameList.add(nameString[1].trim());
                        suffixList.add(null);
                    }
                }
            } else {
                    if (splitString[i].contains("of")) {
                        int index = splitString[i].indexOf("of");
                        splitString[i] = splitString[i].substring(0, index);
                    }

                    if (splitString[i].contains("for")) {
                        int index2 = splitString[i].indexOf("for");
                        splitString[i] = splitString[i].substring(0, index2);
                    }

                    titleList.add(splitString[i]);
            }
            count++;
        }
    }


    /**
     * Scrapes through the website and returns a bishopList
     * @return A BishopList
     * @throws IOException throws Exception if website is not found
     */
    protected static BishopList findAttributes() throws IOException {
        document = Jsoup.connect("http://www.usccb.org/about/bishops-and-dioceses/all-dioceses.cfm").get();
        Elements body = document.select("div#CS_CCF_2203_2211");

        // get the Diocese names
        dioceseList(body,dioceseArray,dioShortNameList);
        int index = 0;



        for (Element element : body.select("table.state")) {

            // Get the state
            Elements state1 = element.select("tr.newstate");
            Elements stateName = state1.select("span.statename");
            stateList.add(stateName.text().trim());
            state = stateName.text();

            Elements testele = element.select("tr[valign=top]");
            for (Element ele : testele) {
                //System.out.println("\n");

                // Address1, address2, city, zip, website
                Element ele2 = ele.select("td[style=padding-left:30px!important; margin-top:0px!important;font-size:11px!important;color:#555!important;]").first();
                addressSort(ele2);

                // Get the Bishops Name
                Element e = ele.select("td.personnel").first();
                bishopNames(e);

                for(int index2 = 0; index2 < firstNameList.size(); index2++) {
                    Bishop bishop = new Bishop("TODO",firstNameList.get(index2),middleNameList.get(index2),lastNameList.get(index2),suffixList.get(index2),titleList.get(index2),"TODO",dioShortNameList.get(index), dioceseArray.get(index),address1,address2,city,state,zipCode);
                    bishopList.add(bishop);
                    //System.out.println(bishop.getBishopLastName());
                }

                firstNameList.clear();
                lastNameList.clear();
                middleNameList.clear();
                suffixList.clear();
                titleList.clear();
                index++;
            }

        }
        return bishopList;
    }

    /**
     * Determines if the given string is a number
     * @param aString A string that you would like to check
     * @return True if an it, False otherwise
     */
    public static boolean isNumberic(String aString){
        try{
            Integer.parseInt(aString);

        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }



    private static int countOccurrences(
            String someString, char searchedChar, int index) {
        if (index >= someString.length()) {
            return 0;
        }

        int count = someString.charAt(index) == searchedChar ? 1 : 0;
        return count + countOccurrences(
                someString, searchedChar, index + 1);
    }
}
