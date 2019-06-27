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

    //todo
    long sid;
    int qid;

    private List<QuestionList> questionLists;
    private int ques_num;

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
        }
    };

    ObserverOnNextListener<Status> status_listener = new ObserverOnNextListener<Status>() {
        @Override
        public void onNext(Status status) {
            Log.d(TAG, "onNext: " + status.getStatus());
            if (status.getStatus() == "success") {
                Log.d(TAG, "status success");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_survey);
        init();
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
        ques_num = questionLists.size();
        submit_btn.setText("NEXT");
        display(qid);
    }

    void radioHandle() {

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.do_survey_answer_a:
                        ApiMethods.updateAnswers(new MyObserver<Status>(DoSurveyActivity.this, status_listener), sid, qid, "A");
                        break;
                    case R.id.do_survey_answer_b:
                        ApiMethods.updateAnswers(new MyObserver<Status>(DoSurveyActivity.this, status_listener), sid, qid, "B");
                        break;
                    case R.id.do_survey_answer_c:
                        ApiMethods.updateAnswers(new MyObserver<Status>(DoSurveyActivity.this, status_listener), sid, qid, "C");
                        break;
                    case R.id.do_survey_answer_d:
                        ApiMethods.updateAnswers(new MyObserver<Status>(DoSurveyActivity.this, status_listener), sid, qid, "D");
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
                if (qid - 1 <= ques_num) {
                    submit_btn.setText("NEXT");
                    qid += 1;
                    display(qid);
                }
                else {
                    submit_btn.setText("SUBMIT");
                    finish();
                }
            }
        });
    }

    void display(int qid) {
        String qid_string = String.valueOf(qid);
        question_id.setText("Question " + qid_string);
        qtitle.setText(questionLists.get(qid).getQtitle());
        answer_a.setText(questionLists.get(qid).getAnswerA());
        answer_b.setText(questionLists.get(qid).getAnswerB());
        answer_c.setText(questionLists.get(qid).getAnswerC());
        answer_d.setText(questionLists.get(qid).getAnswerD());
    }
}
