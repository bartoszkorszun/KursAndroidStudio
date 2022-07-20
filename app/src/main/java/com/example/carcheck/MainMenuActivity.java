package com.example.carcheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carcheck.model.AutoData;

import java.util.ArrayList;

/*
    MAIN MENU
 */

public class MainMenuActivity extends AppCompatActivity {

    public static final String specialData = "SPECIAL_DATA";
    private Button goToTankFormButton;
    private Button goToNewCarButton;
    private Spinner carChooseSpinner;
    private ArrayList<AutoData> carList;
    private ArrayAdapter<AutoData> arrayAdapter;
    private int requestCode = 12345;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu_layout);

        initViews();
    }

    private void initViews() {
        goToTankFormButton = findViewById(R.id.go_to_tank_form_button);
        goToNewCarButton = findViewById(R.id.add_new_car_button);
        carChooseSpinner = findViewById(R.id.car_choose_spinner);

        initCarList();
        initArrayAdapter();
        carChooseSpinner.setAdapter(arrayAdapter);

        goToTankFormButton.setOnClickListener(goToTankUpActivity());
        goToNewCarButton.setOnClickListener(goToNewCarActivity());
    }

    private View.OnClickListener goToNewCarActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this, AddCarActivity.class);
                startActivityForResult(intent, requestCode);
            }
        };
    }

    @NonNull
    private View.OnClickListener goToTankUpActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this, GasTankUpActiivity.class);
                intent.putExtra(specialData, getCurrentCar());
                startActivity(intent);
            }
        };
    }

    private void initArrayAdapter() {
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, carList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void initCarList() {
        carList = new ArrayList<AutoData>();
        carList.add(new AutoData("Skoda", "Kodiaq", "Black Metalic"));
        carList.add(new AutoData("Volkswagen", "Golf", "Yellow Matte"));
    }

    @NonNull
    private AutoData getCurrentCar() {
        return (AutoData) carChooseSpinner.getSelectedItem();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    AutoData newAutoData = (AutoData) data.getExtras().get(AddCarActivity.auto_data_new_car);
                    Boolean isNewCarDefault = (Boolean) data.getExtras().get(AddCarActivity.is_set_as_default);
                    if (isNewCarDefault != null && isNewCarDefault) {
                        carList.add(0, newAutoData);
                    } else {
                        carList.add(newAutoData);
                    }
                }
            }
        }
    }
}
