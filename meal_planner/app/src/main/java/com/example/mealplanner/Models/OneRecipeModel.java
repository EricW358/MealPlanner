package com.example.mealplanner.Models;


import java.util.*;


public class OneRecipeModel {

    private int id;
    private String title;
    private String image;
    private int readyInMinutes;
    private int servings;
    private String summary;
    private ArrayList<OneInstructionsModel> analyzedInstructions;

    public OneRecipeModel(int id, String title, String image, int readyInMinutes, int servings, String summary, ArrayList<OneInstructionsModel> analyzedInstructions) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.summary = summary;
        this.analyzedInstructions = analyzedInstructions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<OneInstructionsModel> getInstructions() {
        return analyzedInstructions;
    }

    public void setInstructions(ArrayList<OneInstructionsModel> instructions) {
        this.analyzedInstructions = instructions;
    }

    public ArrayList<StepsModel> getInstructionSteps() {
        return analyzedInstructions.get(0).getSteps();
    }
}

