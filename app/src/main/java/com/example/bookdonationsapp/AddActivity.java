package com.example.bookdonationsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    // objects for the UI
    EditText fullName, studentID, bookTitle, bookAuthor, numOfBooks;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // finding IDs of the UI elements
        fullName = findViewById(R.id.fullName_input);
        studentID = findViewById(R.id.studentID_input);
        bookTitle = findViewById(R.id.bookTitle_input);
        bookAuthor = findViewById(R.id.bookAuthor_input);
        numOfBooks = findViewById(R.id.numOfBooks_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // myDatabase helper object
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                // we call the add book method from myDatabaseHelper class
                if (fullName.length() <= 0 || studentID.length() <= 0 || bookTitle.length() <= 0 ||
                        bookAuthor.length() <= 0 || numOfBooks.length() <= 0) {
                    Toast.makeText(AddActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    myDB.addBook(
                            fullName.getText().toString().trim(),
                            studentID.getText().toString().trim(),
                            bookTitle.getText().toString().trim(),
                            bookAuthor.getText().toString().trim(),
                            Integer.valueOf(numOfBooks.getText().toString().trim())
                    );

                    // refresh the page
                    Intent intent = new Intent(AddActivity.this, AddActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


}
