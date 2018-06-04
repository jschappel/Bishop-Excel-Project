package com.scrapper;

import javafx.concurrent.Task;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class ExcelWrite extends Task {

    private static BishopList bishopList = new BishopList();
    private static XSSFSheet sheet;
    private static XSSFWorkbook workbook;
    private Boolean finished = false;


    private static final String[] TITLE_LIST =
            {"new bishop", "Ordinary", "Current RI Diocese", "Target", "Primary Contact", "Special Note", "Diocese",
                    "sal", "First", "Middle", "Last", "Suffix", "Title", "Inside Sal", "Diocese Name",
                    "Address 1", "Address 2", "City", "State", "Zip", "USCCB Notes"};


    /**
     * Gets the current date
     * @return A string in the format dd/mm/yyyy
     */
    private static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(new Date());
    }

    /**
     * Adds the column headers to the sheet. They will be placed at the first row of the sheet
     */
    private static void addHeaders() {
        //Set the font for the headers
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeight(14);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);  // Create a new row in the sheet

        // Add the headers
        int cellid = 0;
        for (String title : TITLE_LIST) {
            Cell cell = headerRow.createCell(cellid++); // Create a new Cell in the current row
            cell.setCellValue(title); // Set the value to new value
            cell.setCellStyle(headerStyle);
            //System.out.println(cell.getStringCellValue());


        }
    }

    /**
     * Adds the bishopList elements to th sheet
     */
    private static void addData() {
        Row dataRow;
        int i = 1;
        Sheet getSheet = workbook.getSheetAt(0);
        Row row = getSheet.getRow(0);


        for (Bishop bishop : bishopList) {
            dataRow = sheet.createRow(i++);
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String value = cell.getStringCellValue();
                switch (value) {
                    case "Last":
                        Cell cell1 = dataRow.createCell(cell.getColumnIndex());
                        cell1.setCellValue(bishop.getLast());
                        break;

                    case "Middle":
                        Cell cell2 = dataRow.createCell(cell.getColumnIndex());
                        cell2.setCellValue(bishop.getMiddle());
                        break;

                    case "First":
                        Cell cell3 = dataRow.createCell(cell.getColumnIndex());
                        cell3.setCellValue(bishop.getFirst());
                        break;

                    case "Diocese":
                        Cell cell4 = dataRow.createCell(cell.getColumnIndex());
                        cell4.setCellValue(bishop.getDioShortName());
                        break;

                    case "Suffix":
                        Cell cell5 = dataRow.createCell(cell.getColumnIndex());
                        cell5.setCellValue(bishop.getSuffix());
                        break;

                    case "Diocese Name":
                        Cell cell6 = dataRow.createCell(cell.getColumnIndex());
                        cell6.setCellValue(bishop.getDioceseName());
                        break;

                    case "Sal":
                        Cell cell7 = dataRow.createCell(cell.getColumnIndex());
                        cell7.setCellValue(bishop.getSal());
                        break;

                    case "City":
                        Cell cell8 = dataRow.createCell(cell.getColumnIndex());
                        cell8.setCellValue(bishop.getCity());
                        break;

                    case "Title":
                        Cell cell9 = dataRow.createCell(cell.getColumnIndex());
                        cell9.setCellValue(bishop.getTitle());
                        break;

                    case "State":
                        Cell cell10 = dataRow.createCell(cell.getColumnIndex());
                        cell10.setCellValue(bishop.getState());
                        break;

                    case "Zip":
                        Cell cell11 = dataRow.createCell(cell.getColumnIndex());
                        cell11.setCellValue(bishop.getZip());
                        break;

                    case "Inside Sal":
                        Cell cell12 = dataRow.createCell(cell.getColumnIndex());
                        cell12.setCellValue(bishop.getInsideSal());
                        break;

                    case "Address 1":
                        Cell cell13 = dataRow.createCell(cell.getColumnIndex());
                        cell13.setCellValue(bishop.getAddress1());
                        break;

                    case "Address 2":
                        Cell cell14 = dataRow.createCell(cell.getColumnIndex());
                        cell14.setCellValue(bishop.getAddress2());
                        break;
                }

            }
        }
        // AutoSize all of the columns now that all the data is in
        // documentation recommends autosizing after all data is in because
        // it is a slow process.
        for (int j = 0; j < TITLE_LIST.length; j++)
            sheet.autoSizeColumn(j);

    }

    /**
     * Builds the excel sheet from scratch
     * @return returns done when the program has finished
     * @throws Exception If the bishopList can not be created
     */
    @Override
    protected Object call() throws Exception {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(getDate());
        //Run the sort function to get the bishopList
        try {
            bishopList = Sort.findAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addHeaders();
        addData();
        try {
            FileOutputStream out = new FileOutputStream(new File("fileName" + ".xlsx"));
            workbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.updateProgress(100,100);
        return "done";
    }

    public boolean getStatus(){
        return finished;
    }
}