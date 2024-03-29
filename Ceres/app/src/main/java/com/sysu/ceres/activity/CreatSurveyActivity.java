package com.sysu.ceres.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sysu.ceres.R;
import com.sysu.ceres.http.ApiMethods;
import com.sysu.ceres.model.Status;
import com.sysu.ceres.model.Survey;
import com.sysu.ceres.model.SurveyList;
import com.sysu.ceres.model.Task;
import com.sysu.ceres.model.TaskList;
import com.sysu.ceres.observer.MyObserver;
import com.sysu.ceres.observer.ObserverOnNextListener;

import java.util.List;

import static android.content.ContentValues.TAG;

public class CreatSurveyActivity extends AppCompatActivity {

    //Todo
    int tid;
    private static final String ARG_SURVEY_TID = "task_tid";

    private int sid;
    private int qid;
    TextView question_id;
    EditText question_content;
    EditText choice_a;
    EditText choice_b;
    EditText choice_c;
    EditText choice_d;
    Button next_btn;
    Button finish_btn;

    ObserverOnNextListener<Status> status_listener = new ObserverOnNextListener<Status>() {
        @Override
        public void onNext(Status status) {
            Log.d(TAG, "onNext: " + status.getStatus());
            if (status.getStatus() == "success") {
                Log.d(TAG, "status success");
            }
        }
    };

    ObserverOnNextListener<SurveyList> surveyList_listener = new ObserverOnNextListener<SurveyList>() {
        @Override
        public void onNext(SurveyList surveyList) {
            Log.d(TAG, "onNext: " + surveyList.getStatus());
            Log.d("surveyList ", surveyList.toString());
            sid = surveyList.getSurvey().get(0).getSid().intValue();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_survey);
        tid = getIntent().getIntExtra(ARG_SURVEY_TID, -1);
        if(tid == -1) {
            Log.d("no tid ", "-1");
        }
        ApiMethods.getSurveyList(new MyObserver<SurveyList>(CreatSurveyActivity.this, surveyList_listener), tid);
        init();
        clickHandle();
    }

    void init() {
        question_id = findViewById(R.id.create_qid);
        question_content = findViewById(R.id.question_content);
        choice_a = findViewById(R.id.choice_a);
        choice_b = findViewById(R.id.choice_b);
        choice_c = findViewById(R.id.choice_c);
        choice_d = findViewById(R.id.choice_d);
        next_btn = findViewById(R.id.create_ques_next_btn);
        finish_btn = findViewById(R.id.create_ques_finish_btn);

        qid = 1;
        question_id.setText("Question 1.");
        question_content.setText("");
        question_content.setHint("Question...");
        choice_a.setText("");
        choice_a.setHint("Choice A");
        choice_b.setText("");
        choice_b.setHint("Choice B");
        choice_c.setText("");
        choice_c.setHint("Choice C");
        choice_d.setText("");
        choice_d.setHint("Choice D");
    }

    void clickHandle() {

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuestion();
                clear();
                String qid_string = String.valueOf(qid);
                question_id.setText("Question " + qid_string);
            }
        });

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuestion();
                finish();
            }
        });
    }

    void clear() {
        question_content.setText("");
        choice_a.setText("");
        choice_b.setText("");
        choice_c.setText("");
        choice_d.setText("");
    }

    void addQuestion() {
        qid +=1;
        String qtype = "choice";
        String qtitle = question_content.getText().toString();
        String answer_a = choice_a.getText().toString();
        String answer_b = choice_b.getText().toString();
        String answer_c = choice_c.getText().toString();
        String answer_d = choice_d.getText().toString();
        ApiMethods.addQuestion(new MyObserver<Status>(CreatSurveyActivity.this, status_listener), sid, qid, qtype, qtitle, answer_a, answer_b, answer_c, answer_d);
    }

}
