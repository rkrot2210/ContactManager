package com.rk.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    DatabaseHandler databaseHandler =  new DatabaseHandler(this);
    Contact selectContact;
    TextView labelView;
    EditText name;
    EditText surname;
    EditText phone;
    EditText email;

    Button btnDelete;
    Button btnUpdate;
    Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);
        Bundle arguments = getIntent().getExtras();


        name = findViewById(R.id.contactName);
        surname = findViewById(R.id.contactSurname);
        phone = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        labelView = findViewById(R.id.contactLabel);

        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btCancel);
        btnUpdate.setEnabled(false);

        selectContact = new Contact(arguments.get("name").toString(),
                arguments.get("surname").toString(),arguments.get("phone").toString(),arguments.get("email").toString());

        name.setText(selectContact.getName());
        surname.setText(selectContact.getSurname());
        phone.setText(selectContact.getPhoneNumber());
        email.setText(selectContact.getEmail());

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
                    btnUpdate.setEnabled(true);
                }
                else {
                    btnUpdate.setEnabled(false);
                }

                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(count > 0){
                    labelView.setText(name.getText());
                    btnUpdate.setEnabled(true);
                }
                else {
                    labelView.setText("Unknown");
                    btnUpdate.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(count > 0)
            {
                btnUpdate.setEnabled(true);
            }
            else {
                btnUpdate.setEnabled(false);
            }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.deleteContact(selectContact);
                startActivity(new Intent(UpdateActivity.this,MainActivity.class));
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateActivity.this,MainActivity.class));
                finish();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseHandler.updateContact(selectContact.getPhoneNumber(),
                        phone.getText().toString(),name.getText().toString(),
                        surname.getText().toString(),email.getText().toString());
                startActivity(new Intent(UpdateActivity.this,MainActivity.class));
                finish();
            }
        });
    }

}
