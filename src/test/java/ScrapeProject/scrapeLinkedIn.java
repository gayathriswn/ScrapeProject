package ScrapeProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JList;
import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

// to click on jobs button //span[contains(text(),'Jobs')]
public class scrapeLinkedIn 
{
	WebDriver driver = new ChromeDriver();
	String url = "https://www.linkedin.com/";
	By page_fwd_link = By.xpath("//a[@aria-label='Next Page']");
	String path  = System.getProperty("user.dir") + "/src/test/resources/TestData/";	
	String jobTitle = "", jobSalary="", jobId = "", companyName = "", jobType="";
	HashMap<Integer, ArrayList<String>> jobsList = new HashMap<Integer, ArrayList<String>>();
	ArrayList<String> jlist = new ArrayList<String>();
	int job_index = 1;
	Instant timer_start, timer_end;

	public void createExcel() throws IOException
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet worksheet = workbook.createSheet("Jobs_SDET_Remote");
		XSSFCellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 15);
		font.setBold(true);
		style.setFont(font);
		style.setFillPattern(FillPatternType.FINE_DOTS);
		style.setFillBackgroundColor((IndexedColors.LIGHT_TURQUOISE1.getIndex()));
		Row row = worksheet.createRow(0);
		
		row.createCell(0).setCellValue("Job Role");
		row.createCell(1).setCellValue("Salary");
		row.createCell(2).setCellValue("Company Name");
		row.createCell(3).setCellValue("Job Type");

		for(int j = 0; j<=3; j++)
		{
		 row.getCell(j).setCellStyle(style);
			
		}
		File excelFile = new File(path+"Indeed_jobs.xlsx");
		FileOutputStream fos = new FileOutputStream(excelFile);
		workbook.write(fos);
				
		workbook.close();
		fos.close();
		System.out.println("Excel Created");
	}
	
	public void writeExcel() throws IOException
	{
		String strFilePath  = path+"Indeed_jobs.xlsx";
		
		File excelfile = new File(strFilePath);
		FileInputStream fis = new FileInputStream(excelfile);	
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet worksheet = workbook.getSheet("Jobs_SDET_Remote");
        
        System.out.println("writing to excel");
        
        for(int j=1;j<job_index;j++)
        {
        	//Accessing the details from hash map array
        	jlist = jobsList.get(j);
        	//System.out.println(jlist.get(1));
        	
        	int rowCount = worksheet.getLastRowNum();
            Row row = worksheet.createRow(++rowCount);

            row.createCell(0).setCellValue(jlist.get(0));
            row.createCell(1).setCellValue(jlist.get(1));
            row.createCell(2).setCellValue(jlist.get(2));
            row.createCell(3).setCellValue(jlist.get(3));
        }
            File excelFile = new File(strFilePath);
            FileOutputStream fos = new FileOutputStream(excelFile);
            workbook.write(fos);
            workbook.close();	
    }
	
	
	
    @Test(priority = 1)
    public void open_website() throws IOException
	{
    	timer_start = Instant.now();
		System.out.println("Fetching the URL...");
    	driver.get(url);
		driver.manage().window().maximize();
		driver.navigate().refresh();
		createExcel();
	}
	

    @Test(priority=2)
    public void search_option()
    {
    	System.out.println("Seach begins...");
    	driver.findElement(By.id("text-input-what")).sendKeys("SDET");
    	driver.findElement(By.id("text-input-where")).sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        driver.findElement(By.id("text-input-where")).sendKeys("Remote");
        driver.findElement(By.xpath("//button[text()='Search']")).click();
    }
    
    @Test(priority = 3)
    public void search_jobs() throws InterruptedException, IOException
    {
    	System.out.println("Data Scraping begins...");
    	//System.out.println("Scraping Data in Page "+ driver.findElement(page_fwd_link).getText());
    	int counter = 0;
    	while(counter < 1)
    	{	
        	List <WebElement> jobCards = driver.findElements(By.xpath("//div[@class='job_seen_beacon']//a[contains(@id,'job') or contains(@id,'sj_')]"));
         	int size = jobCards.size();
        	
        	for(int i=0; i<size; i++)
        	{
        		jobsList.put(job_index, new ArrayList<String>());
        		ArrayList<String> jlist = jobsList.get(job_index);
        		
        		jobTitle = jobCards.get(i).getText();
        	    jlist.add(jobTitle);

        		jobId = jobCards.get(i).getAttribute("id");
        		try
        		{
            		  jobSalary = driver.findElement(By.xpath("//a[@id = '"+jobId+"']/../../..//div[@class='metadata salary-snippet-container']")).getText();
            		  jlist.add(jobSalary);
        		}
        		catch(Exception e)
        		{
        			jobSalary = " ";
            		jlist.add(jobSalary);

        		}
        		try
        		{
        			companyName = driver.findElement(By.xpath("//a[@id = '"+jobId+"']/../../..//span[@class='companyName']")).getText();
        			jlist.add(companyName);
        			
        		}
        		catch(Exception e)
        		{
        			companyName = " ";
        			jlist.add(companyName);
        		}
        		
        		try
        		{
        			jobType = driver.findElement(By.xpath("//a[@id = '"+jobId+"']/../../..//div[@class='metadata']")).getText();
        			jlist.add(jobType);
        		}
        		catch(Exception e)
        		{
        			jobType = " ";
        			jlist.add(jobType);
        		}
        		job_index++;
        	}
        	
        	if(driver.findElements(page_fwd_link).size() > 0)
        	{
        		driver.findElement(page_fwd_link).click();
        		//System.out.println("Scraping Data in Page "+ driver.findElement(page_fwd_link).getText());
        	}
        	else 
        	{
        		counter = 1;
        		System.out.println("Scraping Done...");
        		System.out.println("Total jobs scrapped: "+job_index);
        	}
         }
    	writeExcel();
    	System.out.println(jobsList);
    	timer_end = Instant.now();
        System.out.println("Time taken to execute in Seconds: "+ Duration.between(timer_start,timer_end).toSeconds());;
    }
}
                    