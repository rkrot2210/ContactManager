package com.rk.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler = new DatabaseHandler(this);

    private static List<Contact> contacts = new ArrayList();
    ListView phoneBook;

    Button btnRefresh;
    Button btnAddContact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInitialData();


        phoneBook =  findViewById(R.id.countriesList);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnAddContact = findViewById(R.id.btnNew);


        ContactAdapter stateAdapter = new ContactAdapter(this, R.layout.layout_item, contacts);
        phoneBook.setAdapter(stateAdapter);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                final Contact selectedContact = (Contact) parent.getItemAtPosition(position);
                Intent intent = new Intent("SHOW_UPDATE_ACTIVITY");
                intent.putExtra("name", selectedContact.getName());
                intent.putExtra("surname", selectedContact.getSurname());
                intent.putExtra("phone", selectedContact.getPhoneNumber());
                intent.putExtra("email", selectedContact.getEmail());
                contacts.clear();
                startActivity(intent);
                finish();
            }
        };
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("SHOW_SECOND_ACTIVITY");
                contacts.clear();
                startActivity(intent);
                finish();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.deleteDB();
                contacts.clear();
                databaseHandler.onCreate(databaseHandler.sqLiteDatabase);
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                finish();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        phoneBook.setOnItemClickListener(itemListener);
    }
    private void setInitialData(){

        Cursor dataDB = databaseHandler.getTableData();
        while (dataDB.moveToNext())
        {
            contacts.add(new Contact(dataDB.getString(1),dataDB.getString(2),dataDB.getString(0),dataDB.getString(3)));
        }



    }
}
