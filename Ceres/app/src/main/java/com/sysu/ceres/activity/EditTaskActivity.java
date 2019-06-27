package com.sysu.ceres.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.sysu.ceres.model.Task;
import com.sysu.ceres.model.TaskList;
import com.sysu.ceres.observer.MyObserver;
import com.sysu.ceres.observer.ObserverOnNextListener;

import java.sql.Timestamp;
import java.util.List;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class EditTaskActivity extends AppCompatActivity implements OnDateSetListener {
    private static final String ARG_CURRENT_TASK = "current_task";
    private static final String ARG_SURVEY_TID = "task_tid";
    private Task current_task = null;
    EditText et_task_title;
    EditText et_task_detail;
    EditText et_task_money;
    EditText et_task_type;
    EditText et_task_total_num;
//    EditText et_task_end_time;
    Button btn_task_create;
    Button btn_task_edit;
    Button btn_pick_end_time;

    TimePickerDialog mDialogAll;

    int tid;
    long end_time;

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ObserverOnNextListener<Status> createSurveylistener = new ObserverOnNextListener<Status>() {
        @Override
        public void onNext(Status status) {
            Log.d(TAG, "onNext: " + status.toString());
            if (status.getStatus().equals("success")) {
                Intent intent = new Intent(EditTaskActivity.this, CreatSurveyActivity.class);
                intent.putExtra(ARG_SURVEY_TID, tid);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(EditTaskActivity.this,
                        status.getState(), Toast.LENGTH_SHORT).show();
            }
        }
    };


    private ObserverOnNextListener<TaskList> getNewTasklistener = new ObserverOnNextListener<TaskList>() {
        @Override
        public void onNext(TaskList tasklist) {
            Log.d(TAG, "onNext: " + tasklist.toString());
            List<Task> myTaskList = tasklist.getTaskList();
            tid = myTaskList.get(myTaskList.size() - 1).getTid().intValue();
            ApiMethods.createSurvey(new MyObserver<Status>(EditTaskActivity.this, createSurveylistener), tid);
        }
    };

    private ObserverOnNextListener<Status> listener = new ObserverOnNextListener<Status>() {
        @Override
        public void onNext(Status status) {
            Log.d(TAG, "onNext: " + status.toString());
            if (status.getState().equals("success")) {
                Toast.makeText(EditTaskActivity.this,
                        status.getState(), Toast.LENGTH_SHORT).show();
                Log.d("task type ", et_task_type.getText().toString());
                if (current_task == null && et_task_type.getText().toString().equals("survey")) { // 问卷
                    ApiMethods.getPublishTasks(new MyObserver<TaskList>(EditTaskActivity.this, getNewTasklistener),
                            CeresConfig.currentUser.getUid().intValue());
                } else {
                    finish();
                }
            } else {
                Toast.makeText(EditTaskActivity.this,
                        status.getState(), Toast.LENGTH_SHORT).show();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        et_task_title = findViewById(R.id.edit_task_title);
        et_task_detail = findViewById(R.id.edit_task_detail);
        et_task_money = findViewById(R.id.edit_task_money);
        et_task_type = findViewById(R.id.edit_task_type);
        et_task_total_num = findViewById(R.id.edit_task_total_num);
//        et_task_end_time = findViewById(R.id.edit_task_end_time);
        btn_task_create = findViewById(R.id.edit_task_create_btn);
        btn_task_edit = findViewById(R.id.edit_task_edit_btn);
        btn_pick_end_time = findViewById(R.id.timepicker_btn);

        initListener();
        current_task = (Task) getIntent().getSerializableExtra(ARG_CURRENT_TASK);
        if (current_task == null) {
            createTask();
        } else {
            editTask();
        }

        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("Cancel")
                .setSureStringId("Sure")
                .setTitleStringId("Choose End Time")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(end_time)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setType(Type.ALL)
                .build();
    }

    void initListener(){
        btn_task_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_task_title.getText().toString().isEmpty()
                || et_task_detail.getText().toString().isEmpty()
                || et_task_money.getText().toString().isEmpty()
                || et_task_type.getText().toString().isEmpty()
                || et_task_total_num.getText().toString().isEmpty()
                || end_time < System.currentTimeMillis()) {
                    Toast.makeText(EditTaskActivity.this,
                            "please fill all the blank.", Toast.LENGTH_SHORT).show();
                } else {
                    String title = et_task_title.getText().toString();
                    String detail = et_task_detail.getText().toString();
                    int money = Integer.parseInt(et_task_money.getText().toString());
                    String type = et_task_type.getText().toString();
                    int total_num = Integer.parseInt(et_task_total_num.getText().toString());
                    String e_time = getDateToString(end_time);
                    ApiMethods.createTask(new MyObserver<Status>(EditTaskActivity.this, listener),
                            CeresConfig.currentUser.getUid().intValue(), title, detail, money, type, total_num, e_time);
                }
            }
        });

        btn_task_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_task_title.getText().toString().isEmpty()
                        || et_task_detail.getText().toString().isEmpty()
                        || end_time < System.currentTimeMillis()) {
                    Toast.makeText(EditTaskActivity.this,
                            "please fill all the blank.", Toast.LENGTH_SHORT).show();
                } else {
                    String title = et_task_title.getText().toString();
                    String detail = et_task_detail.getText().toString();
                    String type = current_task.getType();
                    String e_time = getDateToString(end_time);
                    ApiMethods.updateTask(new MyObserver<Status>(EditTaskActivity.this, listener),
                            current_task.getTid().intValue(), CeresConfig.currentUser.getUid().intValue(), title, detail, type, e_time);
                }
            }
        });

        btn_pick_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogAll.show(getSupportFragmentManager(), "all");
            }
        });
    }

    void createTask() {
        et_task_title.setText("");
        et_task_detail.setText("");
        et_task_money.setText("");
        et_task_type.setText("");
        et_task_total_num.setText("");
//        et_task_end_time.setText("");
        end_time = System.currentTimeMillis();
        btn_pick_end_time.setText("End Time: " + getDateToString(end_time));
        btn_task_edit.setVisibility(View.GONE);
        btn_task_create.setVisibility(View.VISIBLE);
        et_task_money.setVisibility(View.VISIBLE);
        et_task_total_num.setVisibility(View.VISIBLE);
        et_task_type.setVisibility(View.VISIBLE);
    }

    void editTask(){
        et_task_title.setText(current_task.getTitle());
        et_task_detail.setText(current_task.getDetail());
        et_task_money.setText(current_task.getMoney().toString());
        et_task_type.setText(current_task.getType());
        et_task_total_num.setText(current_task.getTotalNum().toString());
        end_time = current_task.getEndTime();
//        et_task_end_time.setText(endtime.toString());
        btn_pick_end_time.setText("End Time: " + getDateToString(end_time));
        et_task_money.setVisibility(View.GONE);
        et_task_total_num.setVisibility(View.GONE);
        et_task_type.setVisibility(View.GONE);
        btn_task_edit.setVisibility(View.VISIBLE);
        btn_task_create.setVisibility(View.GONE);
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        end_time = millseconds;
        btn_pick_end_time.setText(getDateToString(end_time));
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
}
