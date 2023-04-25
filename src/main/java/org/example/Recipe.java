package org.example;

import java.util.ArrayList;

public class Recipe {
	private String ID;
	private String name;
	private ArrayList<String> ingredients;
	private int prepTime;
	private int cookTime;
	private ArrayList<String> prepMethod;
	private String URL;


	public Recipe(String ID, String name, ArrayList<String> ingredients, int prepTime, int cookTime, ArrayList<String> prepMethod, String URL ){
		this.ID = ID;
		this.name = name;
		this.ingredients = ingredients;
		this.prepTime = prepTime;
		this.cookTime = cookTime;
		this.prepMethod = prepMethod;
		this.URL = URL;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
	}

	public int getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}

	public int getCookTime() {
		return cookTime;
	}

	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}

	public ArrayList<String> getPrepMethod() {
		return prepMethod;
	}

	public void setPrepMethod(ArrayList<String> prepMethod) {
		this.prepMethod = prepMethod;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}

	@Override
	public String toString() {
		return "Recipe Name: " + name + "\nURL: " + URL + "\nPrep Time: " + prepTime + " mins\nCook Time: "
				+ cookTime + " mins\nIngredients: " + ingredients.toString() + "\nPreparation Method: \n" + getPrepMethod().toString();
	}
}
