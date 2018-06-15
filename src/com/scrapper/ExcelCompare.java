package com.scrapper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class ExcelCompare {

    private final File FILE;
    private final XSSFWorkbook WORKBOOK;
    private final XSSFSheet ORIGINAL_SHEET;
    private int changes;

    /**
     * Constructor for creating a Compare Object when just given a file. Will
     * take the last sheet in the file
     * @param file A file that you would like to compare to the updated data
     * @throws IOException If the file can not be found
     */
    protected  ExcelCompare(File file) throws IOException {
        this.FILE = file;
        this.changes = 0;
        WORKBOOK = new XSSFWorkbook(new FileInputStream(FILE));
        this.ORIGINAL_SHEET = getLastSheet();
    }

    /**
     * Constructor for creating a Compare Object when given a file and a sheet.
     * Use this constructor when you wish to compare a specific sheet
     * @param file A file that you would like to compare to the updated data
     * @param sheetName A string that is the name of the sheet
     * @throws IOException If the FileInputStream could not be created
     */
    protected ExcelCompare(File file, String sheetName) throws IOException {
        this.FILE = file;
        this.changes = 0;
        WORKBOOK = new XSSFWorkbook(new FileInputStream(FILE));
        this.ORIGINAL_SHEET = WORKBOOK.getSheet(sheetName);
    }

    /**
     * Returns the last sheet in a Workbook
     * @return an XSSFSheet, null if empty
     */
    private XSSFSheet getLastSheet(){
        int numberOfSheets = WORKBOOK.getNumberOfSheets();
        if(numberOfSheets == 0)
            return null;
        else
            return WORKBOOK.getSheetAt(numberOfSheets -1);

    }

    /**
     * Copies the ORIGINAL_SHEET and adds it to a new sheet. I the sheet already exists
     * it uses the same name but adds (1) after. This will go in for infinite sheets
     */
    protected XSSFSheet cloneSheet(){
        String newSheetName = getDate();
        int index = 1;
        // Make sure that the sheet name does not already exist
        while(WORKBOOK.getSheet(newSheetName) != null){
            newSheetName = getDate() + "(" + index + ")";
            index++;
        }
        return WORKBOOK.cloneSheet(WORKBOOK.getSheetIndex(ORIGINAL_SHEET),newSheetName);
    }

    /**
     * Gets the current Date in string form
     * @return A string in the format dd/mm/yyyy
     */
    private static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(new Date());
    }

    /**
     * Compares a sheet to a given sheet and then edits the sheet with the changes if there are any
     * @param aSheet a XSSFSheet
     * @param bList a BishopList
     */
    protected void compareAndWrite (XSSFSheet aSheet, BishopList bList) {

        // Find the row headers
        Row dataRow;
        int i = 1;
        Row headerRow = aSheet.getRow(0);

        for(Bishop bishop : bList) {
            dataRow  = aSheet.getRow(i++);
            Iterator<Cell> cellIterator = headerRow.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String value = cell.getStringCellValue();
                switch (value) {

                    case "Last":
                        Cell dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getLast(), dataCell);
                        break;

                    case "First":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getFirst(), dataCell);
                        break;

                    case "Middle":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getMiddle(), dataCell);
                        break;

                    case "Diocese":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getDioShortName(), dataCell);
                        break;

                    case "Suffix":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(),bishop.getSuffix(),dataCell);
                        break;

                    case "Diocese Name":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getDioceseName(), dataCell);
                        break;

                    case "Sal":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getSal(), dataCell);
                        break;

                    case "City":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getCity(), dataCell);
                        break;

                    case "Title":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getTitle(), dataCell);
                        break;

                    case "State":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getState(), dataCell);
                        break;

                    case "Zip":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                        if(dataCell.getCellTypeEnum() == CellType.NUMERIC ){
                            String data = String.valueOf((int) dataCell.getNumericCellValue());
                            compare(data, bishop.getZip(), dataCell);
                        }
                        else {
                            compare(dataCell.getStringCellValue(), bishop.getZip(), dataCell);
                        }
                        break;

                    case "Inside Sal":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getInsideSal(), dataCell);
                        break;

                    case "Address 1":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getAddress1(), dataCell);
                        break;

                    case "Address 2":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getAddress2(), dataCell);
                        break;
                }
            }
        }
    }

    /**
     * Compares a sheet to a given sheet and then edits the sheet with the changes if there are any
     * @param aSheet a XSSFSheet
     * @param bList a BishopList
     */
    protected void compareAndWrite_salesforce (XSSFSheet aSheet, BishopList bList) {

        // Find the row headers
        Row dataRow;
        int i = 1;
        Row headerRow = aSheet.getRow(0);

        for(Bishop bishop : bList) {
            dataRow  = aSheet.getRow(i++);
            Iterator<Cell> cellIterator = headerRow.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String value = cell.getStringCellValue();
                switch (value) {

                    case "Last Name":
                        Cell dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getLast(), dataCell);
                        break;

                    case "First Name":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getFirst(), dataCell);
                        break;

                    case "Account":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getDioceseName(), dataCell);
                        break;

                    case "Billing City":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getCity(), dataCell);
                        break;

                    case "Title":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getTitle(), dataCell);
                        break;

                    case "Billing State/Province":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getState(), dataCell);
                        break;

                    case "Billing Zip/Postal Code":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                        if(dataCell.getCellTypeEnum() == CellType.NUMERIC ){
                            String data = String.valueOf((int) dataCell.getNumericCellValue());
                            compare(data, bishop.getZip(), dataCell);
                        }
                        else {
                            compare(dataCell.getStringCellValue(), bishop.getZip(), dataCell);
                        }
                        break;

                    case "Billing Address Line 1":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getAddress1(), dataCell);
                        break;

                    case "Billing Address Line 2":
                        dataCell = dataRow.getCell(cell.getColumnIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        compare(dataCell.getStringCellValue(), bishop.getAddress2(), dataCell);
                        break;
                }
            }
        }
    }

    /**
     * Compares two values. If the values are different then the cell is highlighted and the
     * new value is colored red. The format is as follows: "oldValue | newValue"
     * @param currentValue The value that is currently in the cell
     * @param newValue The value that is in the BishopList
     * @param cell The cell that you would like to make the changes to
     */
    private void compare(String currentValue, String newValue, Cell cell) {

        if(currentValue == null)
            currentValue = "";
        if(newValue == null)
            newValue = "";

        if(!currentValue.equals(newValue)){

            //Create the cell style
            XSSFFont font = WORKBOOK.createFont();
            font.setColor(IndexedColors.RED.getIndex());

            XSSFCellStyle highlight1 = WORKBOOK.createCellStyle();
            highlight1.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            highlight1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Add the new Value to the cell and highlight the cell
            XSSFRichTextString text = new XSSFRichTextString(currentValue + " | " + newValue);
            text.applyFont(0,currentValue.length(),font);
            cell.setCellValue(text);
            cell.setCellStyle(highlight1);
            changes++;

        }
    }

    /**
     * Writes and closes the file
     * @throws IOException if the file does not exist or if the workbook could not write
     */
    protected void fileClose() throws IOException {
        FileOutputStream out = new FileOutputStream(FILE);
        WORKBOOK.write(out);
        out.close();
    }

    /**
     * Returns the number of changes that occurred during the compareAndWrite function
     * @return the number of changes
     */
    protected int getChanges(){
        return changes;
    }

    /**
     * Returns the names of the sheets in a file
     * @param file a Excel file with xlsx ext
     * @return A ArrayList of type String
     * @throws IOException if InputStream can not be created with the file
     */
    protected static ArrayList<String> getSheets(File file) throws IOException {
        ArrayList<String> sheetList = new ArrayList<>();
        sheetList.add("Default");
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        int numberOfSheets = workbook.getNumberOfSheets();

        for(int i = 0; i < numberOfSheets; i++){
            sheetList.add(workbook.getSheetAt(i).getSheetName());
        }
        return sheetList;
    }
}