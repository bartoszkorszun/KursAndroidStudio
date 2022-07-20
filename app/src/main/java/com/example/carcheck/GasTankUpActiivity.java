package com.example.carcheck;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carcheck.model.AutoData;
import com.example.carcheck.model.TankUpRecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
    GAS TANK UP ACTIVITY
 */

public class GasTankUpActiivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private final String new_tank_up = "New Tank Up";
    private EditText dateEditText, mileageEditText, litersEditText, costEditText;
    private Button confirmButton;
    private AutoData autoData;
    private DateFormat dateFormat;
    private TextView mileageEditTextLabel;
    private TextView litersEditTextLabel;
    private TextView costEditTextLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gas_tank_up_layout);
        obtainExtras();
        viewInit();
        setTitle(new_tank_up);
    }

    private void obtainExtras() {
        autoData = (AutoData) getIntent().getExtras().getSerializable(MainMenuActivity.specialData);
    }

    private void viewInit() {
        dateEditText = findViewById(R.id.date);
        mileageEditText = findViewById(R.id.mileage);
        mileageEditTextLabel = findViewById(R.id.mileage_label);
        litersEditText = findViewById(R.id.liters);
        litersEditTextLabel = findViewById(R.id.liters_label);
        costEditText = findViewById(R.id.cost);
        costEditTextLabel = findViewById(R.id.cost_label);
        confirmButton = findViewById(R.id.confirm);

        dateEditText.setText(getCurrentDate());

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(GasTankUpActiivity.this, GasTankUpActiivity.this, year, month, day);

                datePickerDialog.show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateMileage()){
                    TankUpRecord tank = new TankUpRecord(getDateEditTextDate(), getMileageDouble(), getLitersDouble(), getCostDouble());
                    autoData.getTankUpRecord().add(tank);
                }
            }
        });

        mileageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false){
                    validateMileage();
                }
            }
        });

        costEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false){
                    validateCost();
                }
            }
        });

        litersEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false){
                    validateLiters();
                }
            }
        });
    }

    private boolean validateCost() {
        if (TextUtils.isEmpty(costEditText.getText())){
            costEditTextLabel.setText("Koszt musi zostać podany!");
            costEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }else if (Integer.valueOf(costEditText.getText().toString()) <= 0){
            costEditTextLabel.setText("Koszt musi być dodatni!");
            costEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }else{
            costEditTextLabel.setText(getResources().getString(R.string.cost));
            costEditTextLabel.setTextColor(getResources().getColor(R.color.white));
            return true;
        }
    }

    private boolean validateLiters() {
        if (TextUtils.isEmpty(litersEditText.getText())){
            litersEditTextLabel.setText("Litry muszą zostać podane!");
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }else if (Integer.valueOf(litersEditText.getText().toString()) <= 0){
            litersEditTextLabel.setText("Litry muszą być dodatnie!");
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }else{
            litersEditTextLabel.setText(getResources().getString(R.string.liters));
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.white));
            return true;
        }
    }

    private boolean validateMileage() {
        if (TextUtils.isEmpty(mileageEditText.getText())){
            mileageEditTextLabel.setText("Przebieg musi zostać podany!");
            mileageEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
        if (Integer.valueOf(mileageEditText.getText().toString()) <= 0){
            mileageEditTextLabel.setText("Przebieg musi być dodatni!");
            mileageEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
        int size = autoData.getTankUpRecord().size();
        if (size != 0){

            Double newMileage = Double.valueOf(mileageEditText.getText().toString());
            Double oldMileage = autoData.getTankUpRecord().get(size - 1).getMileage();

            if (newMileage <= oldMileage){
                mileageEditTextLabel.setText("Przebieg jest mniejszy lub równy niż poprzednio!");
                mileageEditTextLabel.setTextColor(getResources().getColor(R.color.red));
                return false;
            }else{
                mileageEditTextLabel.setText(getResources().getString(R.string.mileage));
                mileageEditTextLabel.setTextColor(getResources().getColor(R.color.white));
                return true;
            }
        }
        return true;
    }

    private Date getDateEditTextDate() {
        try {
            return dateFormat.parse(dateEditText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // TODO: 13.07.2022 REMEMBER TO ADD VALIDATION
        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return date;
    }

    // TODO: 13.07.2022 REMEMBER TO ADD VALIDATION
    private Double getCostDouble() {
        return Double.valueOf(costEditText.getText().toString());
    }

    // TODO: 13.07.2022 REMEMBER TO ADD VALIDATION
    private Double getLitersDouble() {
        return Double.valueOf(litersEditText.getText().toString());
    }

    // TODO: 13.07.2022 REMEMBER TO ADD VALIDATION
    private Double getMileageDouble() {
        return Double.valueOf(mileageEditText.getText().toString());
    }

    private String getCurrentDate(){

        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        Calendar calendar = new GregorianCalendar(year, month, day);
        dateEditText.setText(dateFormat.format(calendar.getTime()));
    }
}
