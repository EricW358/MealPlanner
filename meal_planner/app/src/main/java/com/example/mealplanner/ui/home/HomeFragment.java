package com.example.mealplanner.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.DayListModel;
import com.example.mealplanner.Models.OneDayModel;
import com.example.mealplanner.R;
import com.example.mealplanner.SavedRecipes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private TextView noShowText;
    private TextView homeTitleText;
    private RecyclerView dayRV;
    private ArrayList<OneDayModel> daysAllArrayList;
    private static DayListAdapter dayListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dayRV = view.findViewById(R.id.idHomeDayList);
        daysAllArrayList = DayListModel.getSortedList();
        dayListAdapter = new DayListAdapter(daysAllArrayList, this.getContext());
        dayRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        dayRV.setAdapter(dayListAdapter);
        dayListAdapter.notifyDataSetChanged();

        noShowText = view.findViewById(R.id.idHomeNoneText);
        if (daysAllArrayList.isEmpty()) {
            noShowText.setText("You have not added any meals yet. Click the plus button to add meals.");
        }
        else {
            noShowText.setText("");
        }

        homeTitleText = view.findViewById(R.id.idHomeTitle);
        loadName();

        return view;

    }

    private void loadName() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());

        String returnedName = sp.getString("signature", "");
        if (returnedName.equals("")) {
            homeTitleText.setText("Your Meal Planner");
        }
        else {
            homeTitleText.setText(returnedName + "'s Meal Planner");
        }
    }

    public static void notifyChanges() {
        dayListAdapter.notifyDataSetChanged();
    }

    private class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.ViewHolder> {

        private ArrayList<OneDayModel> daysArrayList;
        private Context context;

        public DayListAdapter(ArrayList<OneDayModel> daysArrayList, Context context) {
            this.daysArrayList = daysArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_item, parent, false);
            return new DayListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            OneDayModel oneDay = daysArrayList.get(position);

            holder.cardTitle.setText(oneDay.getFormattedDate());

            if (oneDay.getBreakfast() != 0) {
                holder.breakfastText.setText(SavedRecipes.getSavedRecipes().get(oneDay.getBreakfast()).getTitle());
                Picasso.get().load(SavedRecipes.getSavedRecipes().get(oneDay.getBreakfast()).getImage()).into(holder.breakfastImg);
            }
            if (oneDay.getLunch() != 0) {
                holder.lunchText.setText(SavedRecipes.getSavedRecipes().get(oneDay.getLunch()).getTitle());
                Picasso.get().load(SavedRecipes.getSavedRecipes().get(oneDay.getLunch()).getImage()).into(holder.lunchImg);
            }
            if (oneDay.getDinner() != 0) {
                holder.dinnerText.setText(SavedRecipes.getSavedRecipes().get(oneDay.getDinner()).getTitle());
                Picasso.get().load(SavedRecipes.getSavedRecipes().get(oneDay.getDinner()).getImage()).into(holder.dinnerImg);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DayListModel.setLastestClick(oneDay);


                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                    navController.navigate(R.id.detailFragment);
                }
            });

        }

        @Override
        public int getItemCount() {
            return daysArrayList.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            private TextView cardTitle, breakfastText, lunchText, dinnerText;
            private ImageView breakfastImg, lunchImg, dinnerImg;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                cardTitle = itemView.findViewById(R.id.idHomeCardTitle);
                breakfastText = itemView.findViewById(R.id.idHomeCardBreakfastText);
                lunchText = itemView.findViewById(R.id.idHomeCardLunchText);
                dinnerText = itemView.findViewById(R.id.idHomeCardDinnerText);
                breakfastImg = itemView.findViewById(R.id.idHomeCardBreakfastImg);
                lunchImg = itemView.findViewById(R.id.idHomeCardLunchImg);
                dinnerImg = itemView.findViewById(R.id.idHomeCardDinnerImg);
            }

        }
    }
}