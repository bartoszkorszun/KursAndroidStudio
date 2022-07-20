package com.example.carcheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carcheck.model.AutoData;

/*
    WINDOW TO ADD NEW CAR
 */

public class AddCarActivity extends AppCompatActivity {

    public static final String auto_data_new_car = "AUTO_DATA_NEW_CAR";
    public static final String is_set_as_default = "IS_SET_AS_DEFAULT";
    private EditText makeEditText;
    private EditText modelEditText;
    private EditText colorEditText;
    private Button confirmButton;
    private Switch defaultCarSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car_layout);

        makeEditText = findViewById(R.id.make_editText);
        modelEditText = findViewById(R.id.model_editText);
        colorEditText = findViewById(R.id.color_editText);

        defaultCarSwitch = findViewById(R.id.default_car_switch);
        confirmButton = findViewById(R.id.confirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AutoData autoData = new AutoData(makeEditText.getText().toString(), modelEditText.getText().toString(), colorEditText.getText().toString());
                Boolean isDefaultSet = defaultCarSwitch.isChecked();
                Intent intent = new Intent();
                intent.putExtra(auto_data_new_car, autoData);
                intent.putExtra(is_set_as_default, isDefaultSet);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
