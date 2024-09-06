package com.example.mealplanner.ui.grocery;

import java.util.ArrayList;
import java.util.Date;

public class OneGroceryListItem {

    private int groceryID;
    private int metricAmount;
    private int usAmount;
    private String metricUnit;
    private String usUnit;
    private ArrayList<Date> dates;
//    private ArrayList<Re>

    public OneGroceryListItem(int groceryID, int metricAmount, int usAmount, String metricUnit, String usUnit) {
        this.groceryID = groceryID;
        this.metricAmount = metricAmount;
        this.usAmount = usAmount;
        this.metricUnit = metricUnit;
        this.usUnit = usUnit;
        dates = new ArrayList<Date>();
    }

    public OneGroceryListItem(int groceryID, int metricAmount, int usAmount, String metricUnit, String usUnit, Date date) {
        this.groceryID = groceryID;
        this.metricAmount = metricAmount;
        this.usAmount = usAmount;
        this.metricUnit = metricUnit;
        this.usUnit = usUnit;
        dates = new ArrayList<Date>();
        dates.add(date);
    }


    public OneGroceryListItem(int groceryID) {
        this.groceryID = groceryID;
    }


    public int getGroceryID() {
        return groceryID;
    }

    public void setGroceryID(int groceryID) {
        this.groceryID = groceryID;
    }

    public int getMetricAmount() {
        return metricAmount;
    }

    public void setMetricAmount(int metricAmount) {
        this.metricAmount = metricAmount;
    }

    public int getUsAmount() {
        return usAmount;
    }

    public void setUsAmount(int usAmount) {
        this.usAmount = usAmount;
    }

    public String getMetricUnit() {
        return metricUnit;
    }

    public String getUsUnit() {
        return usUnit;
    }

    public void addAmount(OneGroceryListItem otherIngredient){

        usAmount += otherIngredient.getUsAmount();
        metricAmount += otherIngredient.getMetricAmount();
    }



    @Override
    public String toString() {
        return "OneIngredietModel{" +
                "groceryID=" + groceryID +
                ", metricAmount=" + metricAmount +
                ", usAmount=" + usAmount +
                '}';
    }


    public Boolean equals(OneGroceryListItem otherIngredient){

        if(groceryID == otherIngredient.getGroceryID()) {
            return true;
        }
        return false;
    }

}
