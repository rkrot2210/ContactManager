package com.rk.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewContactActivity extends AppCompatActivity {
    DatabaseHandler databaseHandler =  new DatabaseHandler(this);

    TextView labelView;
    EditText name;
    EditText surname;
    EditText phone;
    EditText email;

    Button btnCansel;
    Button btnSave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_contact);



        name = findViewById(R.id.contactName);
        surname = findViewById(R.id.contactSurname);
        phone = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        labelView = findViewById(R.id.contactLabel);

        btnCansel = findViewById(R.id.btnCansel);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setEnabled(false);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0)
                {
                    btnSave.setEnabled(true);
                }
                else {
                    btnSave.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(count > 0){
                labelView.setText(name.getText());
                btnSave.setEnabled(true);
            }
            else {
                labelView.setText("New Contact");
                btnSave.setEnabled(false);
            }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnCansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewContactActivity.this,MainActivity.class));
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseHandler.insertContactDB(
                        new Contact (name.getText().toString(),surname.getText().toString(),
                                phone.getText().toString(),email.getText().toString()));
                startActivity(new Intent(NewContactActivity.this,MainActivity.class));
                finish();
            }
        });
    }

}
