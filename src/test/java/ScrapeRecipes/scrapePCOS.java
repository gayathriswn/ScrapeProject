package ScrapeRecipes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//this program implements hash map to save data and later write in excel. It runs as a java application
public class scrapePCOS {	
	public static WebDriver driver;
	int flag, toAddflag = 0; 
	static String path  = System.getProperty("user.dir") + 
			"/src/test/resources/TestData/";	
	ArrayList <String> eliminatedList = new ArrayList<String>();
	ArrayList <String> allergens = new ArrayList<String>();
	ArrayList <String> toAdd = new ArrayList<String>();
	String morbidCondn = "", rcp_category = "", food_category="", page_index="";	
	String[] recipe_category = {"breakfast","lunch","dinner","snack"};
	HashMap<Integer, ArrayList<String>> PCOSRecipeList = new HashMap<Integer, ArrayList<String>>();
	ArrayList<String> recipe_list = new ArrayList<String>();

	Instant timer_start, timer_end;
	int eliminated_recipe_count =  0, filtered_recipe_count = 0, total_recipes = 0, tohave_count = 0;;
    int recipe_url_size = 0;
	String recipe_id = "", recipe_name="", allergy_name = "", recipe_tags = "", ingredientsList = "";
    String prep_time = "", cook_time="", prep_method="",nutrient_values="",recipe_url="";
    
   
	public static void createExcel() throws IOException
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet worksheet = workbook.createSheet("PCOS_Recipes");
		XSSFCellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 15);
		font.setBold(true);
		style.setFont(font);
		style.setFillPattern(FillPatternType.FINE_DOTS);
		style.setFillBackgroundColor((IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex()));
		Row row = worksheet.createRow(0);
		
		row.createCell(0).setCellValue("Recipe Id");
		row.createCell(1).setCellValue("Recipe Name");
		row.createCell(2).setCellValue("Recipe Category");
		row.createCell(3).setCellValue("Food Category");
		row.createCell(4).setCellValue("Ingredients List");
		row.createCell(5).setCellValue("Preparation Time");
		row.createCell(6).setCellValue("Cooking Time");
		row.createCell(7).setCellValue("Preparation Method");
		row.createCell(8).setCellValue("Nutrient Values");
		row.createCell(9).setCellValue("Recipe URL");
		row.createCell(10).setCellValue("Allergy Information");
		for(int j = 0; j<=10; j++)
		{
		 row.getCell(j).setCellStyle(style);
			
		}
		XSSFSheet newsheet1 = workbook.createSheet("Allergy_info");
		Row row1 = newsheet1.createRow(0);
		
		row1.createCell(0).setCellValue("Morbid Condition");
		row1.createCell(1).setCellValue("Allergy Information");
		row1.createCell(2).setCellValue("Recipe Name");
		row1.createCell(3).setCellValue("Recipe URL");
		
		for(int j = 0; j<=3; j++)
		{
		 row1.getCell(j).setCellStyle(style);
			
		}
       	File excelFile = new File(path+"PCOSRecipes.xlsx");
		FileOutputStream fos = new FileOutputStream(excelFile);
		workbook.write(fos);
				
		workbook.close();
		fos.close();
	}
	public ArrayList<String> readExcel(String sheet_name) throws IOException, InterruptedException
	{
		ArrayList <String> tempArray = new ArrayList<String>();
		File excelFile = new File(path+"Team16_Filters.xlsx");
		FileInputStream fis = new FileInputStream(excelFile);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheet_name);
		Iterator<Row> row = sheet.rowIterator();
	   	int i = 0;
		while(row.hasNext()){
			Row currentRow = row.next();
			Iterator<Cell> cell = currentRow.cellIterator();
			while(cell.hasNext()){
				Cell currentCell = cell.next();
				tempArray.add(currentCell.getStringCellValue());
				//index_count++;
				i++;
			}
		}
		workbook.close();
		if(sheet_name.contains("eliminatedlist"))
		{
	        for(i=0;i<tempArray.size();i++)
	        {
	        	eliminatedList.add(tempArray.get(i)) ;
	        }
			return eliminatedList;			
		}
		
		if(sheet_name == "allergies")
		{
			for(i=0;i<tempArray.size();i++)
	        {
	        	allergens.add(tempArray.get(i));	        }
		
			return allergens;	
		}
		
		if(sheet_name.contains("toadd"))
		{
	        for(i=0;i<tempArray.size();i++)
	        {
	        	toAdd.add(tempArray.get(i));
	        }
			return toAdd;
		}
		return tempArray;
	}
	
	
	public void writeExcel() throws IOException
	{
		String strFilePath  = path+"PCOSRecipes.xlsx";
        File excelfile = new File(strFilePath);
        FileInputStream fis = new FileInputStream(excelfile);	
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet worksheet = workbook.getSheet(morbidCondn+"_Recipes");

        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.FINE_DOTS);
        style.setFillBackgroundColor((IndexedColors.LIGHT_YELLOW.getIndex()));  		

        for(int z=1;z<=filtered_recipe_count;z++)
        {
        	int rowCount = worksheet.getLastRowNum();
            Row row = worksheet.createRow(++rowCount);
            
            recipe_list = PCOSRecipeList.get(z);
            
            row.createCell(0).setCellValue(recipe_list.get(0));
            row.createCell(1).setCellValue(recipe_list.get(1));
            row.createCell(2).setCellValue(recipe_list.get(2));
            row.createCell(3).setCellValue(recipe_list.get(3));
            row.createCell(4).setCellValue(recipe_list.get(4));
            row.createCell(5).setCellValue(recipe_list.get(5));
            row.createCell(6).setCellValue(recipe_list.get(6));
            row.createCell(7).setCellValue(recipe_list.get(7));
            row.createCell(8).setCellValue(recipe_list.get(8));
            row.createCell(9).setCellValue(recipe_list.get(9));
            row.createCell(10).setCellValue(recipe_list.get(10));
            //highlighting good have recipes        	
            if((recipe_list.get(11)).contains("1"))
            {
                for(int h = 0; h<=10; h++)
                {
                	row.getCell(h).setCellStyle(style);
                }
            }
            //populating allergy info sheet
            if((recipe_list.get(10).length()) > 1 )
            {
            	 XSSFSheet worksheet1 = workbook.getSheet("Allergy_info");
                 int rowCount1 = worksheet1.getLastRowNum();

                 Row row1 = worksheet1.createRow(++rowCount1);

                 row1.createCell(0).setCellValue(morbidCondn);
                 row1.createCell(1).setCellValue(recipe_list.get(10));
                 row1.createCell(2).setCellValue(recipe_list.get(1));
                 row1.createCell(3).setCellValue(recipe_list.get(9));
             }

        }

        File excelFile = new File(strFilePath);
        FileOutputStream fos = new FileOutputStream(excelFile);
        workbook.write(fos);
        workbook.close();
        fos.close();
    }

	
	public static void lauch_website() throws IOException
	{
		driver = new ChromeDriver();
		driver.get("https://tarladalal.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		createExcel();
		
	}
	
	
	public void search_for_recipes() throws InterruptedException, IOException
	{
		driver.findElement(By.id("ctl00_txtsearch")).sendKeys("PCOS Recipes");
		driver.findElement(By.id("ctl00_imgsearch")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        page_index = driver.findElement(By.xpath("//a[@href='recipes-for-pcos-1040']")).getAttribute("href");
        page_index = page_index.replaceAll("https://tarladalal.com/","");
		driver.findElement(By.xpath("//a[@href='recipes-for-pcos-1040']")).click();
		Thread.sleep(10);
		morbidCondn = "PCOS";
		System.out.println("-------------------------------------------");
		System.out.println(morbidCondn);
		System.out.println("***********");
	    scrape_recipes();	
		
		
	}
	
	public void scrape_recipes() throws InterruptedException, IOException
	{
		timer_start = Instant.now();
		
		readExcel(morbidCondn+"_eliminatedlist");
		readExcel("allergies");
		readExcel(morbidCondn+"_toadd");
		
		//checking total pages	
    	List <WebElement> recipe_link_pages = driver.findElements(By.xpath("//a[@class='respglink']"));
    	int num =recipe_link_pages.size();
    	String total_pages_to_scrape = recipe_link_pages.get(num-1).getText();
        int total_pages = (Integer.parseInt(total_pages_to_scrape));
		System.out.println("Total pages to Scrape: "+total_pages);

		//total_pages
		//traversing thru recipe pages
		for(int i=1;i<=total_pages;i++)
		{
			driver.findElement(By.xpath("//a[@href='/"+page_index+"?pageindex="+i+"']")).click();
			String current_url = driver.getCurrentUrl();
			Thread.sleep(10);
			List <WebElement> recipes_url = driver.findElements(By.xpath("//a[@itemprop='url']"));
            recipe_url_size = recipes_url.size();
            System.out.println("Total Recipe cards in Page "+i+": "+recipe_url_size);
            //recipe_url_size
            //traversing recipe cards in each page
            for_loop:
            for(int j=0; j<recipe_url_size; j++)
            {
    		  recipes_url = driver.findElements(By.xpath("//a[@itemprop='url']"));
              recipe_name = recipes_url.get(j).getText();
              try
              {
            	  driver.findElement(By.xpath("//a[text()='" + recipe_name + "']")).click();
              }
              catch(ElementNotInteractableException | InvalidSelectorException e)
              {
            	  e.printStackTrace();
            	  continue for_loop;
              }
              ingredientsList = "";
              try
              {
              ingredientsList = driver.findElement(By.xpath("//div[@id='rcpinglist']")).getText();
              }
              catch(UnhandledAlertException e)
              {
            	  e.printStackTrace();
          		  continue for_loop;
          	  }
              flag = 0;
              outer:
              for(int k=0;k<eliminatedList.size();k++)
              {
            	  String s1 = eliminatedList.get(k).toLowerCase();
            	  String s2 = ingredientsList.toLowerCase();
            	  if(s2.contains(s1))
            	  {
            		  eliminated_recipe_count++;
            		  flag = 1;
            		  break outer;
                  }
            	  
              }
              if (flag == 0)
              {
          		  System.out.println(recipe_name);
            	  try
            	  {
            		  recipe_tags = driver.findElement(By.xpath("//div[@id='recipe_tags']")).getText();  
            	  }
            	  catch(NoSuchElementException e)
            	  {
            		  e.printStackTrace();
            		  recipe_tags = " ";
            	  }
                  
            	
            	//checking for allergies
            	  allergy_name = " ";
            	  outer1:
            	  for(int k=0;k<allergens.size();k++)
                  {
                	  String s1 = allergens.get(k).toLowerCase();
                	  String s2 = ingredientsList.toLowerCase();
                	  if(s2.contains(s1))
                	  {
                   		  allergy_name = s1;
                		  break outer1;
                      }
                  }
            	  
            	  //filtering good to have recipes
            	  toAddflag = 0;
              	  outer2:
                	  for(int k=0;k<toAdd.size();k++)
                      {
                    	  String s1 = toAdd.get(k).toLowerCase().trim();
                    	  String s2 = recipe_name.toLowerCase().trim();
                    	  String s3 = recipe_tags.toLowerCase().trim();
                    	  String s4 = ingredientsList.toLowerCase();
                    	  if(s2.contains(s1) || s3.contains(s1) || s4.contains(s1))
                    	  {
                    		  System.out.println("**Good to have Recipe");
                    		  tohave_count++;
                    		  toAddflag = 1;
                    		  break outer2;
                          }
                      }
              	  //checking recipe category
              	  rcp_category = " ";
            	  for(int k=0;k<recipe_category.length;k++)
                  {
                	  String s1 = recipe_tags.toLowerCase().trim();
                      String s2 = recipe_category[k].toLowerCase().trim();
                	  if(s1.contains(s2))
                	  {
                		  rcp_category = rcp_category + " " + s2;
                      }
                   }
            
            	recipe_url = driver.getCurrentUrl();
            	recipe_id = recipe_url.replaceAll("[^0-9]", "");
            	prep_time = " ";
            	cook_time = " ";
            	nutrient_values = " ";
            	try
            	{
            		prep_time = driver.findElement(By.xpath("//time[@itemprop='prepTime']")).getText();
                    cook_time = driver.findElement(By.xpath("//time[@itemprop='cookTime']")).getText();

            	}
            	catch(NoSuchElementException e)
            	{
            		e.printStackTrace();
            	}
            	    prep_method = driver.findElement(By.xpath("//div[@id='recipe_small_steps']")).getText();
            	try
            	{
                	nutrient_values = driver.findElement(By.xpath("//table[@id='rcpnutrients']")).getText();
            	} 
            	catch(NoSuchElementException e)
            	{
            		e.printStackTrace();
            	}
            	
                if((allergy_name).contains("egg"))
                {
                	food_category = "Eggitarian";
                }
                else if(recipe_tags.toLowerCase().trim().contains("vegan"))
                {
                	food_category = "Vegan";
                }
                else
                {
                	food_category = "Vegetarian";
                }
            	filtered_recipe_count++;
            	PCOSRecipeList.put(filtered_recipe_count, new ArrayList<String>());
            	recipe_list = PCOSRecipeList.get(filtered_recipe_count);

            	Collections.addAll(recipe_list,recipe_id,recipe_name,rcp_category,food_category,ingredientsList,
            			prep_time, cook_time,prep_method,nutrient_values,recipe_url,allergy_name,
            			Integer.toString(toAddflag));
        		
             }
              driver.get(current_url);
              driver.navigate().refresh();
              Thread.sleep(5);
           }
           total_recipes = total_recipes + recipe_url_size;
        }
		 writeExcel();
         System.out.println(morbidCondn);
         //System.out.println(PCOSRecipeList);
         System.out.println("Total Recipes:"+total_recipes);  
         System.out.println("Eliminated Recipes:"+eliminated_recipe_count);
         System.out.println("Filtered Recipes: "+filtered_recipe_count);
         System.out.println("Good to Have Recipes: " +tohave_count);
         timer_end = Instant.now();
         System.out.println("Time taken to execute in Minutes: "+ Duration.between(timer_start,timer_end).toMinutes());
	}
	public static void main(String args[]) throws IOException, InterruptedException
    {
		lauch_website();
	    scrapePCOS obj = new scrapePCOS();
	    obj.search_for_recipes();
    }
}
