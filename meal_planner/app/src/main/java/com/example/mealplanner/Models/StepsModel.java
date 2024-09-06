package com.example.mealplanner.Models;

import com.example.mealplanner.Models.IngredientsModel;

import java.util.ArrayList;

public class StepsModel {
    private int number;
    private String step;
    private ArrayList<IngredientsModel> ingredients;

    public StepsModel(int number, String step, ArrayList<IngredientsModel> instructions) {
        this.number = number;
        this.step = step;
        this.ingredients = instructions;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public ArrayList<IngredientsModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientsModel> ingredients) {
        this.ingredients = ingredients;
    }
}
