package com.example.mealplanner;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mealplanner.Models.OneDayModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

public class DayListModel {

    private static final String SHARED_PREF_NAME = "Recipes";
    private static final String SHARED_PREF_KEY = "DayListModel";

    private static TreeMap<Integer, OneDayModel> allDays = new TreeMap<Integer, OneDayModel>();
    private static OneDayModel lastestClick;
    private static Context context;


    public DayListModel(Context context) {
        DayListModel.context = context;
        getSaved();
    }

    public static TreeMap<Integer, OneDayModel> getAllDays() {
        return allDays;
    }

    public static void setAllDays(TreeMap<Integer, OneDayModel> allDays) {
        DayListModel.allDays = allDays;
    }

    public static void addOneDay(OneDayModel newDay) {
        allDays.put(newDay.getDate(), newDay);
        saveEverything();
    }

    public static OneDayModel getLastestClick() {
        return lastestClick;
    }

    public static void setLastestClick(OneDayModel lastestClick) {
        DayListModel.lastestClick = lastestClick;
        saveEverything();
    }

    public static int getLatestBreakfast() {
        return lastestClick.getBreakfast();
    }

    public static int getLatestLunch() {
        return lastestClick.getLunch();
    }

    public static int getLatestDinner() {
        return lastestClick.getDinner();
    }

    public static void addBreakfast(Integer givenDate, int RecipeId) {
        allDays.get(givenDate).setBreakfast(RecipeId);
        saveEverything();
    }

    public static void addLunch(Integer givenDate, int RecipeId) {
        allDays.get(givenDate).setLunch(RecipeId);
        saveEverything();
    }

    public static void addDinner(Integer givenDate, int RecipeId) {
        allDays.get(givenDate).setDinner(RecipeId);
        saveEverything();
    }

    public static ArrayList<OneDayModel> getSortedList() {
        Collection<OneDayModel> colValues = allDays.values();
        return new ArrayList<>(colValues);
    }

    private static void getSaved() {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPref.getString(SHARED_PREF_KEY, "");
        Gson gson = new Gson();
        DayListModel ret = gson.fromJson(json, DayListModel.class);
        allDays = ret.getAllDays();
        lastestClick = ret.getLastestClick();
    }

    private static void saveEverything() {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor= sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(new SavedRecipes(context));
        prefsEditor.putString(SHARED_PREF_KEY, json);
        prefsEditor.commit();
    }
}
