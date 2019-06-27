package com.sysu.ceres.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sysu.ceres.CeresConfig;
import com.sysu.ceres.R;
import com.sysu.ceres.http.Api;
import com.sysu.ceres.http.ApiMethods;
import com.sysu.ceres.model.Status;
import com.sysu.ceres.observer.MyObserver;
import com.sysu.ceres.observer.ObserverOnNextListener;

import static android.content.ContentValues.TAG;

public class EditUserActivity extends AppCompatActivity {
    TextView et_username;
    TextView et_money;
    EditText et_password;
    EditText et_cpassword;
    EditText et_email;
    EditText et_phone;
    Button ok_btn;
    Button logout_btn;
    private String current_user = null;

    private ObserverOnNextListener<Status> getUserlistener = new ObserverOnNextListener<Status>() {
        @Override
        public void onNext(Status status) {
            Log.d(TAG, "onNext: " + status.toString());
            if (status.getStatus().equals("success")) {
                CeresConfig.currentUser = status.getUser();
            } else {
                CeresConfig.currentUser = null;
            }
            finish();
        }
    };

    private ObserverOnNextListener<Status> listener = new ObserverOnNextListener<Status>() {
        @Override
        public void onNext(Status status) {
            Log.d(TAG, "onNext: " + status.toString());
            if (status.getStatus().equals("success")) {
                ApiMethods.getUser(new MyObserver<Status>(EditUserActivity.this, getUserlistener), current_user);
            } else {
                current_user = null;
                CeresConfig.currentUser = null;
                Toast.makeText(EditUserActivity.this,
                        status.getStatus(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    void refresh(){
        if (CeresConfig.currentUser != null) {
            et_username.setText("Username : " + CeresConfig.currentUser.getName());
            et_money.setText("Money : " + CeresConfig.currentUser.getMoney().toString());
            et_password.setText(CeresConfig.currentUser.getPassword());
            et_cpassword.setText(CeresConfig.currentUser.getPassword());
            et_phone.setText(CeresConfig.currentUser.getPhone());
            et_email.setText(CeresConfig.currentUser.getEmail());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        et_username = findViewById(R.id.edit_username);
        et_money = findViewById(R.id.edit_money);
        et_password = findViewById(R.id.edit_password);
        et_cpassword = findViewById(R.id.edit_confirm_psw);
        et_email = findViewById(R.id.edit_user_email);
        et_phone = findViewById(R.id.edit_user_phone);
        ok_btn = findViewById(R.id.edit_ok_btn);
        logout_btn = findViewById(R.id.edit_logout_btn);

        current_user = CeresConfig.currentUser.getName();

        refresh();

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CeresConfig.currentUser = null;
                finish();
            }
        });

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_cpassword.getText().toString().isEmpty()
                    || et_password.getText().toString().isEmpty()
                    || et_email.getText().toString().isEmpty()
                    || et_phone.getText().toString().isEmpty()) {
                    Toast.makeText(EditUserActivity.this,
                            "please fill all the blank.", Toast.LENGTH_SHORT).show();
                } else {
                    if (et_cpassword.getText().toString().equals(et_password.getText().toString())) {
                        int money = CeresConfig.currentUser.getMoney().intValue();
                        String psd = et_cpassword.getText().toString();
                        String phone = et_phone.getText().toString();
                        String email = et_email.getText().toString();
                        ApiMethods.updateUser(new MyObserver<Status>(EditUserActivity.this, listener), current_user, phone, email, psd, money);
                    } else {
                        Toast.makeText(EditUserActivity.this,
                                "Password mismatch.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
