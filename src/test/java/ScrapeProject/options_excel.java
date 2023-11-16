package ScrapeProject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//spreadsheet styling 
public class options_excel {
	
	static String path  = System.getProperty("user.dir") + "/src/test/resources/TestData/";	

	public static void createExcel() throws IOException
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet worksheet = workbook.createSheet("color options");
		XSSFSheet worksheet1 = workbook.createSheet("fill_pattern");
		
		for(int i=0;i<=31;i++)
    	{
			XSSFCellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short) 15);
			font.setBold(true);
			style.setFont(font);
			style.setFillPattern(FillPatternType.FINE_DOTS);

			System.out.println("Color Index["+i+"]: "+IndexedColors.fromInt(i));
			String color_name = IndexedColors.fromInt(i).name();
			//System.out.println(color_name);
			style.setFillBackgroundColor(IndexedColors.fromInt(i).getIndex());
			int rowCount = worksheet.getLastRowNum();
            Row row = worksheet.createRow(++rowCount);
			
			 row.createCell(0).setCellValue(color_name);
			 for(int k=1;k<=20;k++)
			 {
				 row.createCell(k).setCellValue(" ");
			 }
			 for(int j = 1; j<=20; j++)
			 {
				 row.getCell(j).setCellStyle(style);
			 }
    	}
		
		for(int i=0;i<=18;i++)
    	{
			XSSFCellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short) 15);
			font.setBold(true);
			style.setFont(font);
			
			style.setFillBackgroundColor(IndexedColors.PINK1.getIndex());
			System.out.println("Pattern Index["+i+"]: "+FillPatternType.forInt(i));
			String pattern_name = FillPatternType.forInt(i).name();
			System.out.println(pattern_name);
            style.setFillPattern(FillPatternType.forInt(i));			
			int rowCount = worksheet1.getLastRowNum();
			
            Row row = worksheet1.createRow(++rowCount);
            row = worksheet1.createRow(++rowCount);
			 row.createCell(0).setCellValue(pattern_name);
			 for(int k=1;k<=20;k++)
			 {
				 row.createCell(k).setCellValue(" ");
			 }
			
			 for(int j = 1; j<=20; j++)
			 {
				 row.getCell(j).setCellStyle(style);
			 }
    	}

		File excelFile = new File(path+"excel_styling.xlsx");
		FileOutputStream fos = new FileOutputStream(excelFile);
		workbook.write(fos);
		workbook.close();
		fos.close();
		System.out.println("Excel Created");

    }

	public static void main(String args[]) throws IOException
	{
		createExcel();
	}

}
