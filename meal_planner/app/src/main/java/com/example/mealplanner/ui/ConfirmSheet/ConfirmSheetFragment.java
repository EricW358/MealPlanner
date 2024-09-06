package com.example.mealplanner.ui.ConfirmSheet;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.example.mealplanner.DayListModel;
import com.example.mealplanner.Models.OneDayModel;
import com.example.mealplanner.R;
import com.example.mealplanner.SavedRecipes;
import com.example.mealplanner.Models.OneRecipeModel;
import com.example.mealplanner.ui.home.HomeFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ConfirmSheetFragment extends BottomSheetDialogFragment {

    private int oneRecipeID;
    private ImageView confirmImage;
    private TextView confirmTitle;
    private OneRecipeModel savedRecipe;
    private Button confirmButton;
    private RadioGroup radioGroup;
    private CalendarView calendarView;
    private int calSelectedDate;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_confirm_sheet_list_dialog, container, false);

        oneRecipeID = SavedRecipes.getNewestRecipe().getId();
        savedRecipe = SavedRecipes.getNewestRecipe();

        confirmTitle = view.findViewById(R.id.idConfirmRecipeTitle);
        confirmImage = view.findViewById(R.id.idConfirmRecipePic);
        confirmButton = view.findViewById(R.id.idConfirmSubmitButton);
        radioGroup = view.findViewById(R.id.idConfirmRadioGroup);
        calendarView = view.findViewById(R.id.idConfirmCalView);

        confirmTitle.setText(savedRecipe.getTitle());
        Picasso.get().load(savedRecipe.getImage()).into(confirmImage);

        SimpleDateFormat ogSetter = new SimpleDateFormat("yyyyMMdd");
        calSelectedDate = Integer.parseInt(ogSetter.format(new Date()));
        calSelectedDate -= 100;

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calSelectedDate = year * 10000 + month * 100 + dayOfMonth;
            }
        });



        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedRecipes.addSavedRecipes(savedRecipe);

                int checkedRadioID = radioGroup.getCheckedRadioButtonId();
                //calSelectedDate = new Date(calendarView.getDate());

                if (!DayListModel.getAllDays().containsKey(calSelectedDate)) {
                    DayListModel.addOneDay(new OneDayModel(calSelectedDate));
                }

                if (checkedRadioID == R.id.idConfirmRadioBreakfast) {
                    DayListModel.addBreakfast(calSelectedDate, oneRecipeID);
                }
                else if (checkedRadioID == R.id.idConfirmRadioLunch) {
                    DayListModel.addLunch(calSelectedDate, oneRecipeID);

                }
                else if (checkedRadioID == R.id.idConfirmRadioDinner) {
                    DayListModel.addDinner(calSelectedDate, oneRecipeID);
                }


                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_home);
                HomeFragment.notifyChanges();
            }
        });

        return view;

    }

}