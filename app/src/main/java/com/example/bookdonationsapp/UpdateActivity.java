package com.example.bookdonationsapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText fullName_input, studentID_input, bookTitle_input, bookAuthor_input, numOfBooks_input;
    Button update_button, delete_button;
    TextView txt_delete_icon;

    String id, new_fullName, new_studentID, new_bookTitle, new_bookAuthor, new_numOfBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // getting the ids of the form elements
        fullName_input = findViewById(R.id.fullName_input_update);
        studentID_input = findViewById(R.id.studentID_input_update);
        bookTitle_input = findViewById(R.id.bookTitle_input_update);
        bookAuthor_input = findViewById(R.id.bookAuthor_input_update);
        numOfBooks_input = findViewById(R.id.numOfBooks_input_update);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        txt_delete_icon = findViewById(R.id.txt_delete_icon);

        // calling the method to get and set intent data
        getAndSetIntentData();

        // dynamically set actionBar title after getAndSetData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(new_bookTitle);
        }

        // update button onClickListener
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmUpdateDialog();
            }
        });

        // delete icon onClickListener
        txt_delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

        // delete button onClickListener
        /*delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });*/


    }

    // getting and setting intent data
    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("fullName") &&
                getIntent().hasExtra("studentID") && getIntent().hasExtra("bookTitle") &&
                getIntent().hasExtra("bookAuthor") && getIntent().hasExtra("numOfBooks")) {
            // getting data from intent
            id = getIntent().getStringExtra("id");
            new_fullName = getIntent().getStringExtra("fullName");
            new_studentID = getIntent().getStringExtra("studentID");
            new_bookTitle = getIntent().getStringExtra("bookTitle");
            new_bookAuthor = getIntent().getStringExtra("bookAuthor");
            new_numOfBooks = getIntent().getStringExtra("numOfBooks");

            // setting intent data
            fullName_input.setText(new_fullName);
            studentID_input.setText(new_studentID);
            bookTitle_input.setText(new_bookTitle);
            bookAuthor_input.setText(new_bookAuthor);
            numOfBooks_input.setText(new_numOfBooks);
        } else {
            Toast.makeText(this, "No data found!", Toast.LENGTH_SHORT).show();
        }
    }

    // dialog to confirm a delete action
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning!");
        builder.setMessage("You are about to delete this book: " + new_bookTitle);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
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


    // dialog to confirm a update action
    void confirmUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update donation");
        builder.setMessage("Do you want to make changes to this donation?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // calling a method to update the data
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                new_fullName = fullName_input.getText().toString().trim();
                new_studentID = studentID_input.getText().toString().trim();
                new_bookTitle = bookTitle_input.getText().toString().trim();
                new_bookAuthor = bookAuthor_input.getText().toString().trim();
                new_numOfBooks = numOfBooks_input.getText().toString().trim();
                myDB.updateData(id, new_fullName, new_studentID, new_bookTitle, new_bookAuthor, new_numOfBooks);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}
