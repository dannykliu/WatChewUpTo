package com.example.dannyliu.watchewupto;

/**
 * Created by dannyliu on 11/15/15.
 */
public class FoodItem {
    public String name;
    public int serving;
    public boolean vegetarian;
    public String ingredients;
    public int calories;
    public int fat;
    public int saturated;
    public int cholestrol;
    public int sodium;
    public int carbohydrates;
    public int fibre;
    public int sugars;
    public int protein;
    public int vitaminA;
    public int vitaminC;
    public int calcium;
    public int iron;
    public int id;

    public FoodItem() {

    }

    public FoodItem(String name, int serving, boolean vegetarian, String ingredients, int calories, int fat, int saturated, int cholestrol, int sodium, int carbohydrates, int fibre, int sugars, int protein, int vitaminA, int vitaminC, int calcium, int iron, int id) {
        this.name = name;
        this.serving = serving;
        this.vegetarian = vegetarian;
        this.ingredients = ingredients;
        this.calories = calories;
        this.fat = fat;
        this.saturated = saturated;
        this.cholestrol = cholestrol;
        this.sodium = sodium;
        this.carbohydrates = carbohydrates;
        this.fibre = fibre;
        this.sugars = sugars;
        this.protein = protein;
        this.vitaminA = vitaminA;
        this.vitaminC = vitaminC;
        this.calcium = calcium;
        this.iron = iron;
        this.id = id;
    }
}
