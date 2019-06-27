package com.sysu.ceres.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sysu.ceres.CeresConfig;
import com.sysu.ceres.R;
import com.sysu.ceres.http.ApiMethods;
import com.sysu.ceres.model.Status;
import com.sysu.ceres.observer.MyObserver;
import com.sysu.ceres.observer.ObserverOnNextListener;

import static android.content.ContentValues.TAG;

public class CreateMessageActivity extends AppCompatActivity {
    private static final String ARG_TASK_ID = "tid";
    private ObserverOnNextListener<Status> createMessagelistener = new ObserverOnNextListener<Status>() {
        @Override
        public void onNext(Status status) {
            Log.d(TAG, "onNext: " + status.toString());
            if (status.getStatus().equals("success")) {
                finish();
            } else {
                Toast.makeText(CreateMessageActivity.this,
                        status.getStatus(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
        final int tid = getIntent().getIntExtra(ARG_TASK_ID, 1);
        final EditText message_detail = findViewById(R.id.create_message_edittext);
        final Button create_message_btn = findViewById(R.id.create_message_btn);
        create_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiMethods.createMessage(new MyObserver<Status>(CreateMessageActivity.this, createMessagelistener),
                        tid, CeresConfig.currentUser.getUid().intValue(), message_detail.getText().toString());
            }
        });
    }
}
