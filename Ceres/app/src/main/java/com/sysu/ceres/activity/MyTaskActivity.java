package com.sysu.ceres.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.sysu.ceres.R;
import com.sysu.ceres.fragment.TaskListFragment;
import com.sysu.ceres.model.Task;

public class MyTaskActivity extends AppCompatActivity {
    private static final String ARG_LIST_TYPE = "mytask_type";
    //false-mycreatetask; true-myjointask
    private boolean mytasklist_type = false;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        mytasklist_type = getIntent().getBooleanExtra(ARG_LIST_TYPE, false);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, TaskListFragment.newInstance(0, "asc"));
        transaction.commit();
    }
}
