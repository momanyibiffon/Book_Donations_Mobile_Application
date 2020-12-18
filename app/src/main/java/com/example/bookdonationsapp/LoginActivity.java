package com.example.bookdonationsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    EditText mTextUsername, mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;

    MyDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new MyDatabaseHelper(this);

        mTextUsername = (EditText) findViewById(R.id.editText_username);
        mTextPassword = (EditText) findViewById(R.id.editText_password);
        mButtonLogin = (Button) findViewById(R.id.button_login);
        mTextViewRegister = (TextView) findViewById(R.id.textView_register);

        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();

                if (user.length() <= 0 || pwd.length() <= 0) {
                    Toast.makeText(LoginActivity.this, "All filed are required", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean res = db.checkUser(user, pwd);
                    if (res == true) {
                        Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                        Intent moveToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(moveToMainActivity);
                    } else {
                        Toast.makeText(LoginActivity.this, "User account not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
