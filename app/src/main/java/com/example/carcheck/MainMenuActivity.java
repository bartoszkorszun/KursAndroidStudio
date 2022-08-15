package com.example.carcheck;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcheck.historyList.HistoryAdapter;
import com.example.carcheck.model.AutoData;
import com.example.carcheck.model.TankUpRecord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/*
    MAIN MENU
 */

public class MainMenuActivity extends AppCompatActivity {

    public static final String specialData = "SPECIAL_DATA";
    private final String autoPref = "Auto pref";
    private int newCarRequestCode = 12345;
    private int tankRequestCode = 98765;

    private Button goToTankFormButton;
    private Button goToNewCarButton;
    private Spinner carChooseSpinner;

    private ArrayList<AutoData> carList;
    private ArrayAdapter<AutoData> spinnerAdapter;
    private RecyclerView historyRecycleView;
    private RecyclerView.Adapter historyAdapter;
    private RecyclerView.LayoutManager historyLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);
        initViews();

        // TODO: 16.08.2022 zrobić do tego najlepiej jakiś button
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10293);
        }else {
            Intent intent = new Intent(this, GpsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this, GpsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(autoPref, gson.toJson(carList));
        editor.apply();
    }

    private void initViews() {
        goToTankFormButton = findViewById(R.id.go_to_tank_form_button);
        goToNewCarButton = findViewById(R.id.add_new_car_button);
        carChooseSpinner = findViewById(R.id.car_choose_spinner);
        historyRecycleView = (RecyclerView) findViewById(R.id.history_recycle_view);

        initCarList();
        initArrayAdapter();
        carChooseSpinner.setAdapter(spinnerAdapter);
        initRecyclerView();

        goToTankFormButton.setOnClickListener(goToTankUpActivity());
        goToNewCarButton.setOnClickListener(goToNewCarActivity());
        carChooseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initRecyclerView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (carList.isEmpty()) {

            Intent intent = new Intent(MainMenuActivity.this, AddCarActivity.class);
            startActivityForResult(intent, newCarRequestCode);
        }
    }

    private void initRecyclerView() {
        historyLayoutManager = new LinearLayoutManager(this);
        historyRecycleView.setLayoutManager(historyLayoutManager);

        historyRecycleView.setHasFixedSize(true);

        historyAdapter = new HistoryAdapter(this, getCurrentCar() != null ? getCurrentCar().getTankUpRecord() : new ArrayList<TankUpRecord>());
        historyRecycleView.setAdapter(historyAdapter);
    }

    private View.OnClickListener goToNewCarActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this, AddCarActivity.class);
                startActivityForResult(intent, newCarRequestCode);
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
                startActivityForResult(intent, tankRequestCode);
            }
        };
    }

    private void initArrayAdapter() {
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, carList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void initCarList() {

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String string = sharedPreferences.getString(autoPref, null);
        Gson gson = new Gson();
        ArrayList<AutoData> newAutoList = gson.fromJson(string, new TypeToken<ArrayList<AutoData>>() {
        }.getType());

        if (newAutoList != null) {
            carList = newAutoList;
        } else {
            carList = new ArrayList<>();
        }
    }

    @NonNull
    private AutoData getCurrentCar() {

        return (AutoData) carChooseSpinner.getSelectedItem();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.newCarRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    AutoData newAutoData = (AutoData) data.getExtras().get(AddCarActivity.auto_data_new_car);
                    Boolean isNewCarDefault = (Boolean) data.getExtras().get(AddCarActivity.is_set_as_default);
                    if (isNewCarDefault != null && isNewCarDefault) {
                        carList.add(0, newAutoData);
                        carChooseSpinner.setAdapter(spinnerAdapter);
                        carChooseSpinner.setSelection(0, false);
                    } else {
                        carList.add(newAutoData);
                        carChooseSpinner.setAdapter(spinnerAdapter);
                        carChooseSpinner.setSelection(carList.size() - 1, false);
                    }
                }
            }
        }
        if (requestCode == this.tankRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    TankUpRecord newTankRecord = (TankUpRecord) data.getExtras().get(GasTankUpActiivity.newTankUpRecord);
                    if (newTankRecord != null) {
                        getCurrentCar().getTankUpRecord().add(0, newTankRecord);
                        historyAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }
}
