package com.example.mealplanner.ui.BottomSheet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.mealplanner.SavedRecipes;
import com.example.mealplanner.Models.OneRecipeModel;
import com.example.mealplanner.Models.RecipesListModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import com.example.mealplanner.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BottomSheetFragment extends BottomSheetDialogFragment {


    private RecyclerView recipeRV;
    private ProgressBar loadingPB;
    private ArrayList<OneRecipeModel> recipesArrayList;
    private SearchView searchView;
    private RecipeListAdapter recipeListAdapter;
    private int listLength;
    private String favoriteCuisine;
    private String lifestyle;
    private String allergies;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom_sheet_list_dialog, container, false);

        recipeRV = view.findViewById(R.id.idRecycleList);
        loadingPB = view.findViewById(R.id.idPBLoading);
        loadRVSettings();
        recipesArrayList = new ArrayList<>();
        recipeListAdapter = new RecipeListAdapter(recipesArrayList, this.getContext());
        recipeRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recipeRV.setAdapter(recipeListAdapter);
        getRecipesList("");
        recipeListAdapter.notifyDataSetChanged();

        searchView = view.findViewById(R.id.idEditSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                getRecipesList(query);
                recipeListAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    private void loadRVSettings() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        listLength = sp.getInt("recipe_amount", 10);
        favoriteCuisine = sp.getString("favorite_cuisine", "");
        lifestyle = sp.getString("lifestyle","");

    }


    private void getRecipesList(String query){
        loadingPB.setVisibility(View.VISIBLE);
        recipesArrayList.clear();
        String apiKey = "6c35ec72cd1641069466c4d615813143";
        String queryPass = "&query="+ query;
        String numPass = "&number="+ listLength;
        String favoritePass = "&cuisine=" + favoriteCuisine;
        String lifestylePass = "&diet=" + lifestyle;
        String recipeURL = "https://api.spoonacular.com/recipes/complexSearch?addRecipeInformation=true&instructionsRequired=true&apiKey="+apiKey+queryPass+numPass+lifestylePass;
        String defaultTest = "https://api.spoonacular.com/recipes/complexSearch?addRecipeInformation=true&instructionsRequired=true&apiKey="+apiKey+numPass+lifestylePass+favoritePass;
        String REAL_BASE = "https://api.spoonacular.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(REAL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPIcall retrofitAPIcall = retrofit.create(RetrofitAPIcall.class);
        Call<RecipesListModel> call;
        if(query.equals("")){
            call = retrofitAPIcall.getRandomRecipes(defaultTest);
        }
        else {
            call = retrofitAPIcall.getSearchRecipe(recipeURL);
        }

        call.enqueue(new Callback<RecipesListModel>() {
            @Override
            public void onResponse(Call<RecipesListModel> call, Response<RecipesListModel> response) {
                RecipesListModel recipesListModel = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<OneRecipeModel> oneRecipes = recipesListModel.getResults();
                for (int i = 0; i < oneRecipes.size(); i++){
                    OneRecipeModel temp = oneRecipes.get(i);
                    recipesArrayList.add(new OneRecipeModel(temp.getId(), temp.getTitle(), temp.getImage(), temp.getReadyInMinutes(), temp.getServings(), temp.getSummary(), temp.getInstructions()));
                }
                recipeListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RecipesListModel> call, Throwable t) {
                System.out.print("this is the fail pail");
            }
        });
    }

    private class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

        private ArrayList<OneRecipeModel> recipesArrayList;
        private Context context;

        public RecipeListAdapter(ArrayList<OneRecipeModel> recipesArrayList, Context context) {
            this.recipesArrayList = recipesArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_bottom_sheet_list_dialog_item, parent, false);
            return new RecipeListAdapter.ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            OneRecipeModel recipe = recipesArrayList.get(position);
            holder.titleRecipe.setText(recipe.getTitle());
            holder.timeRecipe.setText("Time Needed: "+ recipe.getReadyInMinutes());
            holder.servingsRecipe.setText("Servings " + recipe.getServings());
            Picasso.get().load(recipe.getImage()).into(holder.imageRecipe);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OneRecipeModel temp = recipesArrayList.get(holder.getAbsoluteAdapterPosition());
                    //SavedRecipes.addSavedRecipes(temp);
                    SavedRecipes.setNewestRecipe(temp);
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);

                    navController.navigate(R.id.navigation_home);
                    navController.navigate(R.id.confirmSheetFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return recipesArrayList.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            private TextView titleRecipe, timeRecipe, servingsRecipe;
            private ImageView imageRecipe;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                titleRecipe = itemView.findViewById(R.id.idTitleRecipe);
                timeRecipe = itemView.findViewById(R.id.idTimeRecipe);
                servingsRecipe = itemView.findViewById(R.id.idServingsRecipe);
                imageRecipe = itemView.findViewById(R.id.idImageRecipe);
            }
        }
    }
}