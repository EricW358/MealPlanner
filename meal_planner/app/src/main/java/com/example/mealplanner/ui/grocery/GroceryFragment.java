package com.example.mealplanner.ui.grocery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.DayListModel;
import com.example.mealplanner.Models.OneDayModel;
import com.example.mealplanner.Models.OneRecipeModel;
import com.example.mealplanner.R;
import com.example.mealplanner.SavedRecipes;
import com.example.mealplanner.databinding.FragmentGroceryBinding;
import com.example.mealplanner.ui.BottomSheet.BottomSheetFragment;
import com.example.mealplanner.ui.BottomSheet.RetrofitAPIcall;
import com.example.mealplanner.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroceryFragment extends Fragment {


    private RecyclerView groceryRV;
    private ProgressBar loadingPB;
    private ArrayList<IngredientsListModel> groceryArrayList;
    private GroceryListAdapter groceryListAdapter;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_grocery, container, false);

        groceryRV = view.findViewById(R.id.idGroceryRecyclerView);

        return view;

    }


    private void getAllIngredients(String recipeID){

        String apiKey = "6b9a01b6d89d42bf802f1b1d430ea450";
        String usedrecipeID = recipeID;
        String recipeURL = "https://api.spoonacular.com/recipes/" + recipeID + "/ingredientWidget.json?apiKey="+apiKey;
        String default_url = "https://api.spoonacular.com/recipes/random?apiKey="+apiKey;
        String defaultTest = "https://api.spoonacular.com/recipes/complexSearch?apiKey="+apiKey+"&query=&addRecipeInformation=true&instructionsRequired=true";
        String REAL_BASE = "https://api.spoonacular.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(REAL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPIcall retrofitAPIcall2 = retrofit.create(RetrofitAPIcall.class);
        Call<IngredientsListModel> call;
        if(recipeID.equals("")){
            call = retrofitAPIcall2.getRecipeIngredients(defaultTest);
        }
        else {
            call = retrofitAPIcall2.getRecipeIngredients(recipeURL);
        }

        call.enqueue(new Callback<IngredientsListModel>() {
            @Override
            public void onResponse(Call<IngredientsListModel> call, Response<IngredientsListModel> response) {
                IngredientsListModel ingredientsListModel = response.body();

                ArrayList<OneGroceryListItem> oneRecipes = ingredientsListModel.getIngredients();
                for (int i = 0; i < oneRecipes.size(); i++){
                    OneGroceryListItem temp = oneRecipes.get(i);

                    //add to list
//                    recipesArrayList.add(new OneRecipeModel(temp.getId(), temp.getTitle(), temp.getImage(), temp.getReadyInMinutes(), temp.getServings(), temp.getSummary(), temp.getInstructions()));
                }
            }



            @Override
            public void onFailure(Call<IngredientsListModel> call, Throwable t) {
                System.out.print("this is the fail pail");
                //Toast.makeText(BottomSheetFragment.this, "Failed to Get Recipies", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {

        private ArrayList<IngredientsListModel> ingredientsArrayList;
        private Context context;

        public GroceryListAdapter(ArrayList<IngredientsListModel> ingredientsArrayList, Context context) {
            this.ingredientsArrayList = ingredientsArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_grocery_item, parent, false);
            return new GroceryListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            IngredientsListModel oneIngredient = ingredientsArrayList.get(position);

            holder.groceryCardTitle.setText("yo");
            holder.groceryCardAmount.setText("yo");


        }

        @Override
        public int getItemCount() {
            return ingredientsArrayList.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            private TextView groceryCardTitle, groceryCardAmount;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                groceryCardTitle = itemView.findViewById(R.id.idGroceryCardIngredientTitle);
                groceryCardAmount = itemView.findViewById(R.id.idGroceryCardIngredientAmount);

            }

        }
    }

}