package com.sysu.ceres.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sysu.ceres.R;

public class LoginActivity extends AppCompatActivity {

    private boolean login_or_register = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initListners();
    }

    void initListners(){
        final EditText username = findViewById(R.id.username);
        final EditText confirm_psw = findViewById(R.id.confirm_psw);
        final EditText password = findViewById(R.id.password);
        final EditText userphone = findViewById(R.id.user_phone);
        final EditText useremail = findViewById(R.id.user_email);
        final Button ok_btn = findViewById(R.id.ok_btn);
        final Button clear_btn = findViewById(R.id.clear_btn);
        final RadioGroup radio_group = findViewById(R.id.radio_group);

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_login){
                    Log.d("login ", "");
                    login_or_register = true;
                    password.setHint("Password");
                    confirm_psw.setVisibility(View.GONE);
                    userphone.setVisibility(View.GONE);
                    useremail.setVisibility(View.GONE);
                } else if (checkedId == R.id.radio_register) {
                    Log.d("rigister ", "");
                    login_or_register = false;
                    password.setHint("New Password");
                    confirm_psw.setVisibility(View.VISIBLE);
                    userphone.setVisibility(View.VISIBLE);
                    useremail.setVisibility(View.VISIBLE);
                }
            }
        });

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                confirm_psw.setText("");
                password.setText("");
                useremail.setText("");
                userphone.setText("");
            }
        });

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                            "Username cannot be empty.", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                            "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                } else {
                    if (login_or_register) { //login

                    } else { //register

                    }
                }
            }
        });
    }
}
