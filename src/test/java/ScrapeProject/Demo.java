package ScrapeProject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

//Implement Singleton design pattern

public class Demo {
	
	//create a private static instance of the class
	
	private static WebDriver driver;
	
	//create a private constructor
	
	private Demo()
	{
		
	}

	//create a a public method to access the object of the class
	
	public static WebDriver getInstanceMethod()
	{
		if(driver == null)
		{
			driver = new ChromeDriver();
		}
		return driver;
	}
}
