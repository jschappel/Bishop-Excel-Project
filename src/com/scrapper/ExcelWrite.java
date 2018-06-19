package com.scrapper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelWrite {

    private final XSSFWorkbook WORKBOOK;
    private final File FILE;
    private XSSFSheet sheet;

    private static final String[] TITLE_LIST = {
            "new bishop", "Ordinary", "Current RI Diocese", "Target", "Primary Contact", "Special Note", "Diocese",
            "sal", "First", "Middle", "Last", "Suffix", "Title", "Inside Sal", "Diocese Name",
            "Address 1", "Address 2", "City", "State", "Zip", "USCCB Notes", "Lotus Codes"};



    /**
     * Constructor for Excel write:
     *  initializes WORKBOOK, FILE, and sheet
     * @param file a non null file
     */
    public ExcelWrite(File file) {
        this.FILE = file;
        WORKBOOK = new XSSFWorkbook();
        sheet = WORKBOOK.createSheet(getDate());
    }


    /**
     * Gets the current date
     * @return A string in the format dd/mm/yyyy
     */
    private static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(new Date());
    }

    /**
     * Addes the headers to the excel file
     */
    public void addHeaders() {
        //Set the font for the headers
        XSSFCellStyle headerStyle = WORKBOOK.createCellStyle();
        XSSFFont headerFont = WORKBOOK.createFont();
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
        }
    }

    /**
     * Adds bishop data to the excel file
     * @param bishopList a ist of type Bishop
     */
    public void addData(List<Bishop> bishopList) {
        Row dataRow;
        int i = 1;
        Sheet getSheet = WORKBOOK.getSheetAt(0);
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

                    case "Lotus Codes":
                        Cell cell15 = dataRow.createCell(cell.getColumnIndex());
                        cell15.setCellValue(bishop.getLotusCode());
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
     * Closes the excel file safely so that the file is not corrupted
     */
    public void closeFile(){
        try (FileOutputStream fos = new FileOutputStream(FILE.getAbsolutePath())) {
            WORKBOOK.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}