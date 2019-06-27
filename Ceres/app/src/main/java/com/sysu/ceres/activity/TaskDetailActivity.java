package com.sysu.ceres.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sysu.ceres.CeresConfig;
import com.sysu.ceres.R;
import com.sysu.ceres.adapter.SectionsPagerAdapter;
import com.sysu.ceres.fragment.MessageFragment;
import com.sysu.ceres.http.Api;
import com.sysu.ceres.http.ApiMethods;
import com.sysu.ceres.model.Message;
import com.sysu.ceres.model.Status;
import com.sysu.ceres.model.SurveyList;
import com.sysu.ceres.model.Task;
import com.sysu.ceres.observer.MyObserver;
import com.sysu.ceres.observer.ObserverOnNextListener;

import static android.content.ContentValues.TAG;

public class TaskDetailActivity extends AppCompatActivity implements MessageFragment.OnListFragmentInteractionListener {

    private static final String ARG_CURRENT_TASK = "current_task";
    private Task current_task;
    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    TabLayout tabs;

    private ObserverOnNextListener<Status> listener = new ObserverOnNextListener<Status>() {
        @Override
        public void onNext(Status status) {
            Log.d(TAG, "onNext: " + status.toString());
            if (status.getStatus().equals("success")) {
                viewPager.setCurrentItem(0);
            } else {
                Toast.makeText(TaskDetailActivity.this,
                        status.getStatus(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        current_task = (Task) getIntent().getSerializableExtra(ARG_CURRENT_TASK);
        Log.d("Task Detail ", current_task.toString());
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        sectionsPagerAdapter.setTask(current_task);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onListFragmentInteraction(Message item) {
        final Message msg = item;
        if (current_task.getUid().equals(CeresConfig.currentUser.getUid())) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("提示")
                    .setMessage("是否删除该留言")
                    .setPositiveButton("确认",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ApiMethods.removeMessage(new MyObserver<Status>(TaskDetailActivity.this, listener),
                                    msg.getTid().intValue(), msg.getFloor().intValue());
                        }
                    }).setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d("remove message ", "cancel.");
                        }
                    }).create().show();

        }
    }
}