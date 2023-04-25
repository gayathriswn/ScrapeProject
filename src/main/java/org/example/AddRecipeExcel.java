package org.example;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AddRecipeExcel {

	public static void main(String[] args) throws Exception {

		Scraper sc = new Scraper();
		sc.parseDataFull(23);
		ArrayList<Recipe> recipeList = sc.getRecipes();

		try {

			//create workbook in .xlsx format
			Workbook workbook = new XSSFWorkbook();

			// create column headings
			String[] columnHeadings = {"ID", "Name", "URL", "Ingredients", "Method", "Cook Time", "Prep Time"};

			Sheet sheet = workbook.createSheet("Diabetic Recipes");

			CellStyle cs = workbook.createCellStyle();
			cs.setWrapText(true);
			cs.setVerticalAlignment(VerticalAlignment.CENTER);

			CellStyle header_style = workbook.createCellStyle();
			header_style.setAlignment(HorizontalAlignment.CENTER);
			header_style.setVerticalAlignment(VerticalAlignment.CENTER);

			Row header = sheet.createRow(0);

			for (Cell c : header)
			{
				c.setCellStyle(header_style);
			}


			for (int i = 0; i < columnHeadings.length; i++)
			{
				header.createCell(i).setCellValue(columnHeadings[i]);
			}

			int rowNum = 1;
			for (Recipe r : recipeList)
			{
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(r.getID());
				row.getCell(0).setCellStyle(header_style);

				row.createCell(1).setCellValue(r.getName());
				row.getCell(1).setCellStyle(cs);

				row.createCell(2).setCellValue(r.getURL());
				row.getCell(2).setCellStyle(cs);

				row.createCell(3).setCellValue(String.join(",", r.getIngredients()).replaceAll(",", "\n"));
				row.getCell(3).setCellStyle(cs);

				row.createCell(4).setCellValue(String.join(",", r.getPrepMethod()).replaceAll(",", "\n"));
				row.getCell(4).setCellStyle(cs);

				row.createCell(5).setCellValue(r.getCookTime());
				row.getCell(5).setCellStyle(header_style);

				row.createCell(6).setCellValue(r.getPrepTime());
				row.getCell(6).setCellStyle(header_style);
			}

			for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
				sheet.setColumnWidth(i, 40 * 256);
			}

			FileOutputStream fileOut = new FileOutputStream("ScrapingHackathon.xlsx");
			workbook.write(fileOut);
			fileOut.close();
			sc.closeDriver();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}





}
