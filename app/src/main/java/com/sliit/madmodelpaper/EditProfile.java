package com.sliit.madmodelpaper;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class EditProfile extends AppCompatActivity {

    Button edit, delete, search;
    EditText username, password, dob;
    TextView id;
    RadioButton male, female;
    String ID, USERNAME, PASSWORD, DOB, GENDER;
    boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edit = findViewById(R.id.btn_edit);
        delete = findViewById(R.id.btn_delete);
        search = findViewById(R.id.btn_search);
        id = findViewById(R.id.textId2);
        username = findViewById(R.id.inputUsername2);
        password = findViewById(R.id.inputPassword2);
        dob = findViewById(R.id.inputDob2);
        male = findViewById(R.id.radioMale2);
        female = findViewById(R.id.radioFemale2);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHelper dbHelper = new DBHelper(getApplicationContext());
                List user = dbHelper.readAllInfor(username.getText().toString());

                if (user.isEmpty()){
                    Toast.makeText(EditProfile.this,"User not found!",Toast.LENGTH_SHORT).show();
                    clearText();
                }
                else {
                    id.setText(user.get(0).toString());
                    password.setText(user.get(2).toString());
                    dob.setText(user.get(3).toString());
                    if (user.get(4).toString().equals("Male")) {
                        male.setChecked(true);
                        female.setChecked(false);
                    }
                    else {
                        female.setChecked(true);
                        male.setChecked(false);
                    }
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHelper dbHelper = new DBHelper(getApplicationContext());

                ID = id.getText().toString();
                USERNAME = username.getText().toString();
                PASSWORD = password.getText().toString();
                DOB = dob.getText().toString();
                if (male.isChecked()) {
                    GENDER = "Male";
                }
                else {
                    GENDER = "Female";
                }

                result = dbHelper.updateInfor(ID,USERNAME,PASSWORD,DOB,GENDER);
                if (result == true) {
                    Toast.makeText(EditProfile.this,"Update Successfully!",Toast.LENGTH_SHORT).show();
                    clearText();
                }
                else {
                    Toast.makeText(EditProfile.this,"Update Failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHelper dbHelper = new DBHelper(getApplicationContext());

                result = dbHelper.deleteInfo(id.getText().toString());
                if (result == true) {
                    Toast.makeText(EditProfile.this,"Delete Successfully!",Toast.LENGTH_SHORT).show();
                    clearText();
                }
                else {
                    Toast.makeText(EditProfile.this, "Delete Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clearText(){
        id.setText(null);
        username.setText(null);
        password.setText(null);
        dob.setText(null);
        male.setChecked(false);
        female.setChecked(false);
    }
}