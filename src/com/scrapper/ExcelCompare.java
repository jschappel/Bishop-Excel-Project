package com.scrapper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        this.ORIGINAL_SHEET = getLastSheet();
        this.changes = 0;
        WORKBOOK = new XSSFWorkbook(new FileInputStream(file));

    }

    /**
     * Constructor for creating a Compare Object when given a file and a sheet
     * @param file A file that you would like to compare to the updated data
     * @param sheet A sheet in the that you would like to compare
     * @throws IOException If the file can not be found
     */
    protected ExcelCompare(File file, XSSFSheet sheet) throws IOException {
        this.FILE = file;
        this.ORIGINAL_SHEET = sheet;
        this.changes = 0;
        WORKBOOK = new XSSFWorkbook(new FileInputStream(file));
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
     * Copies the ORIGINAL_SHEET and adds it to a news sheet
     */
    protected XSSFSheet cloneSheet(){
        String newSheetName = getDate();
        int index = 1;
        // Make sure that the sheet name does not already exist
        while(WORKBOOK.getSheet(newSheetName) != null){
            newSheetName = getDate() + "(" + index + ")";

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
     * @throws IOException if the BishopList could not be created
     */
    protected void compareAndWrite (XSSFSheet aSheet, BishopList bList) throws IOException, NoSuchMethodException {

        // Find the row headers
        Row dataRow;
        int i = 1;
        int j = 0;
        Row headerRow = aSheet.getRow(0);

        for(Bishop bishop : bList) {
            dataRow  = aSheet.getRow(i++);
            Iterator<Cell> cellIterator = headerRow.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String value = cell.getStringCellValue();
                switch (value) {

                    case "Last":
                        Cell dataCell = dataRow.getCell(cell.getColumnIndex());
                        compare(dataCell.getStringCellValue(), bishop.getLast(), dataCell);
                        break;

                    case "First":
                        dataCell = dataRow.getCell(cell.getColumnIndex());
                        compare(dataCell.getStringCellValue(), bishop.getFirst(), dataCell);
                        break;

                    /*case "Middle":
                        dataCell = dataRow.getCell(cell.getColumnIndex());
                        System.out.println(dataCell.getStringCellValue());

                        compare(dataCell.getStringCellValue(), bishop.getMiddle(), dataCell);
                        break;
                    */
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
}
