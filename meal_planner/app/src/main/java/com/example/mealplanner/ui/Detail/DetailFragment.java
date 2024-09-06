package com.example.mealplanner.ui.Detail;

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

import com.example.mealplanner.DayListModel;
import com.example.mealplanner.R;
import com.example.mealplanner.SavedRecipes;
import com.example.mealplanner.Models.OneRecipeModel;
import com.example.mealplanner.Models.StepsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class DetailFragment extends Fragment {


    private FloatingActionButton backButton;
    private TabLayout tabLayout;
    private ArrayList<StepsModel> stepsModelArrayList;
    private RecyclerView stepsRV;
    private int selectedTab;
    private DetailListAdapter detailListAdapter;
    private ImageView recipeImg;
    private OneRecipeModel recipeModel;

    public DetailFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);


        backButton = view.findViewById(R.id.idDetailBackButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_home);
            }
        });



        tabLayout = view.findViewById(R.id.idDetailTabLayout);
        selectedTab = tabLayout.getSelectedTabPosition();
        stepsRV = view.findViewById(R.id.idDetailRecyclerView);
        recipeImg = view.findViewById(R.id.idDetailImage);
        stepsModelArrayList = new ArrayList<StepsModel>();


        if (DayListModel.getLatestBreakfast() != 0) {
            stepsModelArrayList.clear();
            recipeModel = SavedRecipes.getSpecificRecipe(DayListModel.getLatestBreakfast());
            stepsModelArrayList = recipeModel.getInstructionSteps();
            Picasso.get().load(recipeModel.getImage()).into(recipeImg);
        }
        else {
            stepsModelArrayList.clear();
            recipeImg.setImageResource(R.drawable.not_available);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchTabRefresh();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        detailListAdapter = new DetailListAdapter(stepsModelArrayList, this.getContext());
        stepsRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        stepsRV.setAdapter(detailListAdapter);
        detailListAdapter.notifyDataSetChanged();

        return view;
    }

    private void switchTabRefresh() {
        selectedTab = tabLayout.getSelectedTabPosition();
        if (selectedTab == 0 && DayListModel.getLatestBreakfast() != 0) {
            stepsModelArrayList.clear();
            recipeModel = SavedRecipes.getSpecificRecipe(DayListModel.getLatestBreakfast());
            stepsModelArrayList.addAll(recipeModel.getInstructionSteps());
            Picasso.get().load(recipeModel.getImage()).into(recipeImg);
            detailListAdapter.notifyDataSetChanged();

        }
        else if (selectedTab == 1 && DayListModel.getLatestLunch() != 0) {
            stepsModelArrayList.clear();
            recipeModel = SavedRecipes.getSpecificRecipe(DayListModel.getLatestLunch());
            stepsModelArrayList.addAll(recipeModel.getInstructionSteps());
            Picasso.get().load(recipeModel.getImage()).into(recipeImg);
            detailListAdapter.notifyDataSetChanged();

        }
        else if (DayListModel.getLatestDinner() != 0) {
            stepsModelArrayList.clear();
            recipeModel = SavedRecipes.getSpecificRecipe(DayListModel.getLatestDinner());
            stepsModelArrayList.addAll(recipeModel.getInstructionSteps());
            Picasso.get().load(recipeModel.getImage()).into(recipeImg);
            detailListAdapter.notifyDataSetChanged();

        }
        else {
            stepsModelArrayList.clear();
            recipeImg.setImageResource(R.drawable.not_available);
        }
    }

    private class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.ViewHolder> {

        private ArrayList<StepsModel> stepsArray;
        private Context context;


        public DetailListAdapter(ArrayList<StepsModel> stepsArray, Context context) {
            this.stepsArray = stepsArray;
            this.context = context;
        }

        @NonNull
        @Override
        public DetailListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_detail_item, parent, false);
            return new DetailFragment.DetailListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DetailListAdapter.ViewHolder holder, int position) {
            StepsModel stepsModel = stepsArray.get(position);

            holder.number.setText("Step: " + stepsModel.getNumber());
            holder.instruction.setText(stepsModel.getStep());
        }

        @Override
        public int getItemCount() {
            return stepsArray.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            private TextView number;
            private TextView instruction;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                number = itemView.findViewById(R.id.idDetailCardNumber);
                instruction = itemView.findViewById(R.id.idDetailCardInstruction);
            }
        }


    }
}