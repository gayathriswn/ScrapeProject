package org.example;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ReadEliminateList {

	public static void main(String[] args) throws IOException {
		String filename = "C:\\Users\\samap\\Downloads\\IngredientsAndComorbidities-ScrapperHackathon.xlsx";


		ArrayList<String> ingredients = readColumnFromExcel(filename, 0); // read column 1
		System.out.println(ingredients);
	}

	public static ArrayList<String> readColumnFromExcel(String filename, int columnNum) throws IOException {
		ArrayList<String> columnData = new ArrayList<>();
		FileInputStream inputStream = new FileInputStream(new File(filename));
		Workbook workbook = WorkbookFactory.create(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		int rowIndex = 0;
		for (Row row : sheet) {
			if (rowIndex >= 2)
			{
				Cell cell = row.getCell(columnNum);
				if (cell != null && cell.getCellType() == CellType.STRING) {
					columnData.add(cell.getStringCellValue());
				}
			}
			rowIndex++;
		}

		workbook.close();
		inputStream.close();

		return columnData;
	}
}





/*	public static ArrayList<String> readIngredientsFromExcel(String filename) throws IOException {
		ArrayList<String> ingredients = new ArrayList<>();
		FileInputStream inputStream = new FileInputStream(new File(filename));
		Workbook workbook = WorkbookFactory.create(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		for (Row row : sheet) {
			for (Cell cell : row) {
				ingredients.add(cell.getStringCellValue());
			}
		}

		workbook.close();
		inputStream.close();

		return ingredients;
	}
} */

