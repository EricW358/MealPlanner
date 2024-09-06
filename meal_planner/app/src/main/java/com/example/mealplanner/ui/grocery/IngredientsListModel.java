package com.example.mealplanner.ui.grocery;

import java.util.ArrayList;

public class IngredientsListModel {

    private ArrayList<OneGroceryListItem> ingredients;
    private int offset;
    private int number;
    private int totalResults;

    public IngredientsListModel(ArrayList<OneGroceryListItem> ingredients, int offset, int number, int totalResults) {
        this.ingredients = ingredients;
        this.offset = offset;
        this.number = number;
        this.totalResults = totalResults;
    }

    public ArrayList<OneGroceryListItem> getIngredients() {
        return ingredients;
    }
}
