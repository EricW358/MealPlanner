package com.example.mealplanner.Models;

import java.util.ArrayList;

public class OneInstructionsModel {

    private String name;
    private ArrayList<StepsModel> steps;

    public OneInstructionsModel( String name, ArrayList<StepsModel> steps) {
        this.name = name;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<StepsModel> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepsModel> steps) {
        this.steps = steps;
    }
}
