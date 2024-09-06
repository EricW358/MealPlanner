package com.example.mealplanner;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mealplanner.Models.OneRecipeModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SavedRecipes {

    private static final String SHARED_PREF_NAME = "Recipes";
    private static final String SHARED_PREF_KEY = "SavedRecipes";


    private static Map<Integer, OneRecipeModel> savedRecipes = new HashMap<Integer, OneRecipeModel>();
    private static OneRecipeModel newestRecipe;
    private static Context context;


    public SavedRecipes(Context context){
        SavedRecipes.context = context;
        getSaved();
    }


    public static Map<Integer, OneRecipeModel> getSavedRecipes() {
        return savedRecipes;
    }

    public static OneRecipeModel getSpecificRecipe(int recipeKey) {
        return savedRecipes.get(recipeKey);
    }

    public static void addSavedRecipes(OneRecipeModel newRecipe) {
        savedRecipes.put(newRecipe.getId(), newRecipe);
        saveEverything();
    }

    public static OneRecipeModel getNewestRecipe() {
        return newestRecipe;
    }

    public static void setNewestRecipe(OneRecipeModel newestRecipe) {
        SavedRecipes.newestRecipe = newestRecipe;
        saveEverything();
    }

    public static ArrayList<OneRecipeModel> getArraySavedRecipes() {
        Collection<OneRecipeModel> colValues = savedRecipes.values();
        return new ArrayList<>(colValues);
    }

    private static void getSaved() {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPref.getString(SHARED_PREF_KEY, "");
        Gson gson = new Gson();
        SavedRecipes ret = gson.fromJson(json, SavedRecipes.class);
        savedRecipes = ret.getSavedRecipes();
        newestRecipe = ret.getNewestRecipe();
    }

    private static void saveEverything() {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(new SavedRecipes(context));
        prefsEditor.putString(SHARED_PREF_KEY, json);
        prefsEditor.commit();
    }
}
