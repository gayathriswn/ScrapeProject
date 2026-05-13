Healthy Recipe Scraper for Medical Conditions & Allergy Filtering

Project Overview
Developed an automated recipe scraping framework using **Java**, **Selenium WebDriver**, and **TestNG** to extract healthy recipes from the **Tarla Dalal** website.

The application categorizes recipes based on various medical conditions and exports the filtered data into structured Excel spreadsheets with separate sheets for each condition.

### Supported Medical Conditions

* Diabetes
* PCOS
* Hypertension
* Hypothyroidism

The project also includes **allergy-based filtering** to identify recipes containing allergens such as:

* Peanuts
* Sesame
* Dairy
* Gluten (configurable)

## Technologies Used

* Java
* Selenium WebDriver
* TestNG
* Apache POI
* Maven

## Key Features

* Automated UI navigation and scraping
* Recipe categorization by medical condition
* Allergy detection from ingredient lists
* Excel spreadsheet generation with multiple sheets
* Structured data extraction and transformation
* Reusable and easily scalable automation framework

## Data Captured

Each recipe is scraped and stored with the following attributes:

| Column Name         |
| ------------------- |
| Recipe Id           |
| Recipe Name         |
| Recipe Category     |
| Food Category       |
| Ingredients List    |
| Preparation Time    |
| Cooking Time        |
| Preparation Method  |
| Nutrient Values     |
| Recipe URL          |
| Allergy Information |


Excel Output Structure

The extracted recipes are organized into separate Excel sheets based on the patient's morbid condition.

Example:

* Diabetes Sheet
* PCOS Sheet
* Hypertension Sheet
* Hypothyroidism Sheet

This structure improves accessibility and allows easier analysis of condition-specific healthy recipes.

## Workflow

1. Launch and navigate recipe pages using Selenium automation.
2. Scrape recipe details and nutritional information.
3. Analyze ingredients and apply condition-specific filtering rules.
4. Detect allergy-related ingredients.
5. Export categorized recipes into Excel sheets.

## Learning Outcomes

This project helped strengthen skills in:

* Web scraping and UI automation
* Data extraction and validation
* Java collections and object-oriented programming
* Spreadsheet handling using Apache POI
* Test automation using TestNG
* Data categorization and filtering logic

## Future Enhancements

* Database integration
* API-based scraping
* Dynamic allergy rule filter
* Nutrition scoring system
* Dashboard for recipe search and analytics

This project combines automation testing, data scraping, and healthcare-focused filtering to create a structured healthy recipe recommendation dataset.
