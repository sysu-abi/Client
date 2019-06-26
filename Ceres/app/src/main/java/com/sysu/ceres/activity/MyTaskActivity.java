package com.sysu.ceres.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.sysu.ceres.R;
import com.sysu.ceres.fragment.TaskListFragment;
import com.sysu.ceres.model.Task;

public class MyTaskActivity extends AppCompatActivity implements TaskListFragment.OnListFragmentInteractionListener {
    private static final String ARG_LIST_TYPE = "mytask_type";
    private static final String ARG_CURRENT_TASK = "current_task";
    //false-mycreatetask; true-myjointask
    private boolean mytasklist_type = false;
    private int getTaskMethod = 4;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        mytasklist_type = getIntent().getBooleanExtra(ARG_LIST_TYPE, false);
        getTaskMethod = mytasklist_type ? 4 : 5;
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_mytasklist, TaskListFragment.newInstance(getTaskMethod, "asc"));
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(Task item) {
        Intent intent = new Intent();
        intent.setClass(MyTaskActivity.this, TaskDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CURRENT_TASK, item);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }
}
