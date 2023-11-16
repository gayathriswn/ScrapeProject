package ScrapeProject;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class callDemoClass {
	
	public static WebDriver driver1 = Demo.getInstanceMethod();
	
	@Test
	public void test1()
	{
		Demo.getInstanceMethod().get("https://www.google.com/");
		System.out.println("Test1");
	}
	@Test
	public void test2()
	{
		Demo.getInstanceMethod().get("https://www.numpyninja.com/");
		System.out.println("Test2");
	}
	@Test
	public void test3()
	{
		Demo.getInstanceMethod().get("https://www.selenium.dev/");
		System.out.println("Test3");
	}

}
