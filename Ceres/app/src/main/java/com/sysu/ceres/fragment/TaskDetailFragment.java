package com.sysu.ceres.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sysu.ceres.CeresConfig;
import com.sysu.ceres.R;
import com.sysu.ceres.activity.DoSurveyActivity;
import com.sysu.ceres.activity.EditTaskActivity;
import com.sysu.ceres.activity.LoginActivity;
import com.sysu.ceres.activity.StatisticListActivity;
import com.sysu.ceres.http.ApiMethods;
import com.sysu.ceres.model.Status;
import com.sysu.ceres.model.SurveyList;
import com.sysu.ceres.model.Task;
import com.sysu.ceres.model.TaskList;
import com.sysu.ceres.model.UserList;
import com.sysu.ceres.observer.MyObserver;
import com.sysu.ceres.observer.ObserverOnNextListener;
import com.sysu.ceres.utils.TimeStringUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static android.content.ContentValues.TAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskDetailFragment extends Fragment {

    private static final String ARG_CURRENT_TASK = "current_task";
    private static final String ARG_SURVEY_SID = "survey_sid";
    private static final String ARG_TID = "task_tid";

    //0-未参与；1-参与；2-发布者; 3-发布者带问卷; 4-发布者finish; 5-发布者finish带问卷
    private int show_status = 0;

    TextView task_title;
    TextView task_detail;
    TextView task_current_num;
    TextView task_total_num;
    TextView task_type;
    TextView task_money;
    TextView task_start_time;
    TextView task_end_time;

    Button edit_btn;
    Button disjoin_btn;
    Button finish_btn;
    Button join_btn;
    Button get_statistic_btn;

    private Task currentTask;
    private int tid;

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ObserverOnNextListener<Status> listener = new ObserverOnNextListener<Status>() {
        @Override
        public void onNext(Status status) {
            Log.d(TAG, "onNext: " + status.toString());
            if (status.getState().equals("success")) {
                Toast.makeText(getActivity(),
                        status.getState(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(),
                        status.getState(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private ObserverOnNextListener<UserList> getJoinUserlistener = new ObserverOnNextListener<UserList>() {
        @Override
        public void onNext(UserList userlist) {
            Log.d(TAG, "onNext: " + userlist.toString());
            if (userlist.isJointUser(CeresConfig.currentUser.getUid())) {
                show_status = 1;
            } else {
                show_status = 0;
            }
            refreshBtnVisibility();
        }
    };

    ObserverOnNextListener<SurveyList> surveyList_listener = new ObserverOnNextListener<SurveyList>() {
        @Override
        public void onNext(SurveyList surveyList) {
            Log.d(TAG, "onNext: " + surveyList.getStatus());
            if (surveyList.getStatus().equals("success") && !surveyList.getSurvey().isEmpty()) {
                int sid = surveyList.getSurvey().get(0).getSid().intValue();
                Log.d("sid " , String.valueOf(sid));
                if (show_status == 0) { //未参与 -> doSurvey
                    Intent intent = new Intent(getActivity(), DoSurveyActivity.class);
                    intent.putExtra(ARG_TID, currentTask.getTid().intValue());
                    intent.putExtra(ARG_SURVEY_SID, sid);
                    startActivity(intent);
                } else if (show_status == 3) { // 发布者带问卷 -> getStatistics
                    Intent intent = new Intent(getActivity(), StatisticListActivity.class);
                    intent.putExtra(ARG_SURVEY_SID, sid);
                    startActivity(intent);
                }
            }
        }
    };

    ObserverOnNextListener<TaskList> getTasklistener = new ObserverOnNextListener<TaskList>() {
        @Override
        public void onNext(TaskList tasklist) {
            Log.d(TAG, "onNext: " + tasklist.toString());
            if (currentTask != null) {
                currentTask = tasklist.getTask();
                tid = currentTask.getTid().intValue();
                refreshData();
            }
        }
    };


    public static TaskDetailFragment newInstance(Task item) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CURRENT_TASK, item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentTask = (Task) getArguments().getSerializable(ARG_CURRENT_TASK);
            tid = currentTask.getTid().intValue();
        }
        if (CeresConfig.currentUser == null) {
            show_status = 0; //未参与
        } else if (currentTask.getUid().equals(CeresConfig.currentUser.getUid())){ // 发布者
            if (currentTask.getState().equals("finished")) {
                show_status = currentTask.getType().equals("survey") ? 5 : 4;
            } else {
                show_status = currentTask.getType().equals("survey") ? 3 : 2;
            }
        } else {
            ApiMethods.getJoinUsers(new MyObserver<UserList>(getActivity(), getJoinUserlistener), currentTask.getTid().intValue());
        }
    }

    void refreshData() {
        task_title.setText(currentTask.getTitle());
        task_detail.setText(currentTask.getDetail());
        task_current_num.setText("Current Participants: " + currentTask.getCurrentNum().toString());
        task_total_num.setText("Total Participants: " + currentTask.getTotalNum().toString());
        task_type.setText("Type: " + currentTask.getType());
        task_money.setText(currentTask.getMoney().toString());
        task_start_time.setText("Start Time: " + TimeStringUtil.getDateToString(currentTask.getStartTime()));
        task_end_time.setText("End Time: " + TimeStringUtil.getDateToString(currentTask.getEndTime()));
    }

    void refreshBtnVisibility(){
        switch (show_status) {
            case 0:
                edit_btn.setVisibility(View.GONE);
                disjoin_btn.setVisibility(View.GONE);
                finish_btn.setVisibility(View.GONE);
                join_btn.setVisibility(View.VISIBLE);
                get_statistic_btn.setVisibility(View.GONE);
                break;
            case 1:
                edit_btn.setVisibility(View.GONE);
                disjoin_btn.setVisibility(View.VISIBLE);
                finish_btn.setVisibility(View.GONE);
                join_btn.setVisibility(View.GONE);
                get_statistic_btn.setVisibility(View.GONE);
                break;
            case 2:
                edit_btn.setVisibility(View.VISIBLE);
                finish_btn.setVisibility(View.VISIBLE);
                disjoin_btn.setVisibility(View.GONE);
                join_btn.setVisibility(View.GONE);
                get_statistic_btn.setVisibility(View.GONE);
                break;
            case 3:
                edit_btn.setVisibility(View.VISIBLE);
                finish_btn.setVisibility(View.VISIBLE);
                disjoin_btn.setVisibility(View.GONE);
                join_btn.setVisibility(View.GONE);
                get_statistic_btn.setVisibility(View.VISIBLE);
                break;
            case 4:
                edit_btn.setVisibility(View.GONE);
                finish_btn.setVisibility(View.GONE);
                disjoin_btn.setVisibility(View.GONE);
                join_btn.setVisibility(View.GONE);
                get_statistic_btn.setVisibility(View.GONE);
                break;
            case 5:
                edit_btn.setVisibility(View.GONE);
                finish_btn.setVisibility(View.GONE);
                disjoin_btn.setVisibility(View.GONE);
                join_btn.setVisibility(View.GONE);
                get_statistic_btn.setVisibility(View.VISIBLE);
                break;
            default:
                edit_btn.setVisibility(View.GONE);
                disjoin_btn.setVisibility(View.GONE);
                finish_btn.setVisibility(View.GONE);
                join_btn.setVisibility(View.VISIBLE);
                get_statistic_btn.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (currentTask != null) {
            ApiMethods.getTask(new MyObserver<TaskList>(getActivity(), getTasklistener), tid);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            if (currentTask != null) {
                ApiMethods.getTask(new MyObserver<TaskList>(getActivity(), getTasklistener), tid);
            }
        } else {
            //相当于Fragment的onPause
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_task_detail, container, false);

        task_title = root.findViewById(R.id.task_detail_title);
        task_detail = root.findViewById(R.id.task_detail_detail);
        task_current_num = root.findViewById(R.id.task_detail_current_num);
        task_total_num = root.findViewById(R.id.task_detail_total_num);
        task_type = root.findViewById(R.id.task_detail_type);
        task_money = root.findViewById(R.id.task_detail_money);
        task_start_time = root.findViewById(R.id.task_detail_start_time);
        task_end_time = root.findViewById(R.id.task_detail_end_time);

        refreshData();

        edit_btn = root.findViewById(R.id.task_detail_edit_btn);
        disjoin_btn = root.findViewById(R.id.task_detail_disjoin_btn);
        finish_btn = root.findViewById(R.id.task_detail_finish_btn);
        join_btn = root.findViewById(R.id.task_detail_join_btn);
        get_statistic_btn = root.findViewById(R.id.task_detail_get_static);

        //Log.d("current task uid: " , currentTask.getUid().toString());
        //Log.d("user: ", CeresConfig.currentUser.getUid().toString());
        refreshBtnVisibility();

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditTaskActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ARG_CURRENT_TASK, currentTask);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });

        disjoin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiMethods.disjoinTask(new MyObserver<Status>(root.getContext(), listener),
                        currentTask.getTid().intValue(), CeresConfig.currentUser.getUid().intValue());
            }
        });

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiMethods.endTask(new MyObserver<Status>(root.getContext(), listener),
                        currentTask.getTid().intValue(), CeresConfig.currentUser.getUid().intValue());
            }
        });

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CeresConfig.currentUser != null) {
                    if (currentTask.getType().equals("survey")) {
                        ApiMethods.getSurveyList(new MyObserver<SurveyList>(root.getContext(), surveyList_listener), currentTask.getTid().intValue());
                    } else {
                        ApiMethods.joinTask(new MyObserver<Status>(root.getContext(), listener),
                                currentTask.getTid().intValue(), CeresConfig.currentUser.getUid().intValue());
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                }
            }
        });

        get_statistic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiMethods.getSurveyList(new MyObserver<SurveyList>(root.getContext(), surveyList_listener), currentTask.getTid().intValue());
            }
        });

        return root;
    }
}