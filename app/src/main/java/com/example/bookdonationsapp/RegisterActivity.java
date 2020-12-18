package com.example.bookdonationsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    MyDatabaseHelper db;

    EditText mTextUsername, mTextPassword, mTextConfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new MyDatabaseHelper(this);

        mTextUsername = (EditText) findViewById(R.id.editText_username);
        mTextPassword = (EditText) findViewById(R.id.editText_password);
        mTextConfPassword = (EditText) findViewById(R.id.editText_conf_password);
        mButtonRegister = (Button) findViewById(R.id.button_register);
        mTextViewLogin = (TextView) findViewById(R.id.textView_login);

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String conf_pwd = mTextConfPassword.getText().toString().trim();

                if (user.length() <= 0 || pwd.length() <= 0 || conf_pwd.length() <= 0) {
                    Toast.makeText(RegisterActivity.this, "All filed are required", Toast.LENGTH_SHORT).show();
                } else {
                    if (pwd.equals(conf_pwd)) {
                        long val = db.addUser(user, pwd);
                        if (val > 0) {
                            Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(moveToLogin);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration error!", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(RegisterActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
