package ScrapeRecipes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.annotations.Test;
/*
public class tryout {

	public static WebDriver driver;
	   @Test
	    public void launch()
	    {
	    driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://dsportalapp.herokuapp.com/");
		
		List<WebElement> el = driver.findElements(By.xpath("//*"));
	    int size = el.size();
	    System.out.println(size);
	    
	    for(int i=0;i<size;i++)
	    {
	    	String t = el.get(i).getText();
	    	System.out.println(t);
	    }
      

		//WebElement l = driver.findElement(By.tagName("body"));
		//String t = l.getText();
		

		//WebElement l = driver.findElement(By.tagName("body"));
		//String t = l.getText();
		//System.out.println(t);
	    }
	    @Test
	   public void test1()
	   {
		   String text = "Showing 2 of 5 of 10 entries";
		   text = text.replaceAll("[0-9]", " ");
		   System.out.println(text);
	   }
	    
	    @Test
	    public void test2()
	    {
	    	int res = new Scanner("Str87uyuy232").useDelimiter("\\D+").nextInt();
	    	System.out.println(res);
	    }
	    
	    @Test
	    public void test3()
	    {
	    	String str = "7576  1  12/22/22  Python  U01  Advanced Python  ok  c:\\ClassNotes  c:\\Recordings ";
	        String str1 = "27/08/1985";
          String[] arrStr = str1.split("/", 3);
          //Arrays.sort(arrStr, Collections.reverseOrder());
          //for (String a : arrStr)
            //  System.out.println(a);
          
         // Arrays.sort(arrStr);
          for (String a : arrStr)
              System.out.println(a);
          //System.out.print(arrStr[2]);
	        
	        
	    }
	    
	    @Test
	    public void relative_ocators()
	    {
	    	  driver = new ChromeDriver();
	    	  driver.get("https://www.numpyninja.com/contact");
	    	  driver.manage().window().maximize();
	    	  driver.navigate().refresh();
	    
	    	  //Here I'm locating the firstname field by using the input element "above" the Email id field
	    	    	  
	    	  By firstname = RelativeLocator.
	    			  //with(By.tagName("input")).above(By.id("input_comp-l1c0ku03"));
	    	  driver.findElement(firstname).sendKeys("XXXX");
	    	  
	    	   //Here I'm locating the emaild id field by using the input element "toLeftOf" the Phone Number field

	    	  By email = RelativeLocator.with(By.tagName("input")).toLeftOf(By.name("phone"));
	    	  driver.findElement(email).sendKeys("xxxxyy@gmail.com");

	    	   //Here I'm locating the lastname field by using the input element "toRightOf" the Firstname field
	    	  By lastname = RelativeLocator.with(By.tagName("input")).toRightOf(By.name("first-name"));
	    	  driver.findElement(lastname).sendKeys("YYYY");
	    	  
	    	   //Here I'm locating the phone number field by using the input element "nearby" lastname field
		      By phno = RelativeLocator.with(By.tagName("input")).near(By.name("last-name"));
	    	  driver.findElement(phno).sendKeys("123-456-7810");
	    	 
	    	  //Here I'm locating the message box field by using the textarea element "below" email id field

		      By msgbox = RelativeLocator.with(By.tagName("textarea")).below(By.name("email"));
	    	  driver.findElement(msgbox).sendKeys("Looking for SDET training");
	           
	    }
}

*/