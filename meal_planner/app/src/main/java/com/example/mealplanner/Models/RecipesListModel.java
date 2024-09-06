package com.example.mealplanner.Models;

import java.util.ArrayList;

public class RecipesListModel {

    private ArrayList<OneRecipeModel> results;
    private int offset;
    private int number;
    private int totalResults;

    public RecipesListModel(ArrayList<OneRecipeModel> results, int offset, int number, int totalResults) {
        this.results = results;
        this.offset = offset;
        this.number = number;
        this.totalResults = totalResults;

    }

    public ArrayList<OneRecipeModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<OneRecipeModel> results) {
        this.results = results;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
