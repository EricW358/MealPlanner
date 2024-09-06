package com.example.mealplanner.Models;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OneDayModel {

    private int breakfast;
    private int lunch;
    private int dinner;
    private int date;

    public OneDayModel(int givenDate) {
        breakfast = 0;
        lunch = 0;
        dinner = 0;
        date = givenDate;

    }

    public OneDayModel(int breakfastKey, int lunchKey, int dinnerKey, int givenDate) {
        breakfast = breakfastKey;
        lunch = lunchKey;
        dinner = dinnerKey;
        date = givenDate;
    }

    public int getBreakfast() {
        return breakfast;
    }

    public int getLunch() {
        return lunch;
    }

    public int getDinner() {
        return dinner;
    }

    public void setBreakfast(int newBreakfastKey){
        breakfast = newBreakfastKey;
    }

    public void setLunch(int newLunchKey){
        lunch = newLunchKey;
    }

    public void setDinner(int newDinnerKey){
        dinner = newDinnerKey;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getFormattedDate() {
        Calendar c = Calendar.getInstance();
        int year = date / 10000;
        int month = (date - year * 10000) / 100;
        int day = date - year * 10000 - month * 100;

        c.set(year, month, day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
        return simpleDateFormat.format(c.getTime());


    }
}
