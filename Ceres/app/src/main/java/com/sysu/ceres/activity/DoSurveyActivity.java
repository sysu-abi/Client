package com.sysu.ceres.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sysu.ceres.CeresConfig;
import com.sysu.ceres.R;
import com.sysu.ceres.http.ApiMethods;
import com.sysu.ceres.model.MessageList;
import com.sysu.ceres.model.QuestionList;
import com.sysu.ceres.model.Status;
import com.sysu.ceres.model.SurveyFull;
import com.sysu.ceres.model.SurveyList;
import com.sysu.ceres.observer.MyObserver;
import com.sysu.ceres.observer.ObserverOnNextListener;

import java.util.List;

import static android.content.ContentValues.TAG;

public class DoSurveyActivity extends AppCompatActivity {
    private static final String ARG_SURVEY_SID = "survey_sid";
    private static final String ARG_TID = "task_tid";

    //todo
    int sid;
    int qid = 1;
    int tid;

    private List<QuestionList> questionLists;
    private int ques_num;
    private String choice_answer;

    TextView question_id;
    TextView qtitle;
    RadioGroup radio_group;
    RadioButton answer_a;
    RadioButton answer_b;
    RadioButton answer_c;
    RadioButton answer_d;
    Button submit_btn;

    ObserverOnNextListener<SurveyFull> survey_full_listener = new ObserverOnNextListener<SurveyFull>() {
        @Override
        public void onNext(SurveyFull surveyFull) {
            Log.d(TAG, "onNext: " + surveyFull.getStatus());
            questionLists = surveyFull.getQuestionList();
            ques_num = questionLists.size();
            if (ques_num == 0) {
                Toast.makeText(DoSurveyActivity.this, "No questions in survey", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                display(qid);
            }
        }
    };

    private ObserverOnNextListener<Status> listener = new ObserverOnNextListener<Status>() {
        @Override
        public void onNext(Status status) {
            Log.d(TAG, "onNext: " + status.toString());
            if (status.getState().equals("success")) {
                Toast.makeText(DoSurveyActivity.this,
                        status.getState(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DoSurveyActivity.this,
                        status.getState(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    ObserverOnNextListener<Status> status_listener = new ObserverOnNextListener<Status>() {
        @Override
        public void onNext(Status status) {
            Log.d(TAG, "onNext: " + status.getStatus());
            if (status.getStatus() == "success") {
                Log.d(TAG, "update answer: status success");
            } else {
                Log.d(TAG, "update answer: " + status.getLog());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_survey);
        sid = getIntent().getIntExtra(ARG_SURVEY_SID, -1);
        tid = getIntent().getIntExtra(ARG_TID, -1);
        if(sid == -1 || tid == -1) {
            Log.d("no sid/tid ", "-1");
        }

        init();
        ApiMethods.getQuestionList(new MyObserver<SurveyFull>(DoSurveyActivity.this, survey_full_listener), sid);
        radioHandle();
        clickHandle();
    }

    void init() {
        question_id = findViewById(R.id.do_survey_qid);
        qtitle = findViewById(R.id.do_survey_ques_content);
        radio_group = findViewById(R.id.do_survey_radio_group);
        answer_a = findViewById(R.id.do_survey_answer_a);
        answer_b = findViewById(R.id.do_survey_answer_b);
        answer_c = findViewById(R.id.do_survey_answer_c);
        answer_d = findViewById(R.id.do_survey_answer_d);
        submit_btn = findViewById(R.id.do_survey_submit_btn);

        qid = 1;
        submit_btn.setText("NEXT");
    }

    void radioHandle() {

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.do_survey_answer_a:
                        choice_answer = "A";
                        break;
                    case R.id.do_survey_answer_b:
                        choice_answer = "B";
                        break;
                    case R.id.do_survey_answer_c:
                        choice_answer = "C";
                        break;
                    case R.id.do_survey_answer_d:
                        choice_answer = "D";
                        break;
                    default:
                        break;
                }
            }
        });
    }

    void clickHandle() {
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiMethods.updateAnswers(new MyObserver<Status>(DoSurveyActivity.this, status_listener), sid, questionLists.get(qid - 1).getQid().intValue(), choice_answer);
                if (qid < ques_num) {
                    submit_btn.setText("NEXT");
                    qid += 1;
                    display(qid);
                }
                else {
                    submit_btn.setText("SUBMIT");
                    ApiMethods.joinTask(new MyObserver<Status>(DoSurveyActivity.this, listener),
                            tid, CeresConfig.currentUser.getUid().intValue());
                    finish();
                }
            }
        });
    }

    void display(int qid) {
        String qid_string = String.valueOf(qid);
        question_id.setText("Question " + qid_string);
        qtitle.setText(questionLists.get(qid - 1).getQtitle());
        answer_a.setText("A. " + questionLists.get(qid - 1).getAnswerA());
        answer_b.setText("B. " + questionLists.get(qid - 1).getAnswerB());
        answer_c.setText("C. " + questionLists.get(qid - 1).getAnswerC());
        answer_d.setText("D. " + questionLists.get(qid - 1).getAnswerD());

        if (qid < ques_num) {
            submit_btn.setText("NEXT");
        }
        else {
            submit_btn.setText("SUBMIT");
        }
    }
}
