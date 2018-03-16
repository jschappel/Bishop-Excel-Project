package com.scrapper;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelWrite {

    public ExcelWrite() throws FileNotFoundException {

    }


    public void extract() throws Exception {

        File file = new File("TestBook.xlsx");
        HSSFWorkbook workbook;
        if(file.exists() == false){
            System.out.println("Creating new book");
            workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Sheet1");
        } else{
            System.out.println("adding to book");
            InputStream is = new FileInputStream(file);
            try{
                workbook = new HSSFWorkbook(is);
                HSSFSheet sheet = workbook.createSheet("Sheet2");
            } finally {
                is.close();
            }
        }
    }
}
