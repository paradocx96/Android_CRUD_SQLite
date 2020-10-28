package com.sliit.madmodelpaper;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class ProfileManagement extends AppCompatActivity {

    Button updateProfile;
    EditText username, dob, password;
    RadioButton male, female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        updateProfile = findViewById(R.id.btn_updateProfile);
        username = findViewById(R.id.inputUsername);
        dob = findViewById(R.id.inputDob);
        password = findViewById(R.id.inputPassword);
        male = findViewById(R.id.radioMale);
        female = findViewById(R.id.radioFemale);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHelper dbHelper = new DBHelper(getApplicationContext());

                String USERNAME = username.getText().toString();
                String DOB = dob.getText().toString();
                String PASSWORD = password.getText().toString();
                String GENDER;

                if (male.isChecked()) { GENDER = "Male"; }
                else { GENDER = "Female"; }

                long result = dbHelper.addInfo(USERNAME,PASSWORD,DOB,GENDER);

                if (result > 0) {
                    Toast.makeText(ProfileManagement.this,"Successfully Added!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ProfileManagement.this,"Operation Failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}