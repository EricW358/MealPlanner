package com.example.mealplanner.ui.Saved;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.SavedRecipes;
import com.example.mealplanner.Models.OneRecipeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SavedRecipesFragment extends Fragment {

    private RecyclerView savedRV;
    private ArrayList<OneRecipeModel> savedArrayList;
    private SavedListAdapter savedListAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_saved_recipes, container, false);

        savedRV = view.findViewById(R.id.idSavedRecyclerView);
        savedArrayList = SavedRecipes.getArraySavedRecipes();
        savedListAdapter = new SavedListAdapter(savedArrayList, this.getContext());
        savedRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        savedRV.setAdapter(savedListAdapter);

        return view;
    }

    private class SavedListAdapter extends RecyclerView.Adapter<SavedListAdapter.ViewHolder> {

        private ArrayList<OneRecipeModel> recipesArrayList;
        private Context context;

        public SavedListAdapter(ArrayList<OneRecipeModel> recipesArrayList, Context context) {
            this.recipesArrayList = recipesArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_bottom_sheet_list_dialog_item, parent, false);
            return new SavedListAdapter.ViewHolder(view);

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