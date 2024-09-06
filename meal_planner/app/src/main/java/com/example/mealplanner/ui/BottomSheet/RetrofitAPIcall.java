package com.example.mealplanner.ui.BottomSheet;

import com.example.mealplanner.Models.RecipesListModel;
import com.example.mealplanner.ui.grocery.IngredientsListModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPIcall {

    @GET
    Call<RecipesListModel> getRandomRecipes(@Url String url);

    @GET
    Call<RecipesListModel> getSearchRecipe(@Url String url);

    @GET
    Call<IngredientsListModel> getRecipeIngredients(@Url String url);
}
