package com.example.mealplanner.Models;

import com.example.mealplanner.ui.grocery.OneGroceryListItem;

import java.util.*;


public class GoceryListModel {

    private ArrayList<OneGroceryListItem> ingrediets;
//    private ArrayList<OneGroceryListItem> sortedDates;


    public GoceryListModel() {
        this.ingrediets =  new ArrayList<OneGroceryListItem>();
    }


    public boolean addIngredient(OneGroceryListItem ingredient){



        if(containsIngredient(ingredient)) {
            equalIngredient(ingredient).addAmount(ingredient);
            return true;

        }else {
            ingrediets.add(ingredient);
        }

        return false;
    }

    public boolean containsIngredient(OneGroceryListItem ingredient){

        for(OneGroceryListItem proccessedingredient:ingrediets ){

            if(proccessedingredient.equals(ingredient)){
                return true;
            }
        }

        return false;
    }

    public OneGroceryListItem equalIngredient(OneGroceryListItem ingredient){

        for(OneGroceryListItem proccessedingredient:ingrediets ){

            if(proccessedingredient.equals(ingredient)){
                return proccessedingredient;
            }
        }
        return null;
    }






}
