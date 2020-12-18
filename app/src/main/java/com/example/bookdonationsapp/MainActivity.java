package com.example.bookdonationsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageView;
    TextView empty_textView;

    MyDatabaseHelper myDB;
    ArrayList<String> donation_id, fullName, studentID, bookTitle, bookAuthor, numOfBooks;
    CustomAdapter customAdapter;

    String msg = "Android: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageView = findViewById(R.id.empty_imageView);
        empty_textView = findViewById(R.id.empty_textView);

        // setting on click listener for add button
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirecting to new activity on button click
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        // initializing the class and arrayList
        myDB = new MyDatabaseHelper(MainActivity.this);
        donation_id = new ArrayList<>();
        fullName = new ArrayList<>();
        studentID = new ArrayList<>();
        bookTitle = new ArrayList<>();
        bookAuthor = new ArrayList<>();
        numOfBooks = new ArrayList<>();

        // calling the store data in arrays method
        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, this, donation_id, fullName, studentID, bookTitle, bookAuthor, numOfBooks);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    // displaying data
    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            // setting this two elements to visible when the database or recyclerView is empty
            empty_imageView.setVisibility(View.VISIBLE);
            empty_textView.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                // reading all the data from the cursor
                donation_id.add(cursor.getString(0));
                fullName.add(cursor.getString(1));
                studentID.add(cursor.getString(2));
                bookTitle.add(cursor.getString(3));
                bookAuthor.add(cursor.getString(4));
                numOfBooks.add(cursor.getString(5));
            }
            // setting this two elements visibility to gone when the database or recyclerView is not empty
            empty_imageView.setVisibility(View.GONE);
            empty_textView.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Handles app bar item clicks

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        int id = item.getItemId();
        if (id == R.id.delete_all) {
            // Toast.makeText(this, "Delete all", Toast.LENGTH_SHORT).show();

            // calling confirm delete all function
            deleteAllConfirmDialog();

        } else if (id == R.id.logout) {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    // dialog to confirm delete all action
    void deleteAllConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all warning!");
        builder.setMessage("You are about to delete all the donations.");
        builder.setPositiveButton("Delete all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllDonations();
                // refreshing the main activity which contains all donations
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "Main activity started");
    }
}
