package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Scraper
{
	private WebDriver driver;
	private ArrayList<Recipe> recipes;
	private ArrayList<String> eliminateList;

	public Scraper() throws Exception {
		//WebDriverManager.firefoxdriver().setup();
		//driver = new FirefoxDriver();

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		//driver.navigate().to("https://www.tarladalal.com/");

		recipes = new ArrayList<>(28);
		eliminateList = ReadEliminateList.readColumnFromExcel("C:\\Users\\samap\\Downloads\\IngredientsAndComorbidities-ScrapperHackathon.xlsx", 0);
	}

	// scrapes and parses all recipes in a single page
	public void parseDataOnPage() throws IOException {

		// list of WebElements that store all the links
		List<WebElement> raw_recipes = driver.findElements(By.className("rcc_recipename"));

		// arraylist to store all the links in string form (can be optimized)
		ArrayList<String> links = new ArrayList<>(14);

		// loop through raw_recipes to fill the links arraylist
		for (WebElement e : raw_recipes)
		{
			// .findElement -----> finds the tag <a> inside the current WebElement
			// .getAttribute ----> returns the href attribute of the <a> tag in the current WebElement
			links.add(e.findElement(By.tagName("a")).getAttribute("href"));
		}

		List<WebElement> raw_ids = driver.findElements((By.className("rcc_rcpno")));

		ArrayList<String> ids = new ArrayList<>(14);

		for (WebElement e : raw_ids)
		{
			String s = e.findElement(By.tagName("span")).getText().substring(0);
			String s2 = s.substring(0, s.indexOf("\n"));
			ids.add(s2);
		}

		int i = 0;

		// loop through the links to access the data for each recipe in a single page
		for (i = 0; i < links.size(); i++) {
			String link = links.get(i);
			// get the DOM in HTML format of the recipe from the link
			Document r_page = null;
			try {
				r_page = Jsoup.connect(link).get();
			} catch (HttpStatusException e) {
				if (e.getStatusCode() == 404) continue;
			}

			// Getting ingredients first extra computation is not done on stuff that'll be filtered out

			// ArrayList to store each ingredient in string format
			ArrayList<String> ingredients = new ArrayList<>();

			// get the specific area that deals with the ingredients
			Element ingredients_div = r_page.select("div#rcpinglist").first();
			// get the list of ingredients which are blue (links), made an executive decision to screw the rest of them >:(
			assert ingredients_div != null;
			Elements ingredients_links = ingredients_div.select("a");

			// loop to add the ingredients received into the arraylist in string format
			for (Element e : ingredients_links) {
				ingredients.add(e.text());
			}
			if (containsFlagIngredient(ingredients)) continue;

			// get the name of the current recipe
			String name = Objects.requireNonNull(r_page.getElementById("ctl00_cntrightpanel_lblRecipeName")).text();

			// .parseInt ----------> converts a String to int
			// .select ------------> focuses on a specific tag that you identified and everything inside it
			// .first -------------> get only one of the tag instead of all of them
			// .text --------------> returns the text in the selected tag in String format
			// .replaceAll --------> replaces all substrings that match the first param to the second param in the string
			// .strip -------------> removes spaces (whitespaces) from the front and end of the string
			int prepTime;
			int cookTime;
			try {
				prepTime = Integer.parseInt(r_page.select("[itemprop=prepTime]").first().text().replaceAll("[^\\d]", "").strip());
				cookTime = Integer.parseInt(r_page.select("[itemprop=cookTime]").first().text().replaceAll("[^\\d]", "").strip());
			} catch (Exception e) {
				prepTime = 0;
				cookTime = 0;
			}

			//to get the prep method

			ArrayList<String> prepMethod = new ArrayList<>();

			Element prepMethod_div = r_page.select("#recipe_small_steps").first();
			Elements prepMethod_links = prepMethod_div.select("[itemprop = text]");

			for (Element e : prepMethod_links) {
				prepMethod.add(e.text());
			}

			// add a new recipe using all the information scraped from the current page
			// new Recipe -------> calls the *constructor* that you wrote in Recipe to create a new Recipe object, i.e. a box has been created with the specific details in the current page
			recipes.add(new Recipe(ids.get(i), name, ingredients, prepTime, cookTime, prepMethod, link));

		}

	}

	// scrapes and parses all recipes in the entire site
	public void parseDataFull(int pageNum) throws IOException {

		pageNum = Math.min(pageNum, 23);
		for (int i = 1; i <= pageNum; i++) {
			driver.navigate().to("https://www.tarladalal.com/recipes-for-indian-diabetic-recipes-370?pageindex=" + i);
			parseDataOnPage();
			System.out.println("Finished Page " + i);
		}
	}

	public boolean containsFlagIngredient(ArrayList<String> Ingredients) throws IOException {


		for (String str : Ingredients) {
			for (String str2 : eliminateList) {
				if (str2.contains(str)) {
					return true;
				}
			}
		}

		return  false;
	}

	// Main function to run the entire process of scraping and storage
	// use mostly for debugging only, use different testing functions etc etc for other reasons
	public static void main(String[] args) throws Exception {
		Scraper sc = new Scraper();

		// use for debugging purposes only
		// after completing parseDataFull, replace with that
		sc.parseDataFull(23);

		sc.driver.close();
	}

	public ArrayList<Recipe> getRecipes()
	{
		return recipes;
	}

	public void printRecipes()
	{
		for (Recipe r : recipes)
		{
			System.out.println(r);
		}
	}

	public void closeDriver()
	{
		driver.close();
	}
}
