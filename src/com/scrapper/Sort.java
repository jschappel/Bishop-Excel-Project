package com.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by joshuaschappel on 12/8/17.
 */

public class Sort {

    private ArrayList<String> dioceseArray = new ArrayList<String>();
    private ArrayList<String> firstNameList = new ArrayList<>();
    private ArrayList<String> middleNameList = new ArrayList<>();
    private ArrayList<String> lastNameList = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<String> stateList = new ArrayList<>();
    private ArrayList<String> suffixList = new ArrayList<>();
    private ArrayList<Dioceses> dioList= new ArrayList<>();

    private String state;
    private String zipCode;
    private String city;
    private String address1;
    private String address2;
    //TODO
    private String website;
    private int i = 0;


    private Document document;

    protected Sort(String webpage) throws IOException {
        document = Jsoup.connect(webpage).get();
    }

    private ArrayList<String> dioceseList(Elements elements, ArrayList<String> list) {
        Elements dioceseElement = elements.select("td[colspan=3][style= width:160px!important; max-width:160px!important; min-width:160px!important;font:9pt arial; padding-bottom:0px;  letter-spacing:-0.3pt; color:#000;font:bold 11pt arial!important; padding-top:6px!important; padding-left:30px!important;margin-bottom:0px!important;]");
        for (Element ele : dioceseElement) {
            list.add(ele.text().trim());
        }
        return list;
    }

    private void addressSort(Element element) {
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
            address1 = newString.substring(0, firstCityIndex);
            address2 = newString.substring(firstCityIndex + 1, lastCityIndex);
        }
        Elements site = element.select("a.subtle");
        website = site.text().trim();
    }

    private void bishopNames(Element elemant){

        String html = elemant.html();
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
                        firstNameList.add(nameString[0].trim());
                        middleNameList.add(nameString[1].trim());
                        lastNameList.add(nameString[2].trim());
                        suffixList.add(nameString[3].trim());

                    } else if (nameString.length == 3) {
                        firstNameList.add(nameString[0].trim());
                        middleNameList.add(nameString[1].trim());
                        lastNameList.add(nameString[2].trim());
                        suffixList.add(null);
                    } else if (nameString.length == 2) {
                        firstNameList.add(nameString[0].trim());
                        middleNameList.add(null);
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


    protected void findAttributes() throws IOException {
        Elements body = document.select("div#CS_CCF_2203_2211");

        // get the Diocese names
        dioceseList(body,dioceseArray);
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

                //create bishop objects and put them in a list.
                Dioceses dioceses = new Dioceses("TODO",firstNameList,middleNameList,lastNameList,suffixList,titleList,"TODO",dioceseArray.get(index),address1,address2,city,state,zipCode);
                dioList.add(dioceses);
                System.out.println(dioceses.returnFirst());
                i = i + dioceses.size1();
                firstNameList.clear();
                lastNameList.clear();
                middleNameList.clear();
                suffixList.clear();
                index++;
            }
        }
        System.out.println(i);
    }

    protected ArrayList<Dioceses> returnDioceseObjectList() {
        return dioList;
    }


    private int countOccurrences(
            String someString, char searchedChar, int index) {
        if (index >= someString.length()) {
            return 0;
        }

        int count = someString.charAt(index) == searchedChar ? 1 : 0;
        return count + countOccurrences(
                someString, searchedChar, index + 1);
    }
}
