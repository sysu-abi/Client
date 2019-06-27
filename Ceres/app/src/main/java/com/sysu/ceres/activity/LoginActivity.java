package com.sysu.ceres.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sysu.ceres.CeresConfig;
import com.sysu.ceres.R;
import com.sysu.ceres.http.ApiMethods;
import com.sysu.ceres.model.Status;
import com.sysu.ceres.model.Task;
import com.sysu.ceres.model.TaskList;
import com.sysu.ceres.observer.MyObserver;
import com.sysu.ceres.observer.ObserverOnNextListener;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    private boolean login_or_register = true;
    private String current_user = null;
    private static final String ARG_USERNAME = "username";
    private ObserverOnNextListener<Status> getUserlistener = new ObserverOnNextListener<Status>() {
        @Override
        public void onNext(Status status) {
            Log.d(TAG, "onNext: " + status.toString());
            if (status.getStatus().toString().equals("success")) {
                CeresConfig.currentUser = status.getUser();
            } else {
                CeresConfig.currentUser = null;
            }
            setResult(2);
            finish();
        }
    };

    private ObserverOnNextListener<Status> listener = new ObserverOnNextListener<Status>() {
        @Override
        public void onNext(Status status) {
            Log.d(TAG, "onNext: " + status.toString());
            if (status.getStatus().toString().equals("success")) {
                current_user = ((TextView)findViewById(R.id.username)).getText().toString();
                ApiMethods.getUser(new MyObserver<Status>(LoginActivity.this, getUserlistener), current_user);
                //Intent intent = new Intent();
                // 获取用户计算后的结果
                //intent.putExtra(ARG_USERNAME, current_user);
                //通过intent对象返回结果，必须要调用一个setResult方法，
                //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
            } else {
                current_user = null;
                CeresConfig.currentUser = null;
                Toast.makeText(LoginActivity.this,
                        status.getStatus(), Toast.LENGTH_SHORT).show();
            }
        }
    };

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
                    String name = username.getText().toString();
                    String psd = password.getText().toString();
                    String c_psd = confirm_psw.getText().toString();
                    String phone = userphone.getText().toString();
                    String email = useremail.getText().toString();
                    if (login_or_register) { //login
                        ApiMethods.userLogin(new MyObserver<Status>(LoginActivity.this, listener), name, psd);
                    } else { //register
                        if (c_psd.equals(psd)) {
                            ApiMethods.userRegist(new MyObserver<Status>(LoginActivity.this, listener), name, phone, email, psd);
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Password mismatch.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}