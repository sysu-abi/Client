package com.sysu.ceres.activity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.sysu.ceres.R;
import com.sysu.ceres.adapter.SectionsPagerAdapter;
import com.sysu.ceres.fragment.MessageFragment;
import com.sysu.ceres.model.Message;
import com.sysu.ceres.model.Task;

public class TaskDetailActivity extends AppCompatActivity implements MessageFragment.OnListFragmentInteractionListener {

    private static final String ARG_CURRENT_TASK = "current_task";
    private Task current_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        current_task = (Task) getIntent().getSerializableExtra(ARG_CURRENT_TASK);
        Log.d("Task Detail ", current_task.toString());
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        sectionsPagerAdapter.setTask(current_task);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onListFragmentInteraction(Message item) {
        Log.d("message clike", item.getDetail());
    }
}